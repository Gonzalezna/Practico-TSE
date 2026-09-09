package pr.java.gonzalezna.datos;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

import pr.java.gonzalezna.entidades.Vehiculo;

@Stateless
public class DatosBean implements IDatosLocal, IDatosRemote{

	@PersistenceContext(unitName = "practico-tsePersistenceUnit")
	private EntityManager em;
	
	
	@Override
	public void agregarVehiculo(Vehiculo vehiculo) {
		em.persist(vehiculo);
	}
	
	@Override
	public List<Vehiculo> obtenerVehiculos(){
		return em.createQuery("SELECT V FROM Vehiculo V", Vehiculo.class).getResultList();
	}
	
	@Override
	public Vehiculo buscarVehiculoPorId(int id) {
		return em.find(Vehiculo.class, id);
	}
	
	  @Override
	    public List<Vehiculo> buscarVehiculoPorModelo(String modelo) {
	    	return em.createQuery("SELECT V FROM Vehiculo V WHERE LOWER(V.modelo) = LOWER(:modelo)", Vehiculo.class)
	    			.setParameter("modelo", modelo)
	    			.getResultList();	
	  }
}
