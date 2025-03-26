package com.umu.springboot.servicio;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.umu.springboot.modelo.Entrenador;
import com.umu.springboot.modelo.Jugador;
import com.umu.springboot.modelo.Usuario;
import com.umu.springboot.repositorios.RepositorioEquipo;
import com.umu.springboot.repositorios.RepositorioEquipoMongo;
import com.umu.springboot.repositorios.RepositorioUsuario;
import com.umu.springboot.repositorios.RepositorioUsuarioMongo;
import com.umu.springboot.rest.EntrenadorDTO;
import com.umu.springboot.rest.JugadorDTO;
import com.umu.springboot.rest.JugadorInfoDTO;
import com.umu.springboot.utils.JwtUtilidades;

@Service
@Transactional
public class ServicioUsuarios implements IServicioUsuarios {

	@Autowired
	private RepositorioEquipo repositorioEquipo; // TODO

	@Autowired
	private RepositorioUsuario repositorioUsuario; // TODO

	@Autowired
	private JwtUtilidades utilidadesJWT;

	public ServicioUsuarios() {
	}

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

		if (tel == null || tel.isEmpty())
			throw new IllegalArgumentException("tel: no debe ser nulo ni vacio");

		if (nombre == null || nombre.isEmpty())
			throw new IllegalArgumentException("nombre: no debe ser negativo");

		if (fechaNac == null || fechaNac.isEmpty())
			throw new IllegalArgumentException("fechaNac: no debe ser negativo");

		if (email == null || email.isEmpty())
			throw new IllegalArgumentException("email: no debe ser negativo");

		if (pass == null || pass.isEmpty())
			throw new IllegalArgumentException("pass: no debe ser negativo");
		
		if (dniDelantera == null || dniDelantera.isEmpty())
			throw new IllegalArgumentException("dniDelantera: no debe ser negativo");
		
		if (dniTrasera == null || dniTrasera.isEmpty())
			throw new IllegalArgumentException("dniTrasera: no debe ser negativo");
		
		if (emailTutor1 == null || emailTutor1.isEmpty())
			throw new IllegalArgumentException("emailTutor1: no debe ser negativo");
		
		if (dniDelanteraTutor1 == null || dniDelanteraTutor1.isEmpty())
			throw new IllegalArgumentException("dniDelanteraTutor1: no debe ser negativo");
		
		if (dniTraseraTutor1 == null || dniTraseraTutor1.isEmpty())
			throw new IllegalArgumentException("dniTraseraTutor1: no debe ser negativo");
		
		if (emailTutor2 == null || emailTutor2.isEmpty())
			throw new IllegalArgumentException("emailTutor2: no debe ser negativo");
		
		if (dniDelanteraTutor2 == null || dniDelanteraTutor2.isEmpty())
			throw new IllegalArgumentException("dniDelanteraTutor2: no debe ser negativo");

		if (dniTraseraTutor2 == null || dniTraseraTutor2.isEmpty())
			throw new IllegalArgumentException("dniTraseraTutor2: no debe ser negativo");

		if (repositorioUsuario.existsById(tel))
			return null;
		
		Jugador jugador = new Jugador(tel, nombre, LocalDate.parse(fechaNac), email, pass, "JUGADOR", dniDelantera,
				dniTrasera, emailTutor1, dniDelanteraTutor1, dniTraseraTutor1, emailTutor2, dniDelanteraTutor2,
				dniTraseraTutor2);
		
		repositorioUsuario.save(jugador);
		return jugador.getTel();
	}

	@Override
	public Page<JugadorDTO> filtrarJugadores(String nombre, String tel, String fechaNac, String email,
			String emailTutor1, String emailTutor2, Pageable paginacion) {
		Page<Usuario> usuarios = repositorioUsuario.filtrarUsuarios(nombre, tel, fechaNac, email, emailTutor1,
				emailTutor2, paginacion);

		Page<JugadorDTO> jugadores = usuarios.map(usuario -> new JugadorDTO((Jugador) usuario));

		return jugadores;
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
