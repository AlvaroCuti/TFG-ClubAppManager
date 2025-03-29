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
	private List<Jugador> jugadores;
	private List<Entrenador> entrenadores;
	private List<Entrenamiento> entrenamientos;

	public Equipo(List<Jugador> jugadores, List<Entrenador> entrenadores) {
		this.idEquipo = null;
		this.jugadores = jugadores;
		this.entrenadores = entrenadores;
		this.entrenamientos = new LinkedList<Entrenamiento>();
	}

	public Equipo(List<Entrenador> entrenadores) {
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

	public void modificar(List<Entrenador> entrenadores, List<Jugador> jugadores) {
		this.entrenadores = entrenadores;
		this.jugadores = jugadores;
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
