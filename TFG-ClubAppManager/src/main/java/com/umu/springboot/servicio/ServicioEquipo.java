package com.umu.springboot.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.umu.springboot.modelo.Entrenador;
import com.umu.springboot.modelo.Equipo;
import com.umu.springboot.modelo.Jugador;
import com.umu.springboot.repositorios.RepositorioEquipoMongo;
import com.umu.springboot.repositorios.RepositorioUsuarioMongo;

public class ServicioEquipo implements IServicioEquipo {

	@Autowired
	private RepositorioEquipoMongo repositorioEquipo; // TODO
	@Autowired
	private RepositorioUsuarioMongo repositorioUsuario; // TODO

	@Override
	public String crearEquipo(List<Jugador> jugadores, List<Entrenador> entrenadores) {

		if (jugadores == null || jugadores.isEmpty())
			return null;

		if (entrenadores == null || entrenadores.isEmpty())
			return null;

		Equipo equipo = new Equipo(jugadores, entrenadores);

		String idEquipo = repositorioEquipo.save(equipo).getId();

		return idEquipo;

	}

	@Override
	public String crearEquipo(List<Entrenador> entrenadores) {

		if (entrenadores == null || entrenadores.isEmpty())
			return null;

		Equipo equipo = new Equipo(entrenadores);

		String idEquipo = repositorioEquipo.save(equipo).getId();

		return idEquipo;
	}

	@Override
	public Equipo getEquipo(String idEquipo) {

		if (idEquipo == null || idEquipo.isEmpty())
			return null;

		return repositorioEquipo.findById(idEquipo).orElse(null);

	}

	@Override
	public void modificarEquipo() {

	}

	@Override
	public void borrarEquipo(String idEquipo) {
		if (idEquipo == null || idEquipo.isEmpty())
			return;

		repositorioEquipo.deleteById(idEquipo);
		return;
	}

	@Override
	public List<Equipo> listarEquipos() {
		return repositorioEquipo.findAll();
	}

}
