package com.umu.springboot.servicio;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.umu.springboot.modelo.Entrenamiento;
import com.umu.springboot.rest.EntrenamientoDTO;

public interface IServicioEntrenamiento {
	
	Page<EntrenamientoDTO> listarEntrenamientos(String idEquipo, Pageable paginacion);
	
	String programarEntrenamiento(String idEquipo, LocalDateTime fecha, String lugar);
	
	void confirmarAsistencia(String idEquipo, String idUsuario);
}
