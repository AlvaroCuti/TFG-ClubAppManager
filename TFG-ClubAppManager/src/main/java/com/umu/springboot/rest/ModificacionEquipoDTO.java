package com.umu.springboot.rest;

import java.util.List;

public class ModificacionEquipoDTO {
	private String nombre;
	private List<EntrenadorDTO> entrenadores;
	private List<JugadorIdDTO> jugadores;

	public ModificacionEquipoDTO(String nombre, List<EntrenadorDTO> entrenadores, List<JugadorIdDTO> jugadores) {
		this.entrenadores = entrenadores;
		this.jugadores = jugadores;
	}

	public ModificacionEquipoDTO() {
	}

	public List<EntrenadorDTO> getEntrenadores() {
		return entrenadores;
	}

	public void setEntrenadores(List<EntrenadorDTO> entrenadores) {
		this.entrenadores = entrenadores;
	}

	public List<JugadorIdDTO> getJugadores() {
		return jugadores;
	}

	public void setJugadores(List<JugadorIdDTO> jugadores) {
		this.jugadores = jugadores;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
