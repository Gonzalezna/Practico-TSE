package pr.java.gonzalezna.cliente;

import java.time.LocalDate;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;

import pr.java.gonzalezna.entidades.Vehiculo;
import pr.java.gonzalezna.negocio.INegocioRemote;

public class Main {

    public static void main(String[] args) {
        try {
            // propiedades de conexion JNDI para wildfly
            Hashtable<String, String> jndiProperties = new Hashtable<>();
            jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY,
                    "org.wildfly.naming.client.WildFlyInitialContextFactory");
            jndiProperties.put(Context.PROVIDER_URL, "remote+http://localhost:8080");

            // EJB y JMS
            Context context = new InitialContext(jndiProperties);


            String jndiName = "ejb:practico-tse/practico-tse-ejb/NegocioBean!pr.java.gonzalezna.negocio.INegocioRemote";
            System.out.println("Conectando y buscando EJB de Negocio en WildFly...");
            INegocioRemote negocio = (INegocioRemote) context.lookup(jndiName);
            
            // camino 1: alta remota 
            // NegocioBean exige potencia > 0 y peso > 0; hay que completar todos los campos de Vehiculo.
            System.out.println("\n--- Alta por EJB remoto ---");
            Vehiculo porEjb = new Vehiculo();
            porEjb.setId(1);
            porEjb.setModelo("Toyota Corolla");
            porEjb.setPeso(1200);
            porEjb.setPotencia(170);
            porEjb.setFechaFabricacion(LocalDate.of(2020, 1, 15));

            negocio.altaVehiculo(porEjb);
            System.out.println("Vehiculo dado de alta por EJB.");

            // camino 2: alta por cola (ejercicio 5) 
            // El texto tiene que coincidir con AltaVehiculoMDB.parsear: 5 campos separados por |
            System.out.println("\n--- Alta por JMS ---");
            ConnectionFactory cf =
                    (ConnectionFactory) context.lookup("jms/RemoteConnectionFactory");
            Queue cola = (Queue) context.lookup("jms/queue/queue_alta_vehiculo");

            Vehiculo porCola = new Vehiculo();
            porCola.setId(2);
            porCola.setModelo("Ford Focus");
            porCola.setPeso(1300);
            porCola.setPotencia(150);
            porCola.setFechaFabricacion(LocalDate.of(2021, 6, 1));

            String cuerpo = porCola.getId()
                    + "|" + porCola.getModelo()
                    + "|" + porCola.getPeso()
                    + "|" + porCola.getPotencia()
                    + "|" + porCola.getFechaFabricacion();

            try (JMSContext jms = cf.createContext()) {
                jms.createProducer().send(cola, cuerpo);
            }
            System.out.println("Mensaje enviado a la cola.");

            // El MDB es asíncrono: el alta por cola puede no verse todavía.
            System.out.println("\n--- Lista (el de JMS puede no estar todavía) ---");
            List<Vehiculo> lista = negocio.listarVehiculos();
            for (Vehiculo v : lista) {
                System.out.println("ID: " + v.getId() + " | Modelo: " + v.getModelo() + " | Potencia: " + v.getPotencia() + " CV");
            }	

        } catch (Exception e) {
            System.err.println("Error al comunicarse con el servidor:");
            e.printStackTrace();
        }
    }
}
