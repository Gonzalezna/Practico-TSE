package pr.java.gonzalezna.negocio;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import java.util.List;

import pr.java.gonzalezna.datos.IDatosLocal;
import pr.java.gonzalezna.entidades.Vehiculo;


@Stateless
public class NegocioBean implements INegocioLocal, INegocioRemote {

    @EJB
    private IDatosLocal datosBean;

    @Override
    public void altaVehiculo(Vehiculo vehiculo) throws Exception {
        if (vehiculo == null) {
            throw new Exception("El vehículo no puede ser nulo.");
        }
        
        
        if (vehiculo.getPotencia() <= 0) {
            throw new Exception("La potencia debe ser mayor a 0.");
        }

        if (vehiculo.getPeso() <= 0) {
            throw new Exception("El peso debe ser mayor a 0.");
        }

        if (datosBean.buscarVehiculoPorId(vehiculo.getId()) != null) {
            throw new Exception("Ya existe un vehículo registrado con el ID " + vehiculo.getId());
        }
        
        //Delega a la capa de datos
        datosBean.agregarVehiculo(vehiculo);
    }

    @Override
    public List<Vehiculo> listarVehiculos() {
        return datosBean.obtenerVehiculos();
    }

    @Override
    public Vehiculo buscarVehiculoPorId(int id) {
        return datosBean.buscarVehiculoPorId(id);
    }
    
    @Override
    public List<Vehiculo> buscarVehiculoPorModelo(String modelo){
    	return datosBean.buscarVehiculoPorModelo(modelo);
    }
}