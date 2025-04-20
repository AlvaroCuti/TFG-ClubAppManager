package com.umu.springboot.rest;

public class ProgramacionEntrenamientoDTO {

	private String fecha;
	private String hora;
	private String lugar;

	public ProgramacionEntrenamientoDTO(String fecha, String hora, String lugar) {
		super();
		this.fecha = fecha;
		this.hora = hora;
		this.lugar = lugar;
	}

	public ProgramacionEntrenamientoDTO() {
		super();
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

}
