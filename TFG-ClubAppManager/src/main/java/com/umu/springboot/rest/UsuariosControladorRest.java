package com.umu.springboot.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.umu.springboot.modelo.Archivo;
import com.umu.springboot.repositorios.EntidadNoEncontrada;
import com.umu.springboot.servicio.IServicioFotos;
import com.umu.springboot.servicio.IServicioUsuarios;

@RestController
@RequestMapping("/api")
public class UsuariosControladorRest {

	@Autowired
	private IServicioUsuarios servicioUsuarios;

	@Autowired
	private IServicioFotos servicioFotos;

	@Autowired
	private PagedResourcesAssembler<EntrenadorCompletoDTO> pagedResourcesAssembler1;

	@Autowired
	private PagedResourcesAssembler<JugadorDTO> pagedResourcesAssembler2;

	@PostMapping(value = "/usuario/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody AutenticarUsuarioDTO autenticacionDTO) {
		Map<String, Object> c = servicioUsuarios.verificarCredenciales(autenticacionDTO.getTel(),
				autenticacionDTO.getPass());
		if (c == null) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("error", "Unauthorized");
			errorResponse.put("message", "Credenciales incorrectas");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
		}
		return ResponseEntity.ok(c);
	}

	@PostMapping(value = "/usuario/register")
	public ResponseEntity<Void> registroUsuario(@RequestParam("creacionDTOString") String creacionDTOString,
			@RequestParam("dniFrontal") MultipartFile dniFrontal, @RequestParam("dniTrasero") MultipartFile dniTrasero,
			@RequestParam("dniFrontalTutor1") MultipartFile dniFrontalTutor1,
			@RequestParam("dniTraseroTutor1") MultipartFile dniTraseroTutor1,
			@RequestParam("dniFrontalTutor2") MultipartFile dniFrontalTutor2,
			@RequestParam("dniTraseroTutor2") MultipartFile dniTraseroTutor2) throws IllegalArgumentException{

		ObjectMapper mapper = new ObjectMapper();

		CreacionJugadorDTO creacionDTO = null;
		try {
			creacionDTO = mapper.readValue(creacionDTOString, CreacionJugadorDTO.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<Long> fotos = servicioFotos.almacenarFotos(dniFrontal, dniTrasero, dniFrontalTutor1, dniTraseroTutor1,
				dniFrontalTutor2, dniTraseroTutor2);

		String idUsuario = servicioUsuarios.darDeAltaJugador(creacionDTO.getTel(), creacionDTO.getNombre(),
				creacionDTO.getFechaNac(), creacionDTO.getEmail(), creacionDTO.getPass(), fotos.get(0), fotos.get(1),
				creacionDTO.getEmailTutor1(), fotos.get(2), fotos.get(3), creacionDTO.getEmailTutor2(), fotos.get(4),
				fotos.get(5));
		if (idUsuario == null) {
			servicioFotos.borrarFotos(fotos.get(0), fotos.get(1), fotos.get(2), fotos.get(3), fotos.get(4),
					fotos.get(5));
			return ResponseEntity.badRequest().build();
		}
		URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idUsuario}").buildAndExpand(idUsuario)
				.toUri();

		return ResponseEntity.created(url).build();
	}

	@GetMapping(value = "/usuario")
	@PreAuthorize("hasAuthority('ADMIN')")
	public PagedModel<EntityModel<JugadorDTO>> filtrarJugadores(@RequestParam(required = false) String nombre,
			@RequestParam(required = false) String tel, @RequestParam(required = false) String fechaNac,
			@RequestParam(required = false) String email, @RequestParam(required = false) String emailTutor1,
			@RequestParam(required = false) String emailTutor2, @RequestParam int page, @RequestParam int size) {

		Pageable paginacion = PageRequest.of(page, size);
		Page<JugadorDTO> listaJugadoresDTO = servicioUsuarios.filtrarJugadores(nombre, tel, fechaNac, email,
				emailTutor1, emailTutor2, paginacion);
		return this.pagedResourcesAssembler2.toModel(listaJugadoresDTO, jugador -> {
			EntityModel<JugadorDTO> model = EntityModel.of(jugador);
			return model;
		});
	}

	@GetMapping(value = "/usuario/{idUsuario}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ByteArrayResource> getInfoJugador(@PathVariable String idUsuario) throws IllegalArgumentException, EntidadNoEncontrada{
		JugadorInfoDTO dto = servicioUsuarios.descargarInfoUsuario(idUsuario);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zipOut = new ZipOutputStream(baos);

		List<Archivo> imagenes = servicioFotos.descargarFotos(dto.getDniDelantera(), dto.getDniTrasera(),
				dto.getDniDelanteraTutor1(), dto.getDniTraseraTutor1(), dto.getDniDelanteraTutor2(),
				dto.getDniTraseraTutor2());
		try {
			for (Archivo imagen : imagenes) {
				ZipEntry zipEntry = new ZipEntry("ID-" + imagen.getId() + "-" + imagen.getNombre());
				zipOut.putNextEntry(zipEntry);
				zipOut.write(imagen.getContenido());
				zipOut.closeEntry();
			}

			zipOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		ByteArrayResource recurso = new ByteArrayResource(baos.toByteArray());

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"archivos.zip\"").body(recurso);
	}

	@GetMapping(value = "/usuario/{idUsuario}/equipo", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('JUGADOR')")
	public EquipoIdDTO getEquipoDeJugador(@PathVariable String idUsuario) throws IllegalArgumentException, EntidadNoEncontrada{
		EquipoIdDTO equipoIdDTO = servicioUsuarios.getEquipoDeJugador(idUsuario);
		return equipoIdDTO;
	}

	@GetMapping(value = "/entrenador", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMIN')")
	public PagedModel<EntityModel<EntrenadorCompletoDTO>> getListadoEntrenadores(@RequestParam int page,
			@RequestParam int size) {
		Pageable paginacion = PageRequest.of(page, size);
		Page<EntrenadorCompletoDTO> listaEntrenadoresDTO = servicioUsuarios.listaEntrenadores(paginacion);
		return this.pagedResourcesAssembler1.toModel(listaEntrenadoresDTO, entrenador -> {
			EntityModel<EntrenadorCompletoDTO> model = EntityModel.of(entrenador);
			return model;
		});
	}

	@PostMapping(value = "/entrenador")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Void> crearEntrenador(@RequestParam("crearEntrenadorDTO") String crearEntrenadorDTO,
			@RequestParam("dniFrontal") MultipartFile dniFrontal, @RequestParam("dniTrasero") MultipartFile dniTrasero,
			@RequestParam("certDelitos") MultipartFile certDelitos) throws IllegalArgumentException, EntidadNoEncontrada{

		ObjectMapper mapper = new ObjectMapper();

		CrearEntrenadorDTO creacionDTO = null;
		try {
			creacionDTO = mapper.readValue(crearEntrenadorDTO, CrearEntrenadorDTO.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<Long> fotos = servicioFotos.almacenarFotos(dniFrontal, dniTrasero, certDelitos);

		String idEntrenador = servicioUsuarios.crearEntrenador(creacionDTO.getTel(), creacionDTO.getNombre(),
				creacionDTO.getFechaNac(), creacionDTO.getEmail(), creacionDTO.getPass(), fotos.get(0), fotos.get(1),
				fotos.get(2));

		if (idEntrenador == null) {
			servicioFotos.borrarFotos(fotos.get(0), fotos.get(1), fotos.get(2));
			return ResponseEntity.badRequest().build();
		
		}

		URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idEntrenador}").buildAndExpand(idEntrenador)
				.toUri();
		return ResponseEntity.created(url).build();
	}

	@GetMapping(value = "/entrenador/{idEntrenador}", produces = MediaType.APPLICATION_JSON_VALUE) 
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ByteArrayResource> getInfoEntrenador(@PathVariable String idEntrenador) throws IllegalArgumentException, EntidadNoEncontrada{
		EntrenadorDTO dto = servicioUsuarios.getEntrenador(idEntrenador);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zipOut = new ZipOutputStream(baos);

		List<Archivo> imagenes = servicioFotos.descargarFotos(dto.getDniDelantera(), dto.getDniTrasera(),
				dto.getCertDelitos());
		try {
			for (Archivo imagen : imagenes) {
				ZipEntry zipEntry = new ZipEntry("ID-" + imagen.getId() + "-" + imagen.getNombre());
				zipOut.putNextEntry(zipEntry);
				zipOut.write(imagen.getContenido());
				zipOut.closeEntry();
			}

			zipOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		ByteArrayResource recurso = new ByteArrayResource(baos.toByteArray());

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"archivos.zip\"").body(recurso);
	}

	@GetMapping(value = "/entrenador/{idEntrenador}/equipo", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ENTRENADOR')")
	public EquiposIdsDTO getEquiposDeEntrenador(@PathVariable String idEntrenador) throws IllegalArgumentException, EntidadNoEncontrada{
		EquiposIdsDTO equiposIdsDTO = servicioUsuarios.getEquiposDeEntrenador(idEntrenador);
		return equiposIdsDTO;
	}

	@PutMapping(value = "/entrenador/{idEntrenador}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Void> modificarEntrenador(@PathVariable String idEntrenador,
			@RequestParam("modificarEntrenadorDTO") String modificarEntrenadorDTO,
			@RequestParam("dniFrontal") MultipartFile dniFrontal, @RequestParam("dniTrasero") MultipartFile dniTrasero,
			@RequestParam("certDelitos") MultipartFile certDelitos) throws IllegalArgumentException, EntidadNoEncontrada{

		ObjectMapper mapper = new ObjectMapper();

		CrearEntrenadorDTO modificacionDTO = null;
		try {
			modificacionDTO = mapper.readValue(modificarEntrenadorDTO, CrearEntrenadorDTO.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		EntrenadorDTO dto = servicioUsuarios.getEntrenador(idEntrenador);

		servicioFotos.borrarFotos(dto.getDniDelantera(), dto.getDniTrasera(), dto.getCertDelitos());

		List<Long> fotos = servicioFotos.almacenarFotos(dniFrontal, dniTrasero, certDelitos);

		servicioUsuarios.modificarEntrenador(idEntrenador, modificacionDTO.getTel(), modificacionDTO.getNombre(),
				modificacionDTO.getFechaNac(), modificacionDTO.getEmail(), modificacionDTO.getPass(), fotos.get(0),
				fotos.get(1), fotos.get(2));
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/usuario/{idUsuario}/pass", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ENTRENADOR')")
	public ResponseEntity<?> cambiarPass(@PathVariable String idUsuario, @Valid @RequestBody CambiarPassDTO cambiarPassDTO) throws IllegalArgumentException, EntidadNoEncontrada{
		boolean confirmacion =servicioUsuarios.cambiarPass(idUsuario, cambiarPassDTO.getOldPass(), cambiarPassDTO.getNewPass());

	    if (confirmacion) {
	        return ResponseEntity.noContent().build();
	    } else {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Contraseña actual incorrecta o cambio inválido.");
	    }
	}

	@DeleteMapping(value = "/entrenador/{idEntrenador}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Void> borrarEntrenador(@PathVariable String idEntrenador) throws IllegalArgumentException, EntidadNoEncontrada{
		EntrenadorDTO dto = servicioUsuarios.getEntrenador(idEntrenador);
		servicioFotos.borrarFotos(dto.getDniDelantera(), dto.getDniTrasera(), dto.getCertDelitos());
		servicioUsuarios.borrarEntrenador(idEntrenador);
		return ResponseEntity.noContent().build();
	}
	
}
