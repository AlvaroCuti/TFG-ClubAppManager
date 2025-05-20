package com.umu.springboot.servicio;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.umu.springboot.repositorios.EntidadNoEncontrada;
import com.umu.springboot.rest.EntrenadorCompletoDTO;
import com.umu.springboot.rest.EntrenadorDTO;
import com.umu.springboot.rest.EquipoIdDTO;
import com.umu.springboot.rest.EquiposIdsDTO;
import com.umu.springboot.rest.JugadorDTO;
import com.umu.springboot.rest.JugadorInfoDTO;

public interface IServicioUsuarios {

	Map<String, Object> verificarCredenciales(String idUsuario, String pass);

	String darDeAltaJugador(String tel, String nombre, String fechaNac, String email, String pass, long dniDelantera,
			long dniTrasera, String emailTutor1, long dniDelanteraTutor1, long dniTraseraTutor1,
			String emailTutor2, long dniDelanteraTutor2, long dniTraseraTutor2) throws IllegalArgumentException;

	Page<JugadorDTO> filtrarJugadores(String nombre, String tel, String fechaNac, String email, String emailTutor1,
			String emailTutor2, Pageable paginacion);

	JugadorInfoDTO descargarInfoUsuario(String idUsuario) throws IllegalArgumentException, EntidadNoEncontrada;
	
	EquipoIdDTO getEquipoDeJugador(String idUsuario) throws IllegalArgumentException, EntidadNoEncontrada;

	Page<EntrenadorCompletoDTO> listaEntrenadores(Pageable paginacion);

	String crearEntrenador(String tel, String nombre, String fechaNac, String email, String pass, long dniDelantera,
			long dniTrasera, long certificadoDelitosSexuales) throws IllegalArgumentException, EntidadNoEncontrada;

	EntrenadorDTO getEntrenador(String idEntrenador) throws IllegalArgumentException, EntidadNoEncontrada;
	
	EquiposIdsDTO getEquiposDeEntrenador(String idEntrenador) throws IllegalArgumentException, EntidadNoEncontrada;
	
	void modificarEntrenador(String telAntiguo, String telNuevo, String nombre, String fechaNac, String email, String pass, long dniDelantera,
			long dniTrasera, long certificadoDelitosSexuales);

	boolean cambiarPass(String idusuario, String oldPass, String newPass) throws IllegalArgumentException, EntidadNoEncontrada;
	
	void borrarEntrenador(String idEntrenador) throws IllegalArgumentException;

	
}


