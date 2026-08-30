package pr.java.gonzalezna.negocio;

import jakarta.ejb.Local;
import java.util.List;
import pr.java.gonzalezna.entidades.Vehiculo;

@Local
public interface INegocioLocal {
	
    void altaVehiculo(Vehiculo vehiculo) throws Exception;
    
    List<Vehiculo> listarVehiculos();
    
    Vehiculo buscarVehiculoPorId(int id);
    
    List<Vehiculo> buscarVehiculoPorModelo(String modelo);
}