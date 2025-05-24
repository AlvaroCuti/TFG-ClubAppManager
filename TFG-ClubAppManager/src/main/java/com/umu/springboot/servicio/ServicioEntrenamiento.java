package com.umu.springboot.servicio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.umu.springboot.modelo.Asistencia;
import com.umu.springboot.modelo.Entrenamiento;
import com.umu.springboot.modelo.Equipo;
import com.umu.springboot.modelo.Jugador;
import com.umu.springboot.modelo.Usuario;
import com.umu.springboot.repositorios.EntidadNoEncontrada;
import com.umu.springboot.repositorios.RepositorioEntrenamientoMongo;
import com.umu.springboot.repositorios.RepositorioEquipoMongo;
import com.umu.springboot.repositorios.RepositorioUsuarioMongo;
import com.umu.springboot.rest.AsistenciaDTO;
import com.umu.springboot.rest.EntrenamientoDTO;

@Service
@Transactional
public class ServicioEntrenamiento implements IServicioEntrenamiento {

	@Autowired
	private RepositorioEquipoMongo repositorioEquipo; // TODO
	@Autowired
	private RepositorioUsuarioMongo repositorioUsuario; // TODO
	@Autowired
	private RepositorioEntrenamientoMongo repositorioEntrenamiento; // TODO

	@Override
	public Page<EntrenamientoDTO> listarEntrenamientos(String idEquipo, Pageable paginacion)  throws IllegalArgumentException{
		if ((idEquipo == null) || (idEquipo.isEmpty()))
			throw new IllegalArgumentException("idEquipo: no debe ser nulo ni vacio");

		Equipo equipo = repositorioEquipo.findById(idEquipo).orElse(null);

		if (equipo == null)
			return Page.empty();
		
		List<Entrenamiento> entrenamientos = equipo.getEntrenamientos();

		// Paginación manual
		int start = (int) paginacion.getOffset();
		int end = Math.min(start + paginacion.getPageSize(), entrenamientos.size());
		List<Entrenamiento> pageEntrenamientos = entrenamientos.subList(start, end);

		List<EntrenamientoDTO> dtos = pageEntrenamientos.stream().map(e -> {
			EntrenamientoDTO entrenamientoDTO = new EntrenamientoDTO();
			entrenamientoDTO.setIdEntrenamiento(e.getId());
			entrenamientoDTO.setLugar(e.getLugar());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy HH:mm");
			entrenamientoDTO.setHorario(e.getHorario().format(formatter));

			List<AsistenciaDTO> asistenciasDTO = e.getAsistencias().stream().map(a -> {
				AsistenciaDTO asistencia = new AsistenciaDTO();
				asistencia.setIdEntrenamiento(a.getIdEntrenamiento());
				asistencia.setIdJugador(a.getIdJugador());
				return asistencia;
			}).collect(Collectors.toList());

			entrenamientoDTO.setAsistencias(asistenciasDTO);
			return entrenamientoDTO;
		}).collect(Collectors.toList());

		// Crear objeto Page
		return new PageImpl<>(dtos, paginacion, entrenamientos.size());
	}

	@Override
	public String programarEntrenamiento(String idEquipo, String entrenador, String fecha, String hora, String lugar) throws IllegalArgumentException, EntidadNoEncontrada{
		if ((idEquipo == null) || (idEquipo.isEmpty()))
			throw new IllegalArgumentException("idEquipo: no debe ser nulo ni vacio");

		if ((entrenador == null) || (entrenador.isEmpty()))
			throw new IllegalArgumentException("entrenador: no debe ser nulo ni vacio");
		
		if ((fecha == null) || (fecha.isEmpty()))
			throw new IllegalArgumentException("fecha: no debe ser nulo ni vacio");

		if ((hora == null)|| (hora.isEmpty()))
			throw new IllegalArgumentException("hora: no debe ser nulo ni vacio");
		
		if ((lugar == null) || (lugar.isEmpty()))
			throw new IllegalArgumentException("lugar: no debe ser nulo ni vacio");

		Equipo equipo = repositorioEquipo.findById(idEquipo).orElse(null);

		if ((equipo == null)||(equipo.getEntrenadores().stream().noneMatch(e -> e.getTel().equals(entrenador))))
			throw new EntidadNoEncontrada("equipo: no existe el id");

		LocalDate fechaF = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalTime horaF = LocalTime.parse(hora, DateTimeFormatter.ofPattern("HH:mm"));

		LocalDateTime fechaHora = LocalDateTime.of(fechaF, horaF);
		
		Entrenamiento entrenamiento = new Entrenamiento(fechaHora, lugar);

		equipo.addEntrenamiento(entrenamiento);

		String idEntrenamiento = repositorioEntrenamiento.save(entrenamiento).getId();
		repositorioEquipo.save(equipo);
		
		return idEntrenamiento;
	}

	@Override
	public void eliminarEntrenamiento(String idEquipo, String idEntrenamiento, String entrenador) throws IllegalArgumentException, EntidadNoEncontrada{
		if ((idEquipo == null) || (idEquipo.isEmpty()))
			throw new IllegalArgumentException("idEquipo: no debe ser nulo ni vacio");

		if ((idEntrenamiento == null) || (idEntrenamiento.isEmpty()))
			throw new IllegalArgumentException("idEntrenamiento: no debe ser nulo ni vacio");

		if ((entrenador == null) || (entrenador.isEmpty()))
			throw new IllegalArgumentException("entrenador: no debe ser nulo ni vacio");

		Equipo equipo = repositorioEquipo.findById(idEquipo).orElse(null);

		if ((equipo == null)||(equipo.getEntrenadores().stream().noneMatch(e -> e.getTel().equals(entrenador))))
			throw new EntidadNoEncontrada("equipo: no existe el id");
		
		Entrenamiento entrenamiento = repositorioEntrenamiento.findById(idEntrenamiento).orElse(null);
		
		if (entrenamiento == null)
			throw new EntidadNoEncontrada("entrenamiento: no existe el id");

		equipo.removeEntrenamiento(entrenamiento);

		repositorioEntrenamiento.deleteById(idEntrenamiento);
		repositorioEquipo.save(equipo);
	}
	
	@Override
	public void confirmarAsistencia(String idEquipo, String idEntrenamiento, String idUsuario) throws IllegalArgumentException, EntidadNoEncontrada{

		if ((idEntrenamiento == null) || (idEntrenamiento.isEmpty()))
			throw new IllegalArgumentException("idEntrenamiento: no debe ser nulo ni vacio");

		if ((idUsuario == null) || (idUsuario.isEmpty()))
			throw new IllegalArgumentException("idUsuario: no debe ser nulo ni vacio");
		
		if ((idEquipo == null) || (idEquipo.isEmpty()))
			throw new IllegalArgumentException("idEquipo: no debe ser nulo ni vacio");

		Equipo equipo = repositorioEquipo.findById(idEquipo).orElse(null); 
		if (equipo == null)
			throw new EntidadNoEncontrada("equipo: no existe el id");
		
		Entrenamiento entrenamiento = repositorioEntrenamiento.findById(idEntrenamiento).orElse(null);
		if ((entrenamiento == null) || (LocalDateTime.now().isAfter(entrenamiento.getHorario())))
			throw new EntidadNoEncontrada("entrenamiento: no existe el id");

		Usuario usuario = repositorioUsuario.findById(idUsuario).orElse(null);
		if ((usuario == null)||(equipo.getJugadores().stream().noneMatch(j -> j.getTel().equals(idUsuario))))
			throw new EntidadNoEncontrada("usuario: no existe el id");

		Asistencia asistencia = new Asistencia(idUsuario, idEntrenamiento);

		if ((entrenamiento.comprobarAsistencia(asistencia)) && (((Jugador) usuario).comprobarAsistencia(asistencia))) {
			entrenamiento.añadirAsistencias(asistencia);
			((Jugador) usuario).añadirAsistencia(asistencia);

			List<Entrenamiento> entrenamientos = equipo.getEntrenamientos();
			for (int i = 0; i < entrenamientos.size(); i++) {
				if (entrenamientos.get(i).getId().equals(idEntrenamiento)) {
					entrenamientos.set(i, entrenamiento);
					break;
				}
			}
		}

		repositorioEntrenamiento.save(entrenamiento);
		repositorioUsuario.save(usuario);
		repositorioEquipo.save(equipo);
	}

	@Override
	public void cancelarAsistencia(String idEquipo, String idEntrenamiento, String idUsuario) throws IllegalArgumentException, EntidadNoEncontrada{

		if ((idEntrenamiento == null) || (idEntrenamiento.isEmpty()))
			throw new IllegalArgumentException("idEntrenamiento: no debe ser nulo ni vacio");

		if ((idUsuario == null) || (idUsuario.isEmpty()))
			throw new IllegalArgumentException("idEntrenamiento: no debe ser nulo ni vacio");

		Equipo equipo = repositorioEquipo.findById(idEquipo).orElse(null); 
		if (equipo == null)
			throw new EntidadNoEncontrada("equipo: no existe el id");
		
		Entrenamiento entrenamiento = repositorioEntrenamiento.findById(idEntrenamiento).orElse(null);
		if ((entrenamiento == null) || (LocalDateTime.now().isAfter(entrenamiento.getHorario())))
			throw new EntidadNoEncontrada("entrenamiento: no existe el id");

		Usuario usuario = repositorioUsuario.findById(idUsuario).orElse(null);
		if ((usuario == null)||(equipo.getJugadores().stream().noneMatch(j -> j.getTel().equals(idUsuario))))
			throw new EntidadNoEncontrada("usuario: no existe el id");

		((Jugador)usuario).eliminarAsistencia(idEntrenamiento, idUsuario);
		entrenamiento.eliminarAsistencia(idEntrenamiento, idUsuario);
		
		List<Entrenamiento> entrenamientos = equipo.getEntrenamientos();
		for (int i = 0; i < entrenamientos.size(); i++) {
			if (entrenamientos.get(i).getId().equals(idEntrenamiento)) {
				entrenamientos.set(i, entrenamiento);
				break;
			}
		}
		
		repositorioEntrenamiento.save(entrenamiento);
		repositorioUsuario.save(usuario);
		repositorioEquipo.save(equipo);

		return;

	}

}
