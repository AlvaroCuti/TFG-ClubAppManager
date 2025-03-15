package com.umu.springboot.servicio;

import java.util.List;

import com.umu.springboot.modelo.Entrenador;
import com.umu.springboot.modelo.Equipo;
import com.umu.springboot.modelo.Jugador;

public interface IServicioEquipo {
	
	String crearEquipo(List<Jugador> jugadores, List<Entrenador> entrenadores);
	
	String crearEquipo(List<Entrenador> entrenadores);
	
	Equipo getEquipo(String idEquipo);
	
	void modificarEquipo();
	
	void borrarEquipo(String idEquipo);
	
	List<Equipo> listarEquipos();
}
