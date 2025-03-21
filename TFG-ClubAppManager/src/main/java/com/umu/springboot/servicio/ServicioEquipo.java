package com.umu.springboot.servicio;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
import com.umu.springboot.rest.EntrenadorDTO;
import com.umu.springboot.rest.EquipoDTO;
import com.umu.springboot.rest.JugadorDTO;

@Service
@Transactional
public class ServicioEquipo implements IServicioEquipo {

	@Autowired
	private RepositorioEquipoMongo repositorioEquipo; // TODO no debe ser el repositorio concreto pero no funncionan las
														// funciones si no se pone mongo
	@Autowired
	private RepositorioUsuarioMongo repositorioUsuario;

	@Override
	public Page<EquipoDTO> listarEquipos(Pageable paginacion) {
		return repositorioEquipo.findAll(paginacion).map((Equipo e) -> {
			EquipoDTO equipoDTO = new EquipoDTO();
			equipoDTO.setIdEquipo(e.getId());
			equipoDTO.setEntrenadores(e.getEntrenadores().toString());
			equipoDTO.setNumeroJugadores(Integer.toString(e.getJugadores().size()));
			return equipoDTO;
		});
	}
	
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
	public String crearEquipo(List<Usuario> usuarios) {

		if (usuarios == null || usuarios.isEmpty())
			return null;

		Equipo equipo = new Equipo(usuarios.stream().map(Entrenador.class::cast).collect(Collectors.toList()));

		String idEquipo = repositorioEquipo.save(equipo).getId();

		return idEquipo;
	}

	@Override
	public EquipoDTO getEquipo(String idEquipo) {

		if (idEquipo == null || idEquipo.isEmpty())
			return null;

		Equipo equipo = repositorioEquipo.findById(idEquipo).orElse(null);
		EquipoDTO dto = new EquipoDTO(idEquipo, Integer.toString(equipo.getJugadores().size()),
				equipo.getEntrenadores().toString());
		return dto;

	}

	@Override
	public void modificarEquipo(String idEquipo, List<Usuario> entrenadores, List<Usuario> jugadores) {
		if (idEquipo == null || idEquipo.isEmpty())
			return;

		Equipo equipo = repositorioEquipo.findById(idEquipo).orElse(null);

		equipo.modificar(entrenadores.stream().map(Entrenador.class::cast).collect(Collectors.toList()), jugadores.stream().map(Jugador.class::cast).collect(Collectors.toList()));

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

	public List<Usuario> dtoToModelEntrenador(List<EntrenadorDTO> entrenadoresDTO) {
		List<Usuario> entrenadores = new LinkedList<Usuario>();
		for (EntrenadorDTO entrenador : entrenadoresDTO) {
			entrenadores.add(repositorioUsuario.findById(entrenador.getTel()).orElse(null));
		}
		return entrenadores;
	}

	public List<Usuario> dtoToModelJugador(List<JugadorDTO> jugadoresDTO) {
		List<Usuario> jugadores = new LinkedList<Usuario>();
		for (JugadorDTO jugador : jugadoresDTO) {
			jugadores.add(repositorioUsuario.findById(jugador.getTel()).orElse(null));
		}
		return jugadores;
	}
	
}
