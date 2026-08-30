package pr.java.gonzalezna.negocio;

import jakarta.ejb.Remote;
import java.util.List;
import pr.java.gonzalezna.entidades.Vehiculo;

@Remote
public interface INegocioRemote {
	
    void altaVehiculo(Vehiculo vehiculo) throws Exception;
    
    List<Vehiculo> listarVehiculos();
    
    Vehiculo buscarVehiculoPorId(int id);
    
    List<Vehiculo> buscarVehiculoPorModelo(String modelo);
}