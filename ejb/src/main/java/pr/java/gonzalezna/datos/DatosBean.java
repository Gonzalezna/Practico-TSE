package pr.java.gonzalezna.datos;

import jakarta.ejb.Singleton;
import java.util.ArrayList;
import java.util.List;
import pr.java.gonzalezna.entidades.Vehiculo;

@Singleton
public class DatosBean implements IDatosLocal, IDatosRemote{

	//Lista en memoria para simular BD
	private final List<Vehiculo> vehiculos = new ArrayList<>();
	
	@Override
	public void agregarVehiculo(Vehiculo vehiculo) {
		vehiculos.add(vehiculo);
	}
	
	@Override
	public List<Vehiculo> obtenerVehiculos(){
		//Retornamos una copia de la lista
		return new ArrayList<>(vehiculos);
	}
	
	@Override
	public Vehiculo buscarVehiculoPorId(int id) {
		for(Vehiculo v : vehiculos) {
			if(v.getId() == id) {
				return v;
			}
		}
		return null;
	}
	
	  @Override
	    public List<Vehiculo> buscarVehiculoPorModelo(String modelo) {
	    	List<Vehiculo> resultado = new ArrayList<>();
	        for (Vehiculo v : obtenerVehiculos()) {
	            if (v.getModelo().equalsIgnoreCase(modelo)) {
	                resultado.add(v);
	            }
	        }
	        return resultado;
	    }
	
}
