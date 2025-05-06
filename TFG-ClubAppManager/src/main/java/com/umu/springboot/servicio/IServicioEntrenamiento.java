package com.umu.springboot.servicio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.umu.springboot.rest.EntrenamientoDTO;

public interface IServicioEntrenamiento {
	
	Page<EntrenamientoDTO> listarEntrenamientos(String idEquipo, Pageable paginacion);
	
	String programarEntrenamiento(String idEquipo, String entrenador, String fecha, String hora, String lugar);
	
	void eliminarEntrenamiento(String idEquipo, String idEntrenamiento, String entrenador);
	
	void confirmarAsistencia(String idEquipo, String idEntrenamiento, String idUsuario);
	
	void cancelarAsistencia(String idEquipo, String idEntrenamiento, String idUsuario);
}
