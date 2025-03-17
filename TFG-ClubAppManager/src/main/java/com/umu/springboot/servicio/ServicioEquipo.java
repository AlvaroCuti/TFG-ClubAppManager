package com.umu.springboot.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.umu.springboot.modelo.Entrenador;
import com.umu.springboot.modelo.Equipo;
import com.umu.springboot.modelo.Jugador;
import com.umu.springboot.repositorios.RepositorioEquipoMongo;

public class ServicioEquipo implements IServicioEquipo {

	@Autowired
	private RepositorioEquipoMongo repositorioEquipo; // TODO

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
	public void modificarEquipo(String idEquipo, List<Entrenador> entrenadores, List<Jugador> jugadores) {
		if (idEquipo == null || idEquipo.isEmpty())
			return;

		Equipo equipo = repositorioEquipo.findById(idEquipo).orElse(null);

		equipo.modificar(entrenadores, jugadores);

		repositorioEquipo.save(equipo);

		return;

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
