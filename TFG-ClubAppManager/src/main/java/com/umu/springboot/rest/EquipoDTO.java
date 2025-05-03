package com.umu.springboot.rest;

import java.util.List;

public class EquipoDTO {
	private String nombre;
	private String categoria;
	private String idEquipo;
	private String numeroJugadores;
	private List<UsuarioElementoDTO> entrenadores;
	private List<UsuarioElementoDTO> jugadores;

	public EquipoDTO(String nombre, String categoria, String idEquipo, String numeroJugadores,
			List<UsuarioElementoDTO> entrenadores, List<UsuarioElementoDTO> jugadores) {
		super();
		this.nombre = nombre;
		this.categoria = categoria;
		this.idEquipo = idEquipo;
		this.numeroJugadores = numeroJugadores;
		this.entrenadores = entrenadores;
		this.jugadores = jugadores;
	}

	public EquipoDTO() {
		super();
	}

	public String getIdEquipo() {
		return idEquipo;
	}

	public void setIdEquipo(String idEquipo) {
		this.idEquipo = idEquipo;
	}

	public String getNumeroJugadores() {
		return numeroJugadores;
	}

	public void setNumeroJugadores(String numeroJugadores) {
		this.numeroJugadores = numeroJugadores;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public List<UsuarioElementoDTO> getEntrenadores() {
		return entrenadores;
	}

	public void setEntrenadores(List<UsuarioElementoDTO> entrenadores) {
		this.entrenadores = entrenadores;
	}

	public List<UsuarioElementoDTO> getJugadores() {
		return jugadores;
	}

	public void setJugadores(List<UsuarioElementoDTO> jugadores) {
		this.jugadores = jugadores;
	}
}
