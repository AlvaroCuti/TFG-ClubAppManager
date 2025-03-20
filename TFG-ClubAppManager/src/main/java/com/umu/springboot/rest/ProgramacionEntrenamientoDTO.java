package com.umu.springboot.rest;

public class ProgramacionEntrenamientoDTO {

	private String fecha;
	private String lugar;

	public ProgramacionEntrenamientoDTO(String fecha, String lugar) {
		this.fecha = fecha;
		this.lugar = lugar;
	}

	public ProgramacionEntrenamientoDTO() {
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

}
