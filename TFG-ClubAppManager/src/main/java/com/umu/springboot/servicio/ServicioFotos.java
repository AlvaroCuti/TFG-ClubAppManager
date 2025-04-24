package com.umu.springboot.servicio;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.umu.springboot.modelo.Imagen;
import com.umu.springboot.repositorios.RepositorioImagen;
import com.umu.springboot.utils.WebPUtilidades;

@Service
@Transactional
public class ServicioFotos implements IServicioFotos {

	@Autowired
	private RepositorioImagen repositorioImagen;
	
	@Autowired
	private WebPUtilidades webPConverter;

	@Override
	public List<Long> almacenarFotos(MultipartFile dniFrontal, MultipartFile dniTrasero, MultipartFile dniFrontalTutor1,
			MultipartFile dniTraseroTutor1, MultipartFile dniFrontalTutor2, MultipartFile dniTraseroTutor2) {

		List<MultipartFile> archivos = Arrays.asList(dniFrontal, dniTrasero, dniFrontalTutor1, dniTraseroTutor1,
				dniFrontalTutor2, dniTraseroTutor2);

		return archivos.stream().filter(this::esArchivoValido).map(this::crearImagen).map(repositorioImagen::save)
				.map(Imagen::getId).collect(Collectors.toList());
	}
	
	@Override
	public List<Long> almacenarFotos(MultipartFile dniFrontal, MultipartFile dniTrasero, MultipartFile certDelitos) {

		List<MultipartFile> archivos = Arrays.asList(dniFrontal, dniTrasero, certDelitos);

		return archivos.stream().filter(this::esArchivoValido).map(this::crearImagen).map(repositorioImagen::save)
				.map(Imagen::getId).collect(Collectors.toList());
	}

	@Override
	public List<Imagen> descargarFotos(long dniFrontal, long dniTrasero, long dniFrontalTutor1, long dniTraseroTutor1,
			long dniFrontalTutor2, long dniTraseroTutor2) {
		
		List<Long> archivos = Arrays.asList(dniFrontal, dniTrasero, dniFrontalTutor1, dniTraseroTutor1,
				dniFrontalTutor2, dniTraseroTutor2);
		
		return archivos.stream().map(repositorioImagen::findById).filter(Optional::isPresent).map(Optional::get).map(this::recuperarImagen).collect(Collectors.toList());		
	}
	
	@Override
	public List<Imagen> descargarFotos(long dniFrontal, long dniTrasero, long certDelitos) {
		
		List<Long> archivos = Arrays.asList(dniFrontal, dniTrasero, certDelitos);
		
		return archivos.stream().map(repositorioImagen::findById).filter(Optional::isPresent).map(Optional::get).map(this::recuperarImagen).collect(Collectors.toList());		
	}
	
	@Override
	public void borrarFotos(long dniFrontal, long dniTrasero, long certDelitos) {
		List<Long> archivos = Arrays.asList(dniFrontal, dniTrasero, certDelitos);
		
		for (Long long1 : archivos) {
			if (repositorioImagen.existsById(long1))
				repositorioImagen.deleteById(long1);
		}		

	}
	
	private boolean esArchivoValido(MultipartFile file) {
		return file != null && !file.isEmpty();
	}

	private Imagen crearImagen(MultipartFile file) {
		Imagen img = null;
		try {
			byte[] originalBytes = file.getBytes();
	        byte[] webpBytes = webPConverter.convertirAWebP(originalBytes);
			img = new Imagen(file.getOriginalFilename(), webpBytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	private Imagen recuperarImagen(Imagen i) {
		Imagen img = null;
		try {
			byte[] originalBytes = i.getContenido();
			
			BufferedImage bufferedImage = webPConverter.descomprimirWebP(originalBytes);
			
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, i.getNombre().substring(i.getNombre().lastIndexOf('.') + 1).toLowerCase(), byteArrayOutputStream); 
			byte[] imageBytes = byteArrayOutputStream.toByteArray();
			
			img = new Imagen(i.getNombre(), imageBytes);  
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
}
}
