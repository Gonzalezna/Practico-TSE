package pr.java.gonzalezna.datos;

import jakarta.ejb.Remote;
import java.util.List;
import pr.java.gonzalezna.entidades.Vehiculo;

@Remote
public interface IDatosRemote {
	
	void agregarVehiculo(Vehiculo vehiculo);
	
	List<Vehiculo> obtenerVehiculos();
	
	Vehiculo buscarVehiculoPorId(int id);
	
	List<Vehiculo> buscarVehiculoPorModelo(String modelo);
} 
