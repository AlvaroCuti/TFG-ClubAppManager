package com.umu.springboot.modelo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.umu.springboot.repositorios.Identificable;

@Document(collection = "entrenamiento")
public class Entrenamiento implements Identificable {
	@Id
	private String idEntrenamiento;
	private LocalDateTime horario;
	private String lugar;
	private List<Asistencia> asistencias;

	public Entrenamiento(LocalDateTime horario, String lugar) {
		this.horario = horario;
		this.lugar = lugar;
		this.asistencias = null;
	}

	public LocalDateTime getHorario() {
		return horario;
	}

	public void setHorario(LocalDateTime horario) {
		this.horario = horario;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public List<Asistencia> getAsistencias() {
		return asistencias;
	}

	public void setAsistencias(List<Asistencia> asistencias) {
		this.asistencias = asistencias;
	}

	@Override
	public String getId() {
		return idEntrenamiento;
	}

	@Override
	public void setId(String id) {
		this.idEntrenamiento = id;
	}

}
