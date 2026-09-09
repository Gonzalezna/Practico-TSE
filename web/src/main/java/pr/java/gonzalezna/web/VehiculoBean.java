package pr.java.gonzalezna.web;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import pr.java.gonzalezna.entidades.Vehiculo;
import pr.java.gonzalezna.negocio.INegocioLocal;

import jakarta.jms.*;
import jakarta.inject.Inject;
import jakarta.annotation.Resource;

@Named("vehiculoBean")
@ViewScoped //el bean vive mientras el usuario esta en la misma vista
public class VehiculoBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    @EJB
    private INegocioLocal negocioBean;
    
    //Lista que se muestra en la tabla
    private List<Vehiculo> lista = new ArrayList<>();
    
    //Campo del formulario de busqueda
    private String buscarModelo;
    
    //Mensajes para mostrar en pantalla
    private String mensajeExito;
    private String mensajeError;
    
    private Vehiculo vehiculo;
    
    @Inject
    @JMSConnectionFactory("java:/ConnectionFactory")
    private JMSContext context;

    @Resource(lookup = "java:/jms/queue/queue_alta_vehiculo")
    private Queue cola;
    
    @PostConstruct
    public void init() {
    	    vehiculo = new Vehiculo();
    	    listar();  //se inicializa con la lista de vehiculos
   
    }
    
    public void alta() {
    	limpiarMensajes();
    	
    	try {
    		negocioBean.altaVehiculo(vehiculo);
            vehiculo = new Vehiculo();  
            listar();                   // Refrescar tabla
            mensajeExito = "Vehiculo ingresado con exito!";
        } catch (Exception e) {
            mensajeError = "Error: " + e.getMessage();
        }
    }
    
    public void enviarAltaVehiculo() {
    	limpiarMensajes();
    	
    	try {
        String cuerpo = vehiculo.getId()
                + "|" + vehiculo.getModelo()
                + "|" + vehiculo.getPeso()
                + "|" + vehiculo.getPotencia()
        		+ "|" + vehiculo.getFechaFabricacion();

        context.createProducer().send(cola, cuerpo);
        
        vehiculo = new Vehiculo();
        mensajeExito = "Alta enviada a la queue";
    	} catch (Exception e) {
    		mensajeError = "Error:" + e.getMessage();
    	}
    }

    public void listar() {
        limpiarMensajes();
        lista = negocioBean.listarVehiculos();
    }

    public void buscarPorModelo() {
        limpiarMensajes();
        if (buscarModelo == null || buscarModelo.isBlank()) {
            mensajeError = "Ingrese un modelo para buscar.";
            return;
        }
        lista = negocioBean.buscarVehiculoPorModelo(buscarModelo.trim());
        mensajeExito = "Busqueda por modelo: " + buscarModelo.trim();
    }
 
    public void limpiarBusqueda() {
        buscarModelo = null;
        listar();
    }
    private void limpiarMensajes() {
        mensajeExito = null;
        mensajeError = null;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }
    
    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }
    
    public List<Vehiculo> getLista() {
        return lista;
    }
    
    public void setLista(List<Vehiculo> lista) {
        this.lista = lista;
    }
    
    public String getBuscarModelo() {
        return buscarModelo;
    }
    
    public void setBuscarModelo(String buscarModelo) {
        this.buscarModelo = buscarModelo;
    }
    
    public String getMensajeExito() {
        return mensajeExito;
    }
    
    public void setMensajeExito(String mensajeExito) {
        this.mensajeExito = mensajeExito;
    }
    
    public String getMensajeError() {
        return mensajeError;
    }
    
    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }
    	
}
