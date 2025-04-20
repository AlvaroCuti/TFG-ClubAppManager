package com.umu.springboot.rest;

import java.util.List;

public class EntrenamientoDTO {

	private String idEntrenamiento;
	private String horario;
	private String lugar;
	private List<AsistenciaDTO> asistencias;

	public EntrenamientoDTO(String idEntrenamiento, String horario, String lugar, List<AsistenciaDTO> asistencias) {
		this.idEntrenamiento = idEntrenamiento;
		this.horario = horario;
		this.lugar = lugar;
		this.asistencias = asistencias;
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

	public List<AsistenciaDTO> getAsistencias() {
		return asistencias;
	}

	public void setAsistencias(List<AsistenciaDTO> asistencias) {
		this.asistencias = asistencias;
	}
}
