package com.umu.springboot.servicio;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.umu.springboot.modelo.Archivo;
import com.umu.springboot.repositorios.RepositorioArchivo;
import com.umu.springboot.utils.WebPUtilidades;

@Service
@Transactional
public class ServicioFotos implements IServicioFotos {

	@Autowired
	private RepositorioArchivo repositorioArchivo;

	@Autowired
	private WebPUtilidades webPConverter;

	@Override
	public List<Long> almacenarFotos(MultipartFile dniFrontal, MultipartFile dniTrasero, MultipartFile dniFrontalTutor1,
			MultipartFile dniTraseroTutor1, MultipartFile dniFrontalTutor2, MultipartFile dniTraseroTutor2) {

		List<MultipartFile> archivos = Arrays.asList(dniFrontal, dniTrasero, dniFrontalTutor1, dniTraseroTutor1,
				dniFrontalTutor2, dniTraseroTutor2);

		return archivos.stream().filter(this::esArchivoValido).map(this::crearImagen).map(repositorioArchivo::save)
				.map(Archivo::getId).collect(Collectors.toList());
	}

	@Override
	public List<Long> almacenarFotos(MultipartFile dniFrontal, MultipartFile dniTrasero, MultipartFile certDelitos) {

		List<Long> listaIds = new LinkedList<Long>();
		List<MultipartFile> archivos = Arrays.asList(dniFrontal, dniTrasero, certDelitos);

		for (MultipartFile multipartFile : archivos) {
			if(multipartFile.getContentType().equals("application/pdf")) {
				Archivo archivo;
				try {
					archivo = new Archivo(multipartFile.getOriginalFilename(), multipartFile.getBytes(), multipartFile.getContentType());
					repositorioArchivo.save(archivo);
					listaIds.add(archivo.getId());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		archivos.stream()
	        .filter(file -> !"application/pdf".equalsIgnoreCase(file.getContentType()))
	        .filter(this::esArchivoValido)
	        .map(this::crearImagen)
	        .map(repositorioArchivo::save)
	        .map(Archivo::getId)
	        .forEach(listaIds::add);
		 return listaIds;
	}

	@Override
	public List<Archivo> descargarFotos(long dniFrontal, long dniTrasero, long dniFrontalTutor1, long dniTraseroTutor1,
			long dniFrontalTutor2, long dniTraseroTutor2) {

		List<Long> archivos = Arrays.asList(dniFrontal, dniTrasero, dniFrontalTutor1, dniTraseroTutor1,
				dniFrontalTutor2, dniTraseroTutor2);
		return archivos.stream().map(repositorioArchivo::findById).filter(Optional::isPresent).map(Optional::get)
				.map(this::recuperarImagen).collect(Collectors.toList());
	}

	@Override
	public List<Archivo> descargarFotos(long dniFrontal, long dniTrasero, long certDelitos) {
		List<Long> archivos = Arrays.asList(dniFrontal, dniTrasero, certDelitos);
		return archivos.stream()
			    .map(repositorioArchivo::findById)
			    .filter(Optional::isPresent)
			    .map(Optional::get)
			    .map(archivo -> {
			        if (!"application/pdf".equalsIgnoreCase(archivo.getTipoMime())) {
			            return recuperarImagen(archivo);
			        } else {
			            return archivo;
			        }
			    })
			    .collect(Collectors.toList());

	}

	@Override
	public void borrarFotos(long dniFrontal, long dniTrasero, long certDelitos) {
		List<Long> archivos = Arrays.asList(dniFrontal, dniTrasero, certDelitos);
		for (Long long1 : archivos) {
			if (repositorioArchivo.existsById(long1))
				repositorioArchivo.deleteById(long1);
		}

	}

	@Override
	public void borrarFotos(long dniFrontal, long dniTrasero, long dniFrontalTutor1, long dniTraseroTutor1,
			long dniFrontalTutor2, long dniTraseroTutor2) {
		List<Long> archivos = Arrays.asList(dniFrontal, dniTrasero, dniFrontal, dniTrasero, dniFrontalTutor1,
				dniTraseroTutor1, dniFrontalTutor2, dniTraseroTutor2);

		for (Long long1 : archivos) {
			if (repositorioArchivo.existsById(long1))
				repositorioArchivo.deleteById(long1);
		}

	}

	private boolean esArchivoValido(MultipartFile file) {
		return file != null && !file.isEmpty();
	}

	private Archivo crearImagen(MultipartFile file) {
		Archivo img = null;
		try {
			byte[] originalBytes = file.getBytes();
			byte[] webpBytes = webPConverter.convertirAWebP(originalBytes);
			img = new Archivo(file.getOriginalFilename(), webpBytes, file.getContentType());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	private Archivo recuperarImagen(Archivo i) {
		Archivo img = null;
		try {
			byte[] originalBytes = i.getContenido();

			BufferedImage bufferedImage = webPConverter.descomprimirWebP(originalBytes);

			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, i.getNombre().substring(i.getNombre().lastIndexOf('.') + 1).toLowerCase(),
					byteArrayOutputStream);
			byte[] imageBytes = byteArrayOutputStream.toByteArray();

			img = new Archivo(i.getNombre(), imageBytes, i.getTipoMime());
			img.setId(i.getId());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
}
