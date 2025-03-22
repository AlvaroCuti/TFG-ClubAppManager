package com.umu.springboot.servicio;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

	Page<EntrenadorDTO> listaEntrenadores(Pageable paginacion);

	String crearEntrenador(String tel, String nombre, String fechaNac, String email, String pass, String dniDelantera,
			String dniTrasera, String certificadoDelitosSexuales);

	EntrenadorDTO getEntrenador(String idUsuario);

	void modificarEntrenador(String tel, String nombre, String fechaNac, String email, String pass, String dniDelantera,
			String dniTrasera, String certificadoDelitosSexuales);

	void borrarEntrenador(String idEntrenador);

}
