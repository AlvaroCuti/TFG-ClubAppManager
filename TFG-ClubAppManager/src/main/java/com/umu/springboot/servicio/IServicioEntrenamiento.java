package com.umu.springboot.servicio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.umu.springboot.repositorios.EntidadNoEncontrada;
import com.umu.springboot.rest.EntrenamientoDTO;

public interface IServicioEntrenamiento {
	
	Page<EntrenamientoDTO> listarEntrenamientos(String idEquipo, Pageable paginacion) throws IllegalArgumentException;
	
	String programarEntrenamiento(String idEquipo, String entrenador, String fecha, String hora, String lugar) throws IllegalArgumentException, EntidadNoEncontrada;
	
	void eliminarEntrenamiento(String idEquipo, String idEntrenamiento, String entrenador) throws IllegalArgumentException, EntidadNoEncontrada;
	
	void confirmarAsistencia(String idEquipo, String idEntrenamiento, String idUsuario) throws IllegalArgumentException, EntidadNoEncontrada;
	
	void cancelarAsistencia(String idEquipo, String idEntrenamiento, String idUsuario) throws IllegalArgumentException, EntidadNoEncontrada;
}
