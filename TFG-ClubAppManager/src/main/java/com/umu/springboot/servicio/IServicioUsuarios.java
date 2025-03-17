package com.umu.springboot.servicio;

import com.umu.springboot.modelo.Jugador;
import com.umu.springboot.modelo.Usuario;
import com.umu.springboot.rest.CreacionUsuarioDTO;
import com.umu.springboot.rest.VerificarUsuarioDTO;

public interface IServicioUsuarios {
	Jugador darDeAltaJugador(CreacionUsuarioDTO crearUsuarioDTO);
	boolean verificarCredenciales(VerificarUsuarioDTO verificarDTO);
	
	Usuario descargarInfoUsuarios(String idUsuario);
	
	void crearEntrenador();
	void getEntrenador(String idUsuario);
	void modificarEntrenador();
	void borrarEntrenador();
	
	void filtrarJugadores();
	
}
