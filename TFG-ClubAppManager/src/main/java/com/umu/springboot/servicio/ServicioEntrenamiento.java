package com.umu.springboot.servicio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.umu.springboot.modelo.Entrenamiento;
import com.umu.springboot.modelo.Equipo;
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
	public String programarEntrenamiento(String idEquipo, LocalDateTime fecha, String lugar) {
		if ((idEquipo == null) || (idEquipo.isEmpty()))
			return null;
		
		if (fecha == null)
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
			entrenamientoDTO.setHorario((e.getHorario().format(formatter)));
			entrenamientoDTO.setNumAsistencias(Integer.toString(e.getAsistencias().size()));
			return entrenamientoDTO;
		});
		
	}

	@Override
	public void confirmarAsistencia(String idEquipo, String idUsuario) {
		//TODO Seria necesario un repositorio de entrenamientos????
	}

}
