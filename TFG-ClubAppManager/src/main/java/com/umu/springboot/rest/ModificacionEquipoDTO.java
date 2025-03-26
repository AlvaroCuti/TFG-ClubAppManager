package com.umu.springboot.rest;

import java.util.List;

public class ModificacionEquipoDTO {
	private List<EntrenadorDTO> entrenadores;
	private List<JugadorDTO> jugadores;

	public ModificacionEquipoDTO(List<EntrenadorDTO> entrenadores, List<JugadorDTO> jugadores) {
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

	public List<JugadorDTO> getJugadores() {
		return jugadores;
	}

	public void setJugadores(List<JugadorDTO> jugadores) {
		this.jugadores = jugadores;
	}

}
