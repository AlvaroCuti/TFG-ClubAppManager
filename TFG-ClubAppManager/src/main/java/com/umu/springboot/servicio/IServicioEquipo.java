package com.umu.springboot.servicio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.umu.springboot.modelo.Entrenador;
import com.umu.springboot.modelo.Jugador;
import com.umu.springboot.modelo.Usuario;
import com.umu.springboot.rest.EntrenadorDTO;
import com.umu.springboot.rest.EquipoDTO;
import com.umu.springboot.rest.JugadorDTO;

public interface IServicioEquipo {
	
	String crearEquipo(List<Jugador> jugadores, List<Entrenador> entrenadores);
	
	String crearEquipo(List<Usuario> entrenadores);
	
	EquipoDTO getEquipo(String idEquipo);
	
	void modificarEquipo(String idEquipo, List<Usuario> entrenadores, List<Usuario> jugadores);
	
	void borrarEquipo(String idEquipo);
	
	Page<EquipoDTO> listarEquipos(Pageable paginacion);
	
	public List<Usuario> dtoToModelEntrenador (List<EntrenadorDTO> entrenadoresDTO);	
	
	public List<Usuario> dtoToModelJugador (List<JugadorDTO> jugadoresDTO);		

}
