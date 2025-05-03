package com.umu.springboot.modelo;

import java.util.LinkedList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.umu.springboot.repositorios.Identificable;

@Document(collection = "equipo")
public class Equipo implements Identificable {
	@Id
	private String idEquipo;
	private String nombre;
	private Categoria categoria;
	private List<Jugador> jugadores;
	private List<Entrenador> entrenadores;
	private List<Entrenamiento> entrenamientos;

	public Equipo(String nombre, Categoria cat, List<Jugador> jugadores, List<Entrenador> entrenadores) {
		this.nombre = nombre;
		this.categoria = cat;
		this.idEquipo = null;
		this.jugadores = jugadores;
		this.entrenadores = entrenadores;
		this.entrenamientos = new LinkedList<Entrenamiento>();
	}

	public Equipo(String nombre, Categoria cat, List<Entrenador> entrenadores) {
		this.nombre = nombre;
		this.categoria = cat;
		this.idEquipo = null;
		this.jugadores = new LinkedList<Jugador>();
		this.entrenadores = entrenadores;
		this.entrenamientos = new LinkedList<Entrenamiento>();
	}

	public Equipo() {
	}

	public List<Jugador> getJugadores() {
		return jugadores;
	}

	public void setJugadores(List<Jugador> jugadores) {
		this.jugadores = jugadores;
	}

	public List<Entrenador> getEntrenadores() {
		return entrenadores;
	}

	public void setEntrenadores(List<Entrenador> entrenadores) {
		this.entrenadores = entrenadores;
	}

	public List<Entrenamiento> getEntrenamientos() {
		return entrenamientos;
	}

	public void setEntrenamientos(List<Entrenamiento> entrenamientos) {
		this.entrenamientos = entrenamientos;
	}

	public void addEntrenamiento(Entrenamiento entrenamiento) {
		this.entrenamientos.add(entrenamiento);
	}

	public void modificar(String nombre, List<Entrenador> entrenadores, List<Jugador> jugadores) {
		this.nombre = nombre;
		this.entrenadores = entrenadores;
		this.jugadores = jugadores;
	}
	
	public void addEntrenador(Entrenador e) {
		this.entrenadores.add(e);
	}

	public void removeEntrenador(String e) {
		entrenadores.removeIf(entrenador -> entrenador.getTel().equals(e));
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
	
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public void addJugador(Jugador jugador) {
		if (!this.jugadores.stream().anyMatch(j -> j.getTel().equals(jugador.getTel())))
			this.jugadores.add(jugador);
	}
	
	@Override
	public String getId() {
		return idEquipo;
	}

	@Override
	public void setId(String id) {
		this.idEquipo = id;
	}
}
