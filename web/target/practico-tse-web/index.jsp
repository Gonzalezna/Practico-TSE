<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="pr.java.gonzalezna.entidades.Vehiculo" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Gestion de Vehiculos - TSE</title>
<style>
    body { font-family: Arial, sans-serif; margin: 30px; }
    h2 { color: #333; }
    .form-group { margin-bottom: 12px; }
    label { display: inline-block; width: 120px; font-weight: bold; }
    input[type="text"], input[type="number"] { padding: 5px; width: 200px; }
    button { padding: 7px 15px; background-color: #007bff; color: white; border: none; cursor: pointer; }
    .btn-success { background-color: #28a745; }
    .btn-secondary { background-color: #6c757d; }
    table { width: 100%; border-collapse: collapse; margin-top: 20px; }
    th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
    th { background-color: #f4f4f4; }
    .error { color: red; font-weight: bold; }
    .exito { color: green; font-weight: bold; }
    .section { margin-bottom: 24px; }
</style>
</head>
<body>

    <h2>Alta de Vehiculo</h2>

    <% if (request.getAttribute("mensajeError") != null) { %>
        <p class="error"><%= request.getAttribute("mensajeError") %></p>
    <% } %>
    <% if (request.getAttribute("mensajeExito") != null) { %>
        <p class="exito"><%= request.getAttribute("mensajeExito") %></p>
    <% } %>

    <form action="VehiculoServlet" method="post" class="section">
        <div class="form-group">
            <label for="id">ID:</label>
            <input type="number" id="id" name="id" required>
        </div>
        <div class="form-group">
            <label for="modelo">Modelo:</label>
            <input type="text" id="modelo" name="modelo" required>
        </div>
        <div class="form-group">
            <label for="peso">Peso (kg):</label>
            <input type="number" id="peso" name="peso" required min="1">
        </div>
        <div class="form-group">
            <label for="potencia">Potencia (CV):</label>
            <input type="number" id="potencia" name="potencia" required min="1">
        </div>
        <div class="form-group">
            <label for="fechaFabricacion">Fecha Fab.:</label>
            <input type="date" id="fechaFabricacion" name="fechaFabricacion" required>
        </div>
        <button type="submit">Guardar Vehiculo</button>
    </form>

    <hr>

    <h2>Listado de Vehiculos Registrados</h2>

    <form action="VehiculoServlet" method="get" class="section">
        <button type="submit" class="btn-success">Cargar / Refrescar Listado</button>
    </form>

    <form action="VehiculoServlet" method="get" class="section">
        <div class="form-group">
            <label for="buscarModelo">Buscar modelo:</label>
            <input type="text" id="buscarModelo" name="buscarModelo"
                value="<%= request.getAttribute("buscarModelo") != null ? request.getAttribute("buscarModelo") : "" %>">
        </div>
        <button type="submit">Buscar</button>
        <a href="VehiculoServlet" style="margin-left: 10px;">Limpiar busqueda</a>
    </form>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Modelo</th>
                <th>Peso (kg)</th>
                <th>Potencia (CV)</th>
                <th>Fecha Fabricacion</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<Vehiculo> lista = (List<Vehiculo>) request.getAttribute("listaVehiculos");
                if (lista != null && !lista.isEmpty()) {
                    for (Vehiculo v : lista) {
            %>
                        <tr>
                            <td><%= v.getId() %></td>
                            <td><%= v.getModelo() %></td>
                            <td><%= v.getPeso() %></td>
                            <td><%= v.getPotencia() %></td>
                            <td><%= v.getFechaFabricacion() %></td>
                        </tr>
            <%
                    }
                } else {
            %>
                    <tr>
                        <td colspan="5">No hay vehiculos registrados.</td>
                    </tr>
            <% } %>
        </tbody>
    </table>

</body>
</html>
