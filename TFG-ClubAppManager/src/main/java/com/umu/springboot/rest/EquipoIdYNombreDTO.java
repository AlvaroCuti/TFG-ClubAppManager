package com.umu.springboot.rest;

public class EquipoIdYNombreDTO {
	private String idEquipo;
	private String nombre;

	public EquipoIdYNombreDTO() {
		super();
	}

	public EquipoIdYNombreDTO(String idEquipo, String nombre) {
		super();
		this.idEquipo = idEquipo;
		this.nombre = nombre;
	}

	public String getIdEquipo() {
		return idEquipo;
	}

	public void setIdEquipo(String idEquipo) {
		this.idEquipo = idEquipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
