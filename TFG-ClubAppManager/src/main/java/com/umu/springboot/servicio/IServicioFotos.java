package com.umu.springboot.servicio;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface IServicioFotos {

	List<Long> almacenarFotos(MultipartFile dniFrontal, MultipartFile dniTrasero, MultipartFile dniFrontalTutor1,
			MultipartFile dniTraseroTutor1, MultipartFile dniFrontalTutor2, MultipartFile dniTraseroTutor2);

}
