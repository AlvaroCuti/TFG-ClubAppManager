package com.umu.springboot.servicio;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.umu.springboot.modelo.Imagen;

public interface IServicioFotos {

	List<Long> almacenarFotos(MultipartFile dniFrontal, MultipartFile dniTrasero, MultipartFile dniFrontalTutor1,
			MultipartFile dniTraseroTutor1, MultipartFile dniFrontalTutor2, MultipartFile dniTraseroTutor2);
	
	List<Long> almacenarFotos(MultipartFile dniFrontal, MultipartFile dniTrasero, MultipartFile certDelitos);
	
	List<Imagen> descargarFotos(long dniFrontal, long dniTrasero, long dniFrontalTutor1,
			long dniTraseroTutor1, long dniFrontalTutor2, long dniTraseroTutor2);

	List<Imagen> descargarFotos(long dniFrontal, long dniTrasero, long certDelitos);
	
	void borrarFotos(long dniFrontal, long dniTrasero, long certDelitos);
}
