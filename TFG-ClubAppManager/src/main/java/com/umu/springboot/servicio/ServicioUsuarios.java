package com.umu.springboot.servicio;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.umu.springboot.modelo.Entrenador;
import com.umu.springboot.modelo.Jugador;
import com.umu.springboot.modelo.Usuario;
import com.umu.springboot.repositorios.RepositorioEquipoMongo;
import com.umu.springboot.repositorios.RepositorioUsuarioMongo;
import com.umu.springboot.rest.CreacionEntrenadorDTO;
import com.umu.springboot.rest.CreacionJugadorDTO;
import com.umu.springboot.rest.EntrenadorDTO;
import com.umu.springboot.rest.JugadorInfoDTO;
import com.umu.springboot.rest.ModificacionEntrenadorDTO;
import com.umu.springboot.rest.VerificarUsuarioDTO;

import io.jsonwebtoken.Claims;
import utils.JwtUtilidades;

@Service
@Transactional
public class ServicioUsuarios implements IServicioUsuarios {

	@Autowired
	private RepositorioEquipoMongo repositorioEquipo; // TODO
	@Autowired
	private RepositorioUsuarioMongo repositorioUsuario; // TODO

	private JwtUtilidades utilidadesJWT;

	@Override
	public Map<String, Object> verificarCredenciales(String idUsuario, String pass) {
		Usuario usuario = repositorioUsuario.findById(idUsuario).orElseGet(null);
		if (usuario == null)
			return null;

		Map<String, Object> claimsUsuario = utilidadesJWT.usuarioAClaims(usuario);

		String tokenJWT = utilidadesJWT.generacionTokenJWT(claimsUsuario);
		claimsUsuario.put("token", tokenJWT);

		return claimsUsuario;
	}

	@Override
	public String darDeAltaJugador(String tel, String nombre, String fechaNac, String email, String pass,
			String dniDelantera, String dniTrasera, String emailTutor1, String dniDelanteraTutor1,
			String dniTraseraTutor1, String emailTutor2, String dniDelanteraTutor2, String dniTraseraTutor2) {
		Jugador jugador = new Jugador(tel, nombre, LocalDate.parse(fechaNac), email, pass, "Jugador", dniDelantera,
				dniTrasera, emailTutor1, dniDelanteraTutor1, dniTraseraTutor1, emailTutor2, dniDelanteraTutor2,
				dniTraseraTutor2);
		repositorioUsuario.save(jugador);
		return jugador.getTel();
	}

	@Override
	public void filtrarJugadores() {
		// TODO Auto-generated method stub

	}

	@Override
	public JugadorInfoDTO descargarInfoUsuario(String idUsuario) { 				// TODO DTO de devolver
		if (idUsuario == null || idUsuario.isEmpty())
			return null;

		Jugador jugador = (Jugador) repositorioUsuario.findById(idUsuario).orElse(null);

		JugadorInfoDTO dto = new JugadorInfoDTO(jugador.getDniDelantera(), jugador.getDniTrasera(),
				jugador.getDniDelanteraTutor1(), jugador.getDniTraseraTutor1(), jugador.getDniDelanteraTutor2(),
				jugador.getDniTraseraTutor2());
		return dto;

	}

	@Override
	public Page<EntrenadorDTO> listaEntrenadores() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entrenador crearEntrenador(CreacionEntrenadorDTO crearEntrenadorDTO) {
		Entrenador entrenador = new Entrenador(crearEntrenadorDTO.getTel(), crearEntrenadorDTO.getNombre(),
				crearEntrenadorDTO.getFechaNac(), crearEntrenadorDTO.getEmail(), crearEntrenadorDTO.getPass(),
				"Entrenador", crearEntrenadorDTO.getDniDelantera(), crearEntrenadorDTO.getDniTrasera(),
				crearEntrenadorDTO.getCertificadoDelitosSexuales()); // TODO pass generada para los entrenadores
		repositorioUsuario.save(entrenador);

		return entrenador;
	}

	@Override
	public void getEntrenador(String idUsuario) {

	}

	@Override
	public void modificarEntrenador(ModificacionEntrenadorDTO modificarEntrenadorDTO) {
		Usuario usuario = repositorioUsuario.findById(modificarEntrenadorDTO.getTel()).orElse(null);
		((Entrenador) usuario).modificar(modificarEntrenadorDTO.getTel(), modificarEntrenadorDTO.getNombre(),
				modificarEntrenadorDTO.getFechaNac(), modificarEntrenadorDTO.getEmail(),
				modificarEntrenadorDTO.getPass(), modificarEntrenadorDTO.getDniDelantera(),
				modificarEntrenadorDTO.getDniTrasera(), modificarEntrenadorDTO.getCertificadoDelitosSexuales());
		repositorioUsuario.save(usuario);
		return;
	}

	@Override
	public void borrarEntrenador(String idEntrenador) {
		if (idEntrenador == null || idEntrenador.isEmpty())
			return;

		repositorioUsuario.deleteById(idEntrenador);

		return;
	}

}
