package com.umu.springboot.servicio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.umu.springboot.modelo.Entrenador;
import com.umu.springboot.modelo.Jugador;
import com.umu.springboot.modelo.Usuario;
import com.umu.springboot.rest.EntrenadorDTO;
import com.umu.springboot.rest.EquipoDTO;
import com.umu.springboot.rest.JugadorIdDTO;

public interface IServicioEquipo {
	
	Page<EquipoDTO> listarEquipos(Pageable paginacion);
	
	String crearEquipo(String nombre, List<Jugador> jugadores, List<Entrenador> entrenadores);
	
	String crearEquipo(String nombre, List<Usuario> entrenadores);
	
	EquipoDTO getEquipo(String idEquipo);
	
	void modificarEquipo(String idEquipo, String nombre, List<Usuario> entrenadores, List<Usuario> jugadores);
	
	void borrarEquipo(String idEquipo);
		
	public List<Usuario> dtoToModelEntrenador (List<EntrenadorDTO> entrenadoresDTO);	
	
	public List<Usuario> dtoToModelJugador (List<JugadorIdDTO> jugadoresDTO);		

}
