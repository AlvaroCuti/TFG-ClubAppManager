package com.umu.springboot.servicio;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.umu.springboot.modelo.Entrenamiento;
import com.umu.springboot.modelo.Equipo;
import com.umu.springboot.repositorios.RepositorioEquipoMongo;
import com.umu.springboot.repositorios.RepositorioUsuarioMongo;

@Service
@Transactional
public class ServicioEntrenamiento implements IServicioEntrenamiento {

	@Autowired
	private RepositorioEquipoMongo repositorioEquipo;			//TODO
	@Autowired
	private RepositorioUsuarioMongo repositorioUsuario;			//TODO
	
	@Override
	public void programarEntrenamiento(String idEquipo, LocalDateTime fecha, String lugar) {
		if ((idEquipo == null) || (idEquipo.isEmpty()))
			return;
		
		if (fecha == null)
			return;
		
		Equipo equipo = repositorioEquipo.findById(idEquipo).orElse(null);
		
		if(equipo == null)
			return;
			
		Entrenamiento entrenamiento = new Entrenamiento(fecha, lugar);
		
		equipo.addEntrenamiento(entrenamiento);		
	}

	@Override
	public List<Entrenamiento> listarEntrenamientos(String idEquipo) {
		if ((idEquipo == null) || (idEquipo.isEmpty()))
			return null;

		Equipo equipo = repositorioEquipo.findById(idEquipo).orElse(null);
		
		if(equipo == null)
			return null;
		
		return equipo.getEntrenamientos();
	}

	@Override
	public void confirmarAsistencia(String idUsuario, String idEquipo) {
		//TODO Seria necesario un repositorio de entrenamientos????
	}

}
