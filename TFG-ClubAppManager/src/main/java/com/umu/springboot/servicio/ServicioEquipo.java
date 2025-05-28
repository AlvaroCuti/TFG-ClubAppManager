package com.umu.springboot.servicio;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.umu.springboot.modelo.Categoria;
import com.umu.springboot.modelo.Entrenador;
import com.umu.springboot.modelo.Equipo;
import com.umu.springboot.modelo.Jugador;
import com.umu.springboot.modelo.Usuario;
import com.umu.springboot.repositorios.EntidadNoEncontrada;
import com.umu.springboot.repositorios.RepositorioEquipoMongo;
import com.umu.springboot.repositorios.RepositorioUsuarioMongo;
import com.umu.springboot.rest.EntrenadorDTO;
import com.umu.springboot.rest.EquipoDTO;
import com.umu.springboot.rest.JugadorIdDTO;
import com.umu.springboot.rest.UsuarioElementoDTO;

@Service
@Transactional
public class ServicioEquipo implements IServicioEquipo {

	@Autowired
	private RepositorioEquipoMongo repositorioEquipo;

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
			equipoDTO.setCategoria(e.getCategoria().toString());
			equipoDTO.setIdEquipo(e.getId());
			equipoDTO.setEntrenadores(e.getEntrenadores().stream()
											.map(eq -> new UsuarioElementoDTO(eq.getTel(), eq.getNombre()))
											.collect(Collectors.toList()));
			
			equipoDTO.setNumeroJugadores(Integer.toString(numJugadores));
			return equipoDTO;
		});
	}

	@Override
	public String crearEquipo(String nombre, Categoria cat, List<Jugador> jugadores, List<Entrenador> entrenadores) throws IllegalArgumentException {

		if (jugadores == null || jugadores.isEmpty())
			throw new IllegalArgumentException("jugadores: no debe ser nulo ni vacio");
					
		if (entrenadores == null || entrenadores.isEmpty())
			throw new IllegalArgumentException("entrenadores: no debe ser nulo ni vacio");

		Equipo equipo = new Equipo(nombre, cat, jugadores, entrenadores);

		String idEquipo = repositorioEquipo.save(equipo).getId();

		return idEquipo;

	}

	@Override
	public String crearEquipo(String nombre, Categoria cat,  List<Usuario> usuarios) throws IllegalArgumentException{

		if (usuarios == null || usuarios.isEmpty() || usuarios.contains(null))
			throw new IllegalArgumentException("usuarios: no debe ser nulo ni vacio");

		Equipo equipo = new Equipo(nombre, cat ,usuarios.stream().map(Entrenador.class::cast).collect(Collectors.toList()));

		String idEquipo = repositorioEquipo.save(equipo).getId();

		usuarios.stream().filter(u -> u instanceof Entrenador).map(u -> (Entrenador) u).forEach(u -> {
			u.addEquipo(equipo.getId());
			repositorioUsuario.save((Usuario) u);
		});

		return idEquipo;
	}

	@Override
	public void addJugadorAEquipo(String idEquipo, String idJugador) throws IllegalArgumentException, EntidadNoEncontrada{
		
		if (idEquipo == null || idEquipo.isEmpty())
			throw new IllegalArgumentException("idEquipo: no debe ser nulo ni vacio");
		
		if (idJugador == null || idJugador.isEmpty())
			throw new IllegalArgumentException("idJugador: no debe ser nulo ni vacio");
		
		Equipo equipo = repositorioEquipo.findById(idEquipo).orElse(null);
		Usuario user = repositorioUsuario.findById(idJugador).orElse(null);
		Jugador j = (Jugador) user;
		
		if((equipo == null))
			throw new EntidadNoEncontrada("equipo: no existe el id");
		
		if((user == null))
			throw new EntidadNoEncontrada("equipo: no existe el id");
		
		if(j.getEquipo() == null) {
			equipo.addJugador(j);
			j.setEquipo(equipo.getId());
		}		
		repositorioUsuario.save(user);
		repositorioEquipo.save(equipo);
		
		return;
	}
	
	@Override
	public void removeUsuarioDeEquipo(String idEquipo, String idUsuario) throws IllegalArgumentException, EntidadNoEncontrada{
		
		if (idEquipo == null || idEquipo.isEmpty())
			throw new IllegalArgumentException("idEquipo: no debe ser nulo ni vacio");
		
		if (idUsuario == null || idUsuario.isEmpty())
			throw new IllegalArgumentException("idUsuario: no debe ser nulo ni vacio");
		
		Equipo equipo = repositorioEquipo.findById(idEquipo).orElse(null);
		Usuario user = repositorioUsuario.findById(idUsuario).orElse(null);
		
		if((equipo == null))
			throw new EntidadNoEncontrada("equipo: no existe el id");
		
		if((user == null))
			throw new EntidadNoEncontrada("equipo: no existe el id");
		
		if(user.getRol().equals("ENTRENADOR")) {
			Entrenador  e = (Entrenador) user;
			e.borrarEquipo(idEquipo);
			equipo.removeEntrenador(idUsuario);
		}else {
			Jugador j = (Jugador) user;
			j.borrarEquipo();
			equipo.removeJugadores(idUsuario);
		}
		
		repositorioUsuario.save(user);
		repositorioEquipo.save(equipo);
		
		return;
		
	}
	
	@Override
	public EquipoDTO getEquipo(String idEquipo) throws IllegalArgumentException{

		if (idEquipo == null || idEquipo.isEmpty())
			throw new IllegalArgumentException("idEquipo: no debe ser nulo ni vacio");

		Equipo equipo = repositorioEquipo.findById(idEquipo).orElse(null);

		int numJugadores = (equipo.getJugadores() != null) ? equipo.getJugadores().size() : 0;

		List<UsuarioElementoDTO> entrenadores = equipo.getEntrenadores().stream()
			.map(e -> new UsuarioElementoDTO(e.getTel(), e.getNombre()))
			.collect(Collectors.toList());
		
		List<UsuarioElementoDTO> jugadores = equipo.getJugadores().stream()
				.map(e -> new UsuarioElementoDTO(e.getTel(), e.getNombre()))
				.collect(Collectors.toList());
		
		EquipoDTO dto = new EquipoDTO(equipo.getNombre(),equipo.getCategoria().toString(), idEquipo, Integer.toString(numJugadores), entrenadores, jugadores);
		return dto;

	}

	@Override
	public void modificarEquipo(String idEquipo, String nombre, List<Usuario> entrenadores, List<Usuario> jugadores) throws IllegalArgumentException{

		if (idEquipo == null || idEquipo.isEmpty())
			throw new IllegalArgumentException("idEquipo: no debe ser nulo ni vacio");

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
	public void borrarEquipo(String idEquipo) throws IllegalArgumentException, EntidadNoEncontrada{
		if (idEquipo == null || idEquipo.isEmpty())
			throw new IllegalArgumentException("idEquipo: no debe ser nulo ni vacio");


		Equipo equipo = repositorioEquipo.findById(idEquipo).orElseGet(null);
		
		if(equipo == null)
			throw new EntidadNoEncontrada("equipo: no existe el id");
		
		equipo.getJugadores().stream().filter(e -> repositorioUsuario.existsById(e.getTel())).forEach(j -> {
			j.borrarEquipo();
			repositorioUsuario.save((Usuario)j);
		});
		
		equipo.getEntrenadores().stream().filter(e -> repositorioUsuario.existsById(e.getTel())).forEach(e -> {
			e.borrarEquipo(equipo.getId());
			repositorioUsuario.save((Entrenador)e);
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
