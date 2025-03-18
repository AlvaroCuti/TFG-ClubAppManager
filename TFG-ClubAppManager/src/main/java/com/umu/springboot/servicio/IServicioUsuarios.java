package com.umu.springboot.servicio;

import com.umu.springboot.modelo.Entrenador;
import com.umu.springboot.modelo.Jugador;
import com.umu.springboot.modelo.Usuario;
import com.umu.springboot.rest.CreacionEntrenadorDTO;
import com.umu.springboot.rest.CreacionJugadorDTO;
import com.umu.springboot.rest.ModificacionEntrenadorDTO;
import com.umu.springboot.rest.VerificarUsuarioDTO;

public interface IServicioUsuarios {
	Jugador darDeAltaJugador(CreacionJugadorDTO crearUsuarioDTO);
	boolean verificarCredenciales(VerificarUsuarioDTO verificarDTO);
	
	Usuario descargarInfoUsuarios(String idUsuario);
	
	Entrenador crearEntrenador(CreacionEntrenadorDTO crearEntrenadorDTO);
	void getEntrenador(String idUsuario);
	void modificarEntrenador(ModificacionEntrenadorDTO modificarEntrenadorDTO);
	void borrarEntrenador(String idEtrenador);
	
	void filtrarJugadores();
	
}
