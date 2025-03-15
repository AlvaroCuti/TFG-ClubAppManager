package com.umu.springboot.servicio;

import java.time.LocalDateTime;
import java.util.List;

import com.umu.springboot.modelo.Entrenamiento;

public interface IServicioEntrenamiento {
	void programarEntrenamiento(String idEquipo, LocalDateTime fecha, String lugar);
	List<Entrenamiento> listarEntrenamientos(String idEquipo);
	void confirmarAsistencia(String idUsuario, String idEquipo);
}
