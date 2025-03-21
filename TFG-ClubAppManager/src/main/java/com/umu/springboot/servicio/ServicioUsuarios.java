package com.umu.springboot.servicio;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.umu.springboot.modelo.Entrenador;
import com.umu.springboot.modelo.Equipo;
import com.umu.springboot.modelo.Jugador;
import com.umu.springboot.modelo.Usuario;
import com.umu.springboot.repositorios.RepositorioEquipoMongo;
import com.umu.springboot.repositorios.RepositorioUsuarioMongo;
import com.umu.springboot.rest.CreacionEntrenadorDTO;
import com.umu.springboot.rest.CreacionJugadorDTO;
import com.umu.springboot.rest.EntrenadorDTO;
import com.umu.springboot.rest.EquipoDTO;
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
	public JugadorInfoDTO descargarInfoUsuario(String idUsuario) { // TODO DTO de devolver
		if (idUsuario == null || idUsuario.isEmpty())
			return null;

		Jugador jugador = (Jugador) repositorioUsuario.findById(idUsuario).orElse(null);

		JugadorInfoDTO dto = new JugadorInfoDTO(jugador.getDniDelantera(), jugador.getDniTrasera(),
				jugador.getDniDelanteraTutor1(), jugador.getDniTraseraTutor1(), jugador.getDniDelanteraTutor2(),
				jugador.getDniTraseraTutor2());
		return dto;

	}

	@Override
	public Page<EntrenadorDTO> listaEntrenadores(Pageable paginacion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String crearEntrenador(String tel, String nombre, String fechaNac, String email, String pass,
			String dniDelantera, String dniTrasera, String certificadoDelitosSexuales) {

		if (tel == null || tel.isEmpty())
			return null;

		if (nombre == null || nombre.isEmpty())
			return null;

		if (fechaNac == null || fechaNac.isEmpty())
			return null;

		if (email == null || email.isEmpty())
			return null;

		if (pass == null || pass.isEmpty())
			return null;

		if (dniDelantera == null || dniDelantera.isEmpty())
			return null;

		if (dniTrasera == null || dniTrasera.isEmpty())
			return null;

		if (certificadoDelitosSexuales == null || certificadoDelitosSexuales.isEmpty())
			return null;

		Entrenador entrenador = new Entrenador(tel, nombre, LocalDate.parse(fechaNac), email, pass, "Entrenador",
				dniDelantera, dniTrasera, certificadoDelitosSexuales); // TODO pass generada para los entrenadores

		repositorioUsuario.save(entrenador);

		return entrenador.getTel();
	}

	@Override
	public EntrenadorDTO getEntrenador(String idUsuario) {
		if (idUsuario == null || idUsuario.isEmpty())
			return null;

		Usuario user = repositorioUsuario.findById(idUsuario).orElse(null);
		EntrenadorDTO dto = new EntrenadorDTO(user.getTel()); // TODO no esta bien
		return dto;
	}

	@Override
	public void modificarEntrenador(String tel, String nombre, String fechaNac, String email, String pass,
			String dniDelantera, String dniTrasera, String certificadoDelitosSexuales) {
		Usuario usuario = repositorioUsuario.findById(tel).orElse(null);
		((Entrenador) usuario).modificar(tel, nombre, LocalDate.parse(fechaNac), email, pass, dniDelantera, dniTrasera,
				certificadoDelitosSexuales);
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
