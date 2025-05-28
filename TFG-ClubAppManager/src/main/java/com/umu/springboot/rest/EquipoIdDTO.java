package com.umu.springboot.rest;

public class EquipoIdDTO {
	private String idEquipo;

	public EquipoIdDTO() {
	}

	public EquipoIdDTO(String idEquipo) {
		super();
		this.idEquipo = idEquipo;
	}

	public String getIdEquipo() {
		return idEquipo;
	}

	public void setIdEquipo(String idEquipo) {
		this.idEquipo = idEquipo;
	}

}
