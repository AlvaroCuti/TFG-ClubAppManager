package com.umu.springboot.servicio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.umu.springboot.modelo.Categoria;
import com.umu.springboot.modelo.Entrenador;
import com.umu.springboot.modelo.Jugador;
import com.umu.springboot.modelo.Usuario;
import com.umu.springboot.repositorios.EntidadNoEncontrada;
import com.umu.springboot.rest.EntrenadorDTO;
import com.umu.springboot.rest.EquipoDTO;
import com.umu.springboot.rest.JugadorIdDTO;

public interface IServicioEquipo {
	
	Page<EquipoDTO> listarEquipos(Pageable paginacion);
	
	String crearEquipo(String nombre, Categoria cat, List<Jugador> jugadores, List<Entrenador> entrenadores) throws IllegalArgumentException;
	
	String crearEquipo(String nombre, Categoria cat, List<Usuario> entrenadores) throws IllegalArgumentException;
	
	void addJugadorAEquipo(String idEquipo, String idJugador) throws IllegalArgumentException, EntidadNoEncontrada;
	
	void removeUsuarioDeEquipo(String idEquipo, String idJugador) throws IllegalArgumentException, EntidadNoEncontrada;
	
	EquipoDTO getEquipo(String idEquipo) throws IllegalArgumentException;
	
	void modificarEquipo(String idEquipo, String nombre, List<Usuario> entrenadores, List<Usuario> jugadores) throws IllegalArgumentException;
	
	void borrarEquipo(String idEquipo) throws IllegalArgumentException, EntidadNoEncontrada;
		
	List<Usuario> dtoToModelEntrenador (List<EntrenadorDTO> entrenadoresDTO);	
	
	List<Usuario> dtoToModelJugador (List<JugadorIdDTO> jugadoresDTO);		

}


