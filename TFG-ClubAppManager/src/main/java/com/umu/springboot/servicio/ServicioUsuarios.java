package com.umu.springboot.servicio;

import org.springframework.beans.factory.annotation.Autowired;

import com.umu.springboot.modelo.Jugador;
import com.umu.springboot.modelo.Usuario;
import com.umu.springboot.repositorios.RepositorioEquipoMongo;
import com.umu.springboot.repositorios.RepositorioUsuarioMongo;
import com.umu.springboot.rest.CreacionUsuarioDTO;
import com.umu.springboot.rest.VerificarUsuarioDTO;

public class ServicioUsuarios implements IServicioUsuarios {

	@Autowired
	private RepositorioEquipoMongo repositorioEquipo; // TODO
	@Autowired
	private RepositorioUsuarioMongo repositorioUsuario; // TODO

	@Override
	public Jugador darDeAltaJugador(CreacionUsuarioDTO crearUsuarioDTO) {
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
		if(usuario == null)
			return false;
		
		if (!usuario.getPass().equals(verificarDTO.getPass()))
			return false;
		return true;
	}

	@Override
	public Usuario descargarInfoUsuarios(String idUsuario) {
		if (idUsuario == null || idUsuario.isEmpty())
			return null;

		Usuario user = repositorioUsuario.findById(idUsuario).orElse(null);

		return user;

	}

	@Override
	public void crearEntrenador() {
		// TODO Auto-generated method stub

	}

	@Override
	public void getEntrenador(String idUsuario) {

	}

	@Override
	public void modificarEntrenador() {
		// TODO Auto-generated method stub

	}

	@Override
	public void borrarEntrenador() {
		// TODO Auto-generated method stub

	}

	@Override
	public void filtrarJugadores() {
		// TODO Auto-generated method stub

	}

}
