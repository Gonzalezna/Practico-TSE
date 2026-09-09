package pr.java.gonzalezna.web;

import jakarta.ejb.EJB;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;

import pr.java.gonzalezna.negocio.INegocioLocal;
import pr.java.gonzalezna.entidades.Vehiculo;

import java.util.List;



@Path("/vehiculos")
public class VehiculoRestService {

	@EJB
	private INegocioLocal negocio;
	
	@GET
	public List<Vehiculo> listarVehiculos(@QueryParam("modelo")String modelo){
		if(modelo == null) {
			return negocio.listarVehiculos();
		}else
		return negocio.buscarVehiculoPorModelo(modelo);
	}
	
	@GET
	@Path("{id}")
	public Vehiculo buscarVehiculoPorId(@PathParam("id")int id){
		return negocio.buscarVehiculoPorId(id);
	}
	
	@POST
	public void alta(Vehiculo vehiculo) throws Exception{
		negocio.altaVehiculo(vehiculo);	
	}
	
}
