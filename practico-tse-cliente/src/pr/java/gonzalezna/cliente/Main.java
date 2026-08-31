package pr.java.gonzalezna.cliente;

import java.util.Hashtable;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;

import pr.java.gonzalezna.entidades.Vehiculo;
import pr.java.gonzalezna.negocio.INegocioRemote;

public class Main {

    public static void main(String[] args) {
        try {
            // 1. Configurar propiedades de conexión JNDI para WildFly
            Hashtable<String, String> jndiProperties = new Hashtable<>();
            jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
            jndiProperties.put(Context.PROVIDER_URL, "remote+http://localhost:8080");

            Context context = new InitialContext(jndiProperties);

            // 2. Ruta JNDI exportada por WildFly para el EJB Stateless
            // Formato estándar: ejb:<app-name>/<module-name>/<bean-name>!<full-interface-name>
            String jndiName = "ejb:practico-tse/practico-tse-ejb/NegocioBean!pr.java.gonzalezna.negocio.INegocioRemote";

            System.out.println("Conectando y buscando EJB de Negocio en WildFly...");
            INegocioRemote negocio = (INegocioRemote) context.lookup(jndiName);

            // 3. Probamos alta de vehiculo
            System.out.println("\n--- Creando Vehiculo desde Cliente Remoto ---");
            Vehiculo nuevo = new Vehiculo();
            nuevo.setId(1);
            nuevo.setModelo("Toyota Corolla");
            nuevo.setPotencia(170);

            negocio.altaVehiculo(nuevo);
            System.out.println("Vehiculo dado de alta exitosamente!");

            // 4. Probar la consulta remota
            System.out.println("\n--- Consultando Lista de Vehiculos ---");
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