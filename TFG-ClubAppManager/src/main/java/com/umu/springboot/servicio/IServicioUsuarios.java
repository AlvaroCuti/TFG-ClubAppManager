package com.umu.springboot.servicio;

import java.util.Map;

import org.springframework.data.domain.Page;

import com.umu.springboot.modelo.Entrenador;
import com.umu.springboot.rest.CreacionEntrenadorDTO;
import com.umu.springboot.rest.EntrenadorDTO;
import com.umu.springboot.rest.JugadorInfoDTO;
import com.umu.springboot.rest.ModificacionEntrenadorDTO;

public interface IServicioUsuarios {

	Map<String, Object> verificarCredenciales(String idUsuario, String pass);

	String darDeAltaJugador(String tel, String nombre, String fechaNac, String email, String pass, String dniDelantera,
			String dniTrasera, String emailTutor1, String dniDelanteraTutor1, String dniTraseraTutor1,
			String emailTutor2, String dniDelanteraTutor2, String dniTraseraTutor2);

	void filtrarJugadores();
	
	JugadorInfoDTO descargarInfoUsuario(String idUsuario);

	Page<EntrenadorDTO> listaEntrenadores();
	
	Entrenador crearEntrenador(CreacionEntrenadorDTO crearEntrenadorDTO);

	void getEntrenador(String idUsuario);

	void modificarEntrenador(ModificacionEntrenadorDTO modificarEntrenadorDTO);

	void borrarEntrenador(String idEntrenador);

}
