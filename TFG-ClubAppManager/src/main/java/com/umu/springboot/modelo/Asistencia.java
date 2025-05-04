package com.umu.springboot.modelo;

public class Asistencia {
	private String idJugador;
	private String idEntrenamiento;

	public Asistencia() {
	}

	public Asistencia(String idJugador, String idEntrenamiento) {
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

	public boolean comprobarAsistencia(String idEntrenamiento, String idUsuario) {
		if((this.idEntrenamiento.equals(idEntrenamiento))&&(this.idJugador.equals(idUsuario)))
			return true;
		return false;
	}
}
