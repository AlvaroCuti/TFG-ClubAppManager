package com.umu.springboot.rest;

public class AsistenciaDTO {
	private String idJugador;
	private String idEntrenamiento;

	public AsistenciaDTO() {
	}

	public AsistenciaDTO(String idJugador, String idEntrenamiento) {
		this.idJugador = idJugador;
		this.idEntrenamiento = idEntrenamiento;
	}

	public String getIdJugador() {
		return idJugador;
	}

	public void setIdJugador(String idJugador) {
		this.idJugador = idJugador;
	}

	public String getIdEntrenamiento() {
		return idEntrenamiento;
	}

	public void setIdEntrenamiento(String idEntrenamiento) {
		this.idEntrenamiento = idEntrenamiento;
	}

}
