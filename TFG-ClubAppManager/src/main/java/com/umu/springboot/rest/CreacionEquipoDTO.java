package com.umu.springboot.rest;

import java.util.List;

public class CreacionEquipoDTO {
	private String nombre;
	private String categoria;
	private List<EntrenadorDTO> entrenadores;

	public CreacionEquipoDTO(String nombre, String categoria, List<EntrenadorDTO> entrenadores) {
		super();
		this.nombre = nombre;
		this.categoria = categoria;
		this.entrenadores = entrenadores;
	}

	public CreacionEquipoDTO() {
		super();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public List<EntrenadorDTO> getEntrenadores() {
		return entrenadores;
	}

	public void setEntrenadores(List<EntrenadorDTO> entrenadores) {
		this.entrenadores = entrenadores;
	}

}
