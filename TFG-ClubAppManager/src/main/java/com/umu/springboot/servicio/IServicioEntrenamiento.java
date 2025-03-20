package com.umu.springboot.servicio;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.umu.springboot.modelo.Entrenamiento;
import com.umu.springboot.rest.EntrenamientoDTO;

public interface IServicioEntrenamiento {
	String programarEntrenamiento(String idEquipo, LocalDateTime fecha, String lugar);
	Page<EntrenamientoDTO> listarEntrenamientos(String idEquipo, Pageable paginacion);
	void confirmarAsistencia(String idEquipo, String idUsuario);
}
