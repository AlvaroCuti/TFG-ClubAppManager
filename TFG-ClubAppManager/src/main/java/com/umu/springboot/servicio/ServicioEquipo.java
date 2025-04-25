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
import com.umu.springboot.rest.JugadorIdDTO;

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
			int numJugadores = (e.getJugadores() != null) ? e.getJugadores().size() : 0;
			String entrenadores = e.getEntrenadores().stream().map(Entrenador::getNombre)
					.collect(Collectors.joining(", "));
			equipoDTO.setNombre(e.getNombre());
			equipoDTO.setIdEquipo(e.getId());
			equipoDTO.setEntrenadores(entrenadores);
			equipoDTO.setNumeroJugadores(Integer.toString(numJugadores));
			return equipoDTO;
		});
	}

	@Override
	public String crearEquipo(String nombre, List<Jugador> jugadores, List<Entrenador> entrenadores) {

		if (jugadores == null || jugadores.isEmpty())
			return null;

		if (entrenadores == null || entrenadores.isEmpty())
			return null;

		Equipo equipo = new Equipo(nombre, jugadores, entrenadores);

		String idEquipo = repositorioEquipo.save(equipo).getId();

		return idEquipo;

	}

	@Override
	public String crearEquipo(String nombre, List<Usuario> usuarios) {

		if (usuarios == null || usuarios.isEmpty() || usuarios.contains(null))
			return null;

		Equipo equipo = new Equipo(nombre, usuarios.stream().map(Entrenador.class::cast).collect(Collectors.toList()));

		String idEquipo = repositorioEquipo.save(equipo).getId();

		usuarios.stream().filter(u -> u instanceof Entrenador).map(u -> (Entrenador) u).forEach(u -> {
			u.addEquipo(equipo.getId());
			repositorioUsuario.save((Usuario) u);
		});

		return idEquipo;
	}

	@Override
	public void addJugadorAEquipo(String idEquipo, String idJugador) {
		
		if (idEquipo == null || idEquipo.isEmpty())
			return;
		
		if (idJugador == null || idJugador.isEmpty())
			return;
		
		Equipo equipo = repositorioEquipo.findById(idEquipo).orElse(null);
		Usuario user = repositorioUsuario.findById(idJugador).orElse(null);
		Jugador j = (Jugador) user;
		
		if((equipo == null) || (user == null))
			return;
		
		if(j.getEquipo() == null) {
			equipo.addJugador(j);
			j.setEquipo(equipo.getId());
		}		
		repositorioUsuario.save(user);
		repositorioEquipo.save(equipo);
		
		return;
	}
	
	@Override
	public EquipoDTO getEquipo(String idEquipo) {

		if (idEquipo == null || idEquipo.isEmpty())
			return null;

		Equipo equipo = repositorioEquipo.findById(idEquipo).orElse(null);

		int numJugadores = (equipo.getJugadores() != null) ? equipo.getJugadores().size() : 0;

		String entrenadores = equipo.getEntrenadores().stream().map(Entrenador::getNombre)
				.collect(Collectors.joining(", "));

		EquipoDTO dto = new EquipoDTO(equipo.getNombre(), idEquipo, Integer.toString(numJugadores), entrenadores);
		return dto;

	}

	@Override
	public void modificarEquipo(String idEquipo, String nombre, List<Usuario> entrenadores, List<Usuario> jugadores) {

		if (idEquipo == null || idEquipo.isEmpty())
			return;

//		if (entrenadores == null || entrenadores.isEmpty() || entrenadores.contains(null))
//			return;
//
//		if (jugadores == null || jugadores.isEmpty() || jugadores.contains(null))
//			return;

		Equipo equipo = repositorioEquipo.findById(idEquipo).orElse(null);

		equipo.modificar(nombre, entrenadores.stream().map(Entrenador.class::cast).collect(Collectors.toList()),
				jugadores.stream().map(Jugador.class::cast).collect(Collectors.toList()));

		jugadores.stream().map(Jugador.class::cast).forEach(j -> {
			j.setEquipo(idEquipo);
		});

		for (Usuario usuario : jugadores) {
			repositorioUsuario.save(usuario);
		}

		entrenadores.stream().filter(u -> u instanceof Entrenador).map(u -> (Entrenador) u).forEach(u -> {
			u.addEquipo(equipo.getId());
			repositorioUsuario.save((Usuario) u);
		});
		
		repositorioEquipo.save(equipo);

		return;

	}

	@Override
	public void borrarEquipo(String idEquipo) {
		if (idEquipo == null || idEquipo.isEmpty())
			return;

		Equipo equipo = repositorioEquipo.findById(idEquipo).orElseGet(null);
		
		if(equipo == null)
			return;
		
		equipo.getJugadores().stream().forEach(j -> {
			j.borrarEquipo();
		});
		
		equipo.getEntrenadores().stream().forEach(e -> {
			e.borrarEquipo(equipo.getId());
		});
				
		repositorioEquipo.deleteById(idEquipo);
		return;
	}

	public List<Usuario> dtoToModelEntrenador(List<EntrenadorDTO> entrenadoresDTO) {
		List<Usuario> entrenadores = new LinkedList<Usuario>();
		for (EntrenadorDTO entrenador : entrenadoresDTO) {
			Usuario user = repositorioUsuario.findById(entrenador.getTel()).orElse(null);
			if (!user.getRol().equals("ENTRENADOR"))
				user = null;
			entrenadores.add(user);
		}
		return entrenadores;
	}

	public List<Usuario> dtoToModelJugador(List<JugadorIdDTO> jugadoresDTO) {
		List<Usuario> jugadores = new LinkedList<Usuario>();
		for (JugadorIdDTO jugador : jugadoresDTO) {
			jugadores.add(repositorioUsuario.findById(jugador.getTel()).orElse(null));
		}
		return jugadores;
	}

}
