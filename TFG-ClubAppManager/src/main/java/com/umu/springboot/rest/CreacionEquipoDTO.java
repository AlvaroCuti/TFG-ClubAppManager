package com.umu.springboot.rest;

import java.util.List;

public class CreacionEquipoDTO {
	private List<EntrenadorDTO> entrenadores;

	public CreacionEquipoDTO(List<EntrenadorDTO> entrenadores) {
		this.entrenadores = entrenadores;
	}

	public CreacionEquipoDTO() {
	}

	public List<EntrenadorDTO> getEntrenadores() {
		return entrenadores;
	}

	public void setEntrenadores(List<EntrenadorDTO> entrenadores) {
		this.entrenadores = entrenadores;
	}

}
