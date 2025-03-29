package com.umu.springboot.servicio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.umu.springboot.modelo.Asistencia;
import com.umu.springboot.modelo.Entrenamiento;
import com.umu.springboot.modelo.Equipo;
import com.umu.springboot.modelo.Jugador;
import com.umu.springboot.modelo.Usuario;
import com.umu.springboot.repositorios.RepositorioEntrenamientoMongo;
import com.umu.springboot.repositorios.RepositorioEquipoMongo;
import com.umu.springboot.repositorios.RepositorioUsuarioMongo;
import com.umu.springboot.rest.EntrenamientoDTO;

@Service
@Transactional
public class ServicioEntrenamiento implements IServicioEntrenamiento {

	@Autowired
	private RepositorioEquipoMongo repositorioEquipo;			//TODO
	@Autowired
	private RepositorioUsuarioMongo repositorioUsuario;			//TODO
	@Autowired
	private RepositorioEntrenamientoMongo repositorioEntrenamiento;			//TODO
	
	@Override
	public Page<EntrenamientoDTO> listarEntrenamientos(String idEquipo, Pageable paginacion) {
		if ((idEquipo == null) || (idEquipo.isEmpty()))
			return null;

		Equipo equipo = repositorioEquipo.findById(idEquipo).orElse(null);
		
		if(equipo == null)
			return null;
		
		return repositorioEntrenamiento.findAll(paginacion).map((Entrenamiento e) -> {
			EntrenamientoDTO entrenamientoDTO = new EntrenamientoDTO();
			entrenamientoDTO.setIdEntrenamiento(e.getId());
			entrenamientoDTO.setLugar(e.getLugar());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
			
			int numAsistencias = (e.getAsistencias() != null) ? e.getAsistencias().size() : 0;
			
			entrenamientoDTO.setHorario((e.getHorario().format(formatter)));
			entrenamientoDTO.setNumAsistencias(Integer.toString(numAsistencias));
			return entrenamientoDTO;
		});
		
	}
	
	@Override
	public String programarEntrenamiento(String idEquipo, LocalDateTime fecha, String lugar) {
		if ((idEquipo == null) || (idEquipo.isEmpty()))
			return null;
		
		if (fecha == null)
			return null;
		
		if ((lugar == null) || (lugar.isEmpty()))
			return null;
		
		Equipo equipo = repositorioEquipo.findById(idEquipo).orElse(null);
		
		if(equipo == null)
			return null;
			
		Entrenamiento entrenamiento = new Entrenamiento(fecha, lugar);
		
		equipo.addEntrenamiento(entrenamiento);	
		
		String idEntrenamiento = repositorioEntrenamiento.save(entrenamiento).getId();
		
		return idEntrenamiento;
	}

	@Override
	public void confirmarAsistencia(String idEntrenamiento, String idUsuario) {
		
		if ((idEntrenamiento == null) || (idEntrenamiento.isEmpty()))
			return;
		
		if ((idUsuario == null) || (idUsuario.isEmpty()))
			return;
	
		Entrenamiento entrenamiento = repositorioEntrenamiento.findById(idEntrenamiento).orElse(null);
		if(entrenamiento == null)
			return;
		
		Usuario usuario = repositorioUsuario.findById(idUsuario).orElse(null);
		if(usuario == null)
			return;
		
		Asistencia asistencia = new Asistencia(true);
		
		entrenamiento.añadirAsistencias(asistencia);
		((Jugador)usuario).añadirAsistencia(asistencia);
		
		repositorioEntrenamiento.save(entrenamiento);
		repositorioUsuario.save(usuario);
		
		return;
		
		
	}

}
