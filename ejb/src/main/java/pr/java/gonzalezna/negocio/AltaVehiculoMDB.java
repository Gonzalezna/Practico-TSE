package pr.java.gonzalezna.negocio;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.EJB;
import jakarta.ejb.MessageDriven;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;
import pr.java.gonzalezna.entidades.Vehiculo;

@MessageDriven(activationConfig = { 
        @ActivationConfigProperty(
        propertyName = "destinationLookup",     //Necesario para el WildFly
        propertyValue = "java:/jms/queue/queue_alta_vehiculo"),
        @ActivationConfigProperty(
                propertyName = "acknowledgeMode",
                propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(
                propertyName = "destinationType",
                propertyValue = "jakarta.jms.Queue")
})

public class AltaVehiculoMDB implements MessageListener {

	@EJB
	private INegocioLocal negocio;
	
	@Override
    public void onMessage(Message inMessage) {
        TextMessage msg = null;
        try {
            if (inMessage instanceof TextMessage) {
                msg = (TextMessage) inMessage;
                System.err.println("MDB: recibí mensaje: " + msg.getText());
                
            String cuerpo = msg.getText();
            Vehiculo vehiculo = parsear(cuerpo);
            negocio.altaVehiculo(vehiculo);

            }
        } catch (JMSException e) {
            //posible reintento con RuntineException
            throw new RuntimeException("No se pudo leer el mensaje JMS", e);
        } catch (Exception e) {
            //mal parseado o rechazado por capa de negocio
            System.err.println("Alta de vehiculo rechazada o fallida: " + e.getMessage());
        }
    }
	
	 //parseo, el mensaje es convertido a un objeto para el alta de negocio
    private Vehiculo parsear(String cuerpo) throws Exception {
        if (cuerpo == null || cuerpo.isBlank()) {
            throw new Exception("Mensaje vacio");
        }

        String[] partes = cuerpo.split("\\|", -1);
        if (partes.length != 5) {
            throw new Exception("Se esperaban 5 campos separados por |");
        }

        Vehiculo v = new Vehiculo();
        v.setId(Integer.parseInt(partes[0].trim()));
        v.setModelo(partes[1].trim());
        v.setPeso(Integer.parseInt(partes[2].trim()));
        v.setPotencia(Integer.parseInt(partes[3].trim()));
        v.setFechaFabricacion(java.time.LocalDate.parse(partes[4].trim()));
        
        return v;
    }
}

