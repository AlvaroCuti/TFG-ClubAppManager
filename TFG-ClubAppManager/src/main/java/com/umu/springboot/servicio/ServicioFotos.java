package com.umu.springboot.servicio;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.umu.springboot.modelo.Imagen;
import com.umu.springboot.repositorios.RepositorioImagen;

@Service
@Transactional
public class ServicioFotos implements IServicioFotos {

	@Autowired
	private RepositorioImagen repositorioImagen;

	@Override
	public List<Long> almacenarFotos(MultipartFile dniFrontal, MultipartFile dniTrasero, MultipartFile dniFrontalTutor1,
			MultipartFile dniTraseroTutor1, MultipartFile dniFrontalTutor2, MultipartFile dniTraseroTutor2) {

		List<MultipartFile> archivos = Arrays.asList(dniFrontal, dniTrasero, dniFrontalTutor1, dniTraseroTutor1,
				dniFrontalTutor2, dniTraseroTutor2);

		return archivos.stream().filter(this::esArchivoValido).map(this::crearImagen).map(repositorioImagen::save)
				.map(Imagen::getId).collect(Collectors.toList());
	}

	private boolean esArchivoValido(MultipartFile file) {
		return file != null && !file.isEmpty();
	}

	private Imagen crearImagen(MultipartFile file) {
		Imagen img = null;
		try {
			img = new Imagen(file.getOriginalFilename(), file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	@Override
	public List<Imagen> descargarFotos(long dniFrontal, long dniTrasero, long dniFrontalTutor1, long dniTraseroTutor1,
			long dniFrontalTutor2, long dniTraseroTutor2) {
		
		List<Long> archivos = Arrays.asList(dniFrontal, dniTrasero, dniFrontalTutor1, dniTraseroTutor1,
				dniFrontalTutor2, dniTraseroTutor2);
		
		return archivos.stream().map(repositorioImagen::findById).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());		
	}
	
}
