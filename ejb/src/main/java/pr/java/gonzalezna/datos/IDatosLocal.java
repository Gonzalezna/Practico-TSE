package pr.java.gonzalezna.datos;

import jakarta.ejb.Local;
import java.util.List;
import pr.java.gonzalezna.entidades.Vehiculo;

@Local
public interface IDatosLocal {

	void agregarVehiculo(Vehiculo vehiculo);
	
	List<Vehiculo> obtenerVehiculos();
	
	Vehiculo buscarVehiculoPorId(int id);
	
	List<Vehiculo> buscarVehiculoPorModelo(String modelo);
}