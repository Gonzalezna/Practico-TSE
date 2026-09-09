package pr.java.gonzalezna.entidades;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Vehiculo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	//Atributos
	@Id
	private int id;
	
	private String modelo;
	private int peso;
	private int potencia;
	private LocalDate fechaFabricacion;
	
	//Constructor vacio
	public Vehiculo(){
		
	}
	
	//Constructor con parametros
	public Vehiculo(int id, String modelo, int peso, int potencia, LocalDate fechaFabricacion) {
		this.id = id;
		this.modelo = modelo;
		this.peso = peso;
		this.potencia = potencia;
		this.fechaFabricacion = fechaFabricacion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public int getPotencia() {
		return potencia;
	}

	public void setPotencia(int potencia) {
		this.potencia = potencia;
	}

	public LocalDate getFechaFabricacion() {
		return fechaFabricacion;
	}

	public void setFechaFabricacion(LocalDate fechaFabricacion) {
		this.fechaFabricacion = fechaFabricacion;
	}

	
	
}
