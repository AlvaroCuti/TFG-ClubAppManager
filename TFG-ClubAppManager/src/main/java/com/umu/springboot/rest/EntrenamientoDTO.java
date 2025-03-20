package com.umu.springboot.rest;

import com.umu.springboot.modelo.Asistencia;

public class EntrenamientoDTO {

	private String idEntrenamiento;
	private String horario;
	private String lugar;
	private String numAsistencias;

	public EntrenamientoDTO(String idEntrenamiento, String horario, String lugar, String numAsistencias) {
		this.idEntrenamiento = idEntrenamiento;
		this.horario = horario;
		this.lugar = lugar;
		this.numAsistencias = numAsistencias;
	}

	public EntrenamientoDTO() {
	}

	public String getIdEntrenamiento() {
		return idEntrenamiento;
	}

	public void setIdEntrenamiento(String idEntrenamiento) {
		this.idEntrenamiento = idEntrenamiento;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public String getNumAsistencias() {
		return numAsistencias;
	}

	public void setNumAsistencias(String numAsistencias) {
		this.numAsistencias = numAsistencias;
	}

}
