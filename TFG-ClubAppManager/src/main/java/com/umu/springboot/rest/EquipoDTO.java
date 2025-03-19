package com.umu.springboot.rest;

public class EquipoDTO {
	private String idEquipo;
	private String numeroJugadores;
	private String entrenadores;

	public EquipoDTO(String idEquipo, String numeroJugadores, String entrenadores) {
		this.idEquipo = idEquipo;
		this.numeroJugadores = numeroJugadores;
		this.entrenadores = entrenadores;
	}

	public EquipoDTO() {
	}

	public String getIdEquipo() {
		return idEquipo;
	}

	public void setIdEquipo(String idEquipo) {
		this.idEquipo = idEquipo;
	}

	public String getNumeroJugadores() {
		return numeroJugadores;
	}

	public void setNumeroJugadores(String numeroJugadores) {
		this.numeroJugadores = numeroJugadores;
	}

	public String getEntrenadores() {
		return entrenadores;
	}

	public void setEntrenadores(String entrenadores) {
		this.entrenadores = entrenadores;
	}

}
