package com.umu.springboot.rest;

import java.util.List;

public class CreacionEquipoDTO {
	private String nombre;
	private List<EntrenadorDTO> entrenadores;

	public CreacionEquipoDTO(String nombre, List<EntrenadorDTO> entrenadores) {
		super();
		this.nombre = nombre;
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

	public List<EntrenadorDTO> getEntrenadores() {
		return entrenadores;
	}

	public void setEntrenadores(List<EntrenadorDTO> entrenadores) {
		this.entrenadores = entrenadores;
	}

}
