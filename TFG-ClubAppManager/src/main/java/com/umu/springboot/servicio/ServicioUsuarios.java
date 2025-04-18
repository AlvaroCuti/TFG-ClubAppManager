package com.umu.springboot.servicio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.umu.springboot.modelo.Entrenador;
import com.umu.springboot.modelo.Jugador;
import com.umu.springboot.modelo.Usuario;
import com.umu.springboot.repositorios.RepositorioEquipo;
import com.umu.springboot.repositorios.RepositorioUsuario;
import com.umu.springboot.rest.EntrenadorCompletoDTO;
import com.umu.springboot.rest.EntrenadorDTO;
import com.umu.springboot.rest.EquipoIdDTO;
import com.umu.springboot.rest.EquiposIdsDTO;
import com.umu.springboot.rest.JugadorDTO;
import com.umu.springboot.rest.JugadorInfoDTO;
import com.umu.springboot.utils.JwtUtilidades;

@Service
@Transactional
public class ServicioUsuarios implements IServicioUsuarios {

	@Autowired
	private RepositorioEquipo repositorioEquipo; // TODO

	@Autowired
	private RepositorioUsuario repositorioUsuario; // TODO

	@Autowired
	private JwtUtilidades utilidadesJWT;

	@Autowired
	private MongoTemplate mongoTemplate;
	
	public ServicioUsuarios() {
	}

	@Override
	public Map<String, Object> verificarCredenciales(String idUsuario, String pass) {
		Usuario usuario = repositorioUsuario.findById(idUsuario).orElseGet(null);
		if ((usuario == null)||(!usuario.getPass().equals(pass)))
			return null;

		Map<String, Object> claimsUsuario = utilidadesJWT.usuarioAClaims(usuario);

		String tokenJWT = utilidadesJWT.generacionTokenJWT(claimsUsuario);
		claimsUsuario.put("token", tokenJWT);

		return claimsUsuario;
	}

	@Override
	public String darDeAltaJugador(String tel, String nombre, String fechaNac, String email, String pass,
			long dniDelantera, long dniTrasera, String emailTutor1, long dniDelanteraTutor1,
			long dniTraseraTutor1, String emailTutor2, long dniDelanteraTutor2, long dniTraseraTutor2) {

		if (tel == null || tel.isEmpty())
			throw new IllegalArgumentException("tel: no debe ser nulo ni vacio");

		if (nombre == null || nombre.isEmpty())
			throw new IllegalArgumentException("nombre: no debe ser nulo ni vacio");

		if (fechaNac == null || fechaNac.isEmpty())
			throw new IllegalArgumentException("fechaNac: no debe ser nulo ni vacio");

		if (email == null || email.isEmpty())
			throw new IllegalArgumentException("email: no debe ser nulo ni vacio");

		if (pass == null || pass.isEmpty())
			throw new IllegalArgumentException("pass: no debe ser nulo ni vacio");
//		
//		if (dniDelantera == null || dniDelantera.isEmpty())
//			throw new IllegalArgumentException("dniDelantera: no debe ser nulo ni vacio");
//		
//		if (dniTrasera == null || dniTrasera.isEmpty())
//			throw new IllegalArgumentException("dniTrasera: no debe ser nulo ni vacio");
		
		if (emailTutor1 == null || emailTutor1.isEmpty())
			throw new IllegalArgumentException("emailTutor1: no debe ser nulo ni vacio");
//		
//		if (dniDelanteraTutor1 == null || dniDelanteraTutor1.isEmpty())
//			throw new IllegalArgumentException("dniDelanteraTutor1: no debe ser nulo ni vacio");
//		
//		if (dniTraseraTutor1 == null || dniTraseraTutor1.isEmpty())
//			throw new IllegalArgumentException("dniTraseraTutor1: no debe ser nulo ni vacio");
		
		if (emailTutor2 == null || emailTutor2.isEmpty())
			throw new IllegalArgumentException("emailTutor2: no debe ser nulo ni vacio");
		
//		if (dniDelanteraTutor2 == null || dniDelanteraTutor2.isEmpty())
//			throw new IllegalArgumentException("dniDelanteraTutor2: no debe ser nulo ni vacio");
//
//		if (dniTraseraTutor2 == null || dniTraseraTutor2.isEmpty())
//			throw new IllegalArgumentException("dniTraseraTutor2: no debe ser nulo ni vacio");

		if (repositorioUsuario.existsById(tel))
			return null;
		
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		Jugador jugador = new Jugador(tel, nombre, LocalDate.parse(fechaNac, formatter), email, pass, "JUGADOR", dniDelantera,
				dniTrasera, emailTutor1, dniDelanteraTutor1, dniTraseraTutor1, emailTutor2, dniDelanteraTutor2,
				dniTraseraTutor2);
		
		repositorioUsuario.save(jugador);
		return jugador.getTel();
	}

	@Override
    public Page<JugadorDTO> filtrarJugadores(String nombre, String tel, String fechaNac, String email,
                                                 String emailTutor1, String emailTutor2, Pageable pageable) {
        Query query = new Query();
        List<Criteria> criterios = new ArrayList<>();

        criterios.add(Criteria.where("rol").is("JUGADOR"));
        // Agregar filtros dinámicos solo si no son null ni vacíos
        if (nombre != null && !nombre.isEmpty()) {
            criterios.add(Criteria.where("nombre").regex(nombre, "i")); // Búsqueda insensible a mayúsculas
        }
        if (tel != null && !tel.isEmpty()) {
            criterios.add(Criteria.where("tel").is(tel));
        }
        if (fechaNac != null && !fechaNac.isEmpty()) {
            criterios.add(Criteria.where("fechaNac").is(LocalDate.parse(fechaNac)));
        }
        if (email != null && !email.isEmpty()) {
            criterios.add(Criteria.where("email").is(email));
        }
        if (emailTutor1 != null && !emailTutor1.isEmpty()) {
            criterios.add(Criteria.where("emailTutor1").is(emailTutor1));
        }
        if (emailTutor2 != null && !emailTutor2.isEmpty()) {
            criterios.add(Criteria.where("emailTutor2").is(emailTutor2));
        }

        // Si hay criterios, agregarlos a la consulta
        if (!criterios.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criterios.toArray(new Criteria[0])));
        }

        long total = mongoTemplate.count(query, Usuario.class); 
        query.with(pageable); // Aplicar paginación
        List<Usuario> usuarios = mongoTemplate.find(query, Usuario.class); 

        Page<Usuario> usuariosPaginados = new PageImpl<>(usuarios, pageable, total); 
        
        Page<JugadorDTO> jugadores = usuariosPaginados.map(usuario -> new JugadorDTO((Jugador) usuario));
        return jugadores;
    }
	

	@Override
	public JugadorInfoDTO descargarInfoUsuario(String idUsuario) { // TODO DTO de devolver
		if (idUsuario == null || idUsuario.isEmpty())
			return null;

		if(!repositorioUsuario.existsById(idUsuario))
			return null;
		
		Jugador jugador = (Jugador) repositorioUsuario.findById(idUsuario).orElse(null);

		JugadorInfoDTO dto = new JugadorInfoDTO(jugador.getDniDelantera(), jugador.getDniTrasera(),
				jugador.getDniDelanteraTutor1(), jugador.getDniTraseraTutor1(), jugador.getDniDelanteraTutor2(),
				jugador.getDniTraseraTutor2());
		return dto;

	}

	@Override
	public EquipoIdDTO getEquipoDeJugador(String idUsuario) {
		if (idUsuario == null || idUsuario.isEmpty())
			return null;

		if(!repositorioUsuario.existsById(idUsuario))
			return null;
		
		Jugador jugador = (Jugador) repositorioUsuario.findById(idUsuario).orElse(null);

		EquipoIdDTO dto = new EquipoIdDTO(jugador.getEquipo());
		
		return dto;
	}
	
	@Override
	public Page<EntrenadorCompletoDTO> listaEntrenadores(Pageable paginacion) {
		return this.repositorioUsuario.findByRol("ENTRENADOR", paginacion).map((entrena) -> {
			Entrenador e = (Entrenador) entrena;
			EntrenadorCompletoDTO entrenador = new EntrenadorCompletoDTO();
			entrenador.setTel(e.getTel());
			entrenador.setNombre(e.getNombre());
			entrenador.setFechaNac(e.getFechaNac().toString());
			entrenador.setEmail(e.getEmail());
			return entrenador;
		});
	}

	@Override
	public String crearEntrenador(String tel, String nombre, String fechaNac, String email, String pass,
			long dniDelantera, long dniTrasera, long certificadoDelitosSexuales) {

		if (tel == null || tel.isEmpty())
			throw new IllegalArgumentException("tel: no debe ser nulo ni vacio");

		if (nombre == null || nombre.isEmpty())
			throw new IllegalArgumentException("nombre: no debe ser nulo ni vacio");

		if (fechaNac == null || fechaNac.isEmpty())
			throw new IllegalArgumentException("fechaNac: no debe ser nulo ni vacio");

		if (email == null || email.isEmpty())
			throw new IllegalArgumentException("email: no debe ser nulo ni vacio");

		if (pass == null || pass.isEmpty())
			throw new IllegalArgumentException("pass: no debe ser nulo ni vacio");

//		if (dniDelantera == null || dniDelantera.isEmpty())
//			throw new IllegalArgumentException("dniDelantera: no debe ser nulo ni vacio");
//
//		if (dniTrasera == null || dniTrasera.isEmpty())
//			throw new IllegalArgumentException("dniTrasera: no debe ser nulo ni vacio");
//
//		if (certificadoDelitosSexuales == null || certificadoDelitosSexuales.isEmpty())
//			throw new IllegalArgumentException("certificadoDelitosSexuales: no debe ser nulo ni vacio");

		if (repositorioUsuario.existsById(tel))
			return null;
		
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		Entrenador entrenador = new Entrenador(tel, nombre, LocalDate.parse(fechaNac, formatter), email, pass, "ENTRENADOR",
				dniDelantera, dniTrasera, certificadoDelitosSexuales); // TODO pass generada para los entrenadores

		repositorioUsuario.save(entrenador);

		return entrenador.getTel();
	}

	@Override
	public EntrenadorDTO getEntrenador(String idEntrenador) {
		if (idEntrenador == null || idEntrenador.isEmpty())
			return null;

		if(!repositorioUsuario.existsById(idEntrenador))
			return null;
		
		Entrenador entrenador = (Entrenador) repositorioUsuario.findById(idEntrenador).orElse(null);
		EntrenadorDTO dto = new EntrenadorDTO(entrenador.getTel(), entrenador.getDniDelantera(), entrenador.getDniTrasera(), entrenador.getCertificadoDelitosSexuales()); // TODO no esta bien
		return dto;
	}

	@Override
	public EquiposIdsDTO getEquiposDeEntrenador(String idEntrenador) {
		if (idEntrenador == null || idEntrenador.isEmpty())
			return null;

		if(!repositorioUsuario.existsById(idEntrenador))
			return null;
		
		Entrenador entrenador = (Entrenador) repositorioUsuario.findById(idEntrenador).orElse(null);

		List<EquipoIdDTO> equipos = entrenador.getEquipos().stream()
				.map(EquipoIdDTO::new).collect(Collectors.toList());	
		
		return new EquiposIdsDTO(equipos);
	}
	
	@Override
	public void modificarEntrenador(String telAntiguo, String telNuevo, String nombre, String fechaNac, String email, String pass,
			long dniDelantera, long dniTrasera, long certificadoDelitosSexuales) {
		Usuario usuario = repositorioUsuario.findById(telAntiguo).orElse(null);
		((Entrenador) usuario).modificar(telNuevo, nombre, LocalDate.parse(fechaNac), email, pass, dniDelantera, dniTrasera,
				certificadoDelitosSexuales);
		repositorioUsuario.deleteById(telAntiguo);
		repositorioUsuario.save(usuario);
		return;
	}

	@Override
	public void borrarEntrenador(String idEntrenador) {
		if (idEntrenador == null || idEntrenador.isEmpty())
			return;

		repositorioUsuario.deleteById(idEntrenador);

		return;
	}

}
