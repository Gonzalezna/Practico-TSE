package pr.java.gonzalezna.web;

import java.io.IOException;
import java.util.List;
import java.time.LocalDate;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import pr.java.gonzalezna.entidades.Vehiculo;
import pr.java.gonzalezna.negocio.INegocioLocal;

@WebServlet("/VehiculoServlet")
public class VehiculoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private INegocioLocal negocioBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String modeloBusqueda = request.getParameter("buscarModelo");
        List<Vehiculo> lista;

        if (modeloBusqueda != null && !modeloBusqueda.isBlank()) {
            lista = negocioBean.buscarVehiculoPorModelo(modeloBusqueda.trim());
            request.setAttribute("buscarModelo", modeloBusqueda.trim());
            request.setAttribute("mensajeExito", "Busqueda por modelo: " + modeloBusqueda.trim());
        } else {
            lista = negocioBean.listarVehiculos();
        }

        request.setAttribute("listaVehiculos", lista);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String modelo = request.getParameter("modelo");
            int peso = Integer.parseInt(request.getParameter("peso"));
            int potencia = Integer.parseInt(request.getParameter("potencia"));
            String fechaStr = request.getParameter("fechaFabricacion");
            LocalDate fecha = LocalDate.parse(fechaStr);

            Vehiculo v = new Vehiculo();
            v.setId(id);
            v.setModelo(modelo);
            v.setPeso(peso);
            v.setPotencia(potencia);
            v.setFechaFabricacion(fecha);
            negocioBean.altaVehiculo(v);

            request.setAttribute("mensajeExito", "Vehiculo ingresado con exito!");

        } catch (Exception e) {
            request.setAttribute("mensajeError", "Error: " + e.getMessage());
        }

        doGet(request, response);
    }
}
