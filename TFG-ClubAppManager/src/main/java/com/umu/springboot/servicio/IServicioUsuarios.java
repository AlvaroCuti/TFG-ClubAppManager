package com.umu.springboot.servicio;

public interface IServicioUsuarios {
	void darDeAltaUsuario();
	void verificarCredenciales();
	
	void descargarInfoUsuarios();
	
	void crearEntrenador();
	void obtenerEntrenador();
	void modificarEntrenador();
	void borrarEntrenador();
	
	void filtrarJugadores();
	
}
