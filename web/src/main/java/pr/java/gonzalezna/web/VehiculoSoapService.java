package pr.java.gonzalezna.web;

import jakarta.ejb.EJB;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.util.List;

import pr.java.gonzalezna.entidades.Vehiculo;
import pr.java.gonzalezna.negocio.INegocioLocal;


@WebService
public class VehiculoSoapService {

	@EJB
	private INegocioLocal negocio;
	
	@WebMethod
	public void altaVehiculo(@WebParam(name = "vehiculo") Vehiculo vehiculo) throws Exception {
		negocio.altaVehiculo(vehiculo);
	}
	
	@WebMethod
	public List<Vehiculo> listarVehiculos(){
		return negocio.listarVehiculos();
	}
	
	@WebMethod
	public Vehiculo buscarVehiculoPorId(@WebParam(name = "id")int id) {
		return negocio.buscarVehiculoPorId(id);
	}
	
	@WebMethod
	public List<Vehiculo> buscarVehiculoPorModelo(@WebParam(name = "modelo") String modelo){
		return negocio.buscarVehiculoPorModelo(modelo);
	}
}
