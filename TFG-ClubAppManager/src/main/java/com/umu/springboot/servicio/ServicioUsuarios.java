package com.umu.springboot.servicio;

import org.springframework.beans.factory.annotation.Autowired;

import com.umu.springboot.modelo.Entrenador;
import com.umu.springboot.modelo.Jugador;
import com.umu.springboot.modelo.Usuario;
import com.umu.springboot.repositorios.RepositorioEquipoMongo;
import com.umu.springboot.repositorios.RepositorioUsuarioMongo;
import com.umu.springboot.rest.CreacionEntrenadorDTO;
import com.umu.springboot.rest.CreacionJugadorDTO;
import com.umu.springboot.rest.ModificacionEntrenadorDTO;
import com.umu.springboot.rest.VerificarUsuarioDTO;

public class ServicioUsuarios implements IServicioUsuarios {

	@Autowired
	private RepositorioEquipoMongo repositorioEquipo; // TODO
	@Autowired
	private RepositorioUsuarioMongo repositorioUsuario; // TODO

	@Override
	public Jugador darDeAltaJugador(CreacionJugadorDTO crearUsuarioDTO) {
		Jugador jugador = new Jugador(crearUsuarioDTO.getTel(), crearUsuarioDTO.getNombre(),
				crearUsuarioDTO.getFechaNac(), crearUsuarioDTO.getEmail(), crearUsuarioDTO.getPass(), "Jugador",
				crearUsuarioDTO.getDniDelantera(), crearUsuarioDTO.getDniTrasera(), crearUsuarioDTO.getEmailTutor1(),
				crearUsuarioDTO.getDniDelanteraTutor1(), crearUsuarioDTO.getDniTraseraTutor1(),
				crearUsuarioDTO.getEmailTutor2(), crearUsuarioDTO.getDniDelanteraTutor2(),
				crearUsuarioDTO.getDniTraseraTutor2());
		repositorioUsuario.save(jugador);
		return jugador;
	}

	@Override
	public boolean verificarCredenciales(VerificarUsuarioDTO verificarDTO) {
		Usuario usuario = repositorioUsuario.findById(verificarDTO.getIdTelefono()).orElseGet(null);
		if (usuario == null)
			return false;

		if (!usuario.getPass().equals(verificarDTO.getPass()))
			return false;
		return true;
	}

	@Override
	public Usuario descargarInfoUsuarios(String idUsuario) { // TODO DTO de devolver
		if (idUsuario == null || idUsuario.isEmpty())
			return null;

		Usuario user = repositorioUsuario.findById(idUsuario).orElse(null);

		return user;

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

	@Override
	public void filtrarJugadores() {
		// TODO Auto-generated method stub

	}

}
