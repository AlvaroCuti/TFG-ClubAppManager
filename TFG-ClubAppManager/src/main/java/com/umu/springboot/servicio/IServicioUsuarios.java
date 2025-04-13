package com.umu.springboot.servicio;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.umu.springboot.rest.EntrenadorCompletoDTO;
import com.umu.springboot.rest.EntrenadorDTO;
import com.umu.springboot.rest.JugadorDTO;
import com.umu.springboot.rest.JugadorInfoDTO;

public interface IServicioUsuarios {

	Map<String, Object> verificarCredenciales(String idUsuario, String pass);

	String darDeAltaJugador(String tel, String nombre, String fechaNac, String email, String pass, long dniDelantera,
			long dniTrasera, String emailTutor1, long dniDelanteraTutor1, long dniTraseraTutor1,
			String emailTutor2, long dniDelanteraTutor2, long dniTraseraTutor2);

	Page<JugadorDTO> filtrarJugadores(String nombre, String tel, String fechaNac, String email, String emailTutor1,
			String emailTutor2, Pageable paginacion);

	JugadorInfoDTO descargarInfoUsuario(String idUsuario);

	Page<EntrenadorCompletoDTO> listaEntrenadores(Pageable paginacion);

	String crearEntrenador(String tel, String nombre, String fechaNac, String email, String pass, String dniDelantera,
			String dniTrasera, String certificadoDelitosSexuales);

	EntrenadorDTO getEntrenador(String idEntrenador);

	void modificarEntrenador(String telAntiguo, String telNuevo, String nombre, String fechaNac, String email, String pass, String dniDelantera,
			String dniTrasera, String certificadoDelitosSexuales);

	void borrarEntrenador(String idEntrenador);

}
