package com.umu.springboot.servicio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.umu.springboot.modelo.Entrenador;
import com.umu.springboot.modelo.Equipo;
import com.umu.springboot.modelo.Jugador;
import com.umu.springboot.modelo.Usuario;
import com.umu.springboot.repositorios.EntidadNoEncontrada;
import com.umu.springboot.repositorios.RepositorioEquipo;
import com.umu.springboot.repositorios.RepositorioUsuario;
import com.umu.springboot.rest.EntrenadorCompletoDTO;
import com.umu.springboot.rest.EntrenadorDTO;
import com.umu.springboot.rest.EquipoIdDTO;
import com.umu.springboot.rest.EquipoIdYNombreDTO;
import com.umu.springboot.rest.EquiposIdsDTO;
import com.umu.springboot.rest.JugadorDTO;
import com.umu.springboot.rest.JugadorInfoDTO;
import com.umu.springboot.utils.JwtUtilidades;

@Service
@Transactional
public class ServicioUsuarios implements IServicioUsuarios {

	@Autowired
	private RepositorioEquipo repositorioEquipo;
	@Autowired
	private RepositorioUsuario repositorioUsuario;
	@Autowired
	private JwtUtilidades utilidadesJWT;
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public ServicioUsuarios() {
	}

	@Override
	public Map<String, Object> verificarCredenciales(String idUsuario, String pass) {
		Usuario usuario = repositorioUsuario.findById(idUsuario).orElseGet(null);
		if ((usuario == null)||(!passwordEncoder.matches(pass, usuario.getPass())))
			return null;

		Map<String, Object> claimsUsuario = utilidadesJWT.usuarioAClaims(usuario);

		String tokenJWT = utilidadesJWT.generacionTokenJWT(claimsUsuario);
		claimsUsuario.put("token", tokenJWT);

		return claimsUsuario;
	}

	@Override
	public String darDeAltaJugador(String tel, String nombre, String fechaNac, String email, String pass,
			long dniDelantera, long dniTrasera, String emailTutor1, long dniDelanteraTutor1,
			long dniTraseraTutor1, String emailTutor2, long dniDelanteraTutor2, long dniTraseraTutor2) throws IllegalArgumentException{

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
		
		if (dniDelantera < 0)
			throw new IllegalArgumentException("dniDelantera: no debe ser menor de cero");
		
		if (dniTrasera < 0)
			throw new IllegalArgumentException("dniTrasera: no debe ser menor de cero");
		
		if (emailTutor1 == null || emailTutor1.isEmpty())
			throw new IllegalArgumentException("emailTutor1: no debe ser nulo ni vacio");
		
		if (dniDelanteraTutor1 < 0)
			throw new IllegalArgumentException("dniDelanteraTutor1: no debe ser menor de cero");
		
		if (dniTraseraTutor1 < 0)
			throw new IllegalArgumentException("dniTraseraTutor1: no debe ser menor de cero");
		
		if (emailTutor2 == null || emailTutor2.isEmpty())
			throw new IllegalArgumentException("emailTutor2: no debe ser nulo ni vacio");
		
		if (dniDelanteraTutor2 < 0)
			throw new IllegalArgumentException("dniDelanteraTutor2: no debe ser menor de cero");

		if (dniTraseraTutor2 < 0)
			throw new IllegalArgumentException("dniTraseraTutor2: no debe ser menor de cero");

		if (repositorioUsuario.existsById(tel))
			return null;
		
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
        String passwordEncriptada = passwordEncoder.encode(pass);
        
		Jugador jugador = new Jugador(tel, nombre, LocalDate.parse(fechaNac, formatter), email, passwordEncriptada, "JUGADOR", dniDelantera,
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
        
        Page<JugadorDTO> jugadores = usuariosPaginados.map(usuario -> {
            Jugador jugador = (Jugador) usuario;
            String nombreEquipo = null;

            if (jugador.getEquipo() != null) {
                Equipo e = repositorioEquipo.findById(jugador.getEquipo()).orElse(null);
                if (e != null) {
                    nombreEquipo = e.getNombre();
                }
            }

            return new JugadorDTO(jugador, nombreEquipo); // Constructor con nombre de equipo
        });        
        return jugadores;
    }
	
	@Override
	public JugadorInfoDTO descargarInfoUsuario(String idUsuario) throws IllegalArgumentException, EntidadNoEncontrada{ 
		if (idUsuario == null || idUsuario.isEmpty())
			throw new IllegalArgumentException("idUsuario: no debe ser nulo ni vacio");

		if(!repositorioUsuario.existsById(idUsuario))
			throw new EntidadNoEncontrada("idUsuario: no existe el id");
		
		Jugador jugador = (Jugador) repositorioUsuario.findById(idUsuario).orElse(null);

		JugadorInfoDTO dto = new JugadorInfoDTO(jugador.getDniDelantera(), jugador.getDniTrasera(),
				jugador.getDniDelanteraTutor1(), jugador.getDniTraseraTutor1(), jugador.getDniDelanteraTutor2(),
				jugador.getDniTraseraTutor2());
		return dto;

	}

	@Override
	public EquipoIdDTO getEquipoDeJugador(String idUsuario) throws IllegalArgumentException, EntidadNoEncontrada{
		if (idUsuario == null || idUsuario.isEmpty())
			throw new IllegalArgumentException("idUsuario: no debe ser nulo ni vacio");

		if(!repositorioUsuario.existsById(idUsuario))
			throw new EntidadNoEncontrada("idUsuario: no existe el id");
		
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
			if (e.getEquipos() != null) {
			    String equiposFinal = e.getEquipos().stream()
			        .map(id -> repositorioEquipo.findById(id).orElse(null))
			        .filter(Objects::nonNull)
			        .map(Equipo::getNombre)
			        .collect(Collectors.joining(", "));
			    entrenador.setEquipos(equiposFinal);
			}

			return entrenador;
		});
	}

	@Override
	public String crearEntrenador(String tel, String nombre, String fechaNac, String email, String pass,
			long dniDelantera, long dniTrasera, long certificadoDelitosSexuales) throws IllegalArgumentException, EntidadNoEncontrada{

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

		if (dniDelantera < 0)
			throw new IllegalArgumentException("dniDelantera: no debe menor de cero");

		if (dniTrasera < 0)
			throw new IllegalArgumentException("dniTrasera: no debe menor de cerp");

		if (certificadoDelitosSexuales < 0)
			throw new IllegalArgumentException("certificadoDelitosSexuales: no debe menor de cero");

		if (repositorioUsuario.existsById(tel))
			throw new EntidadNoEncontrada("tel: no existe el id");
		
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
        String passwordEncriptada = passwordEncoder.encode(pass);
        
		Entrenador entrenador = new Entrenador(tel, nombre, LocalDate.parse(fechaNac, formatter), email, passwordEncriptada, "ENTRENADOR",
				dniDelantera, dniTrasera, certificadoDelitosSexuales);

		repositorioUsuario.save(entrenador);

		return entrenador.getTel();
	}

	@Override
	public EntrenadorDTO getEntrenador(String idEntrenador) throws IllegalArgumentException, EntidadNoEncontrada{
		if (idEntrenador == null || idEntrenador.isEmpty())
			throw new IllegalArgumentException("idEntrenador: no debe ser nulo ni vacio");

		if(!repositorioUsuario.existsById(idEntrenador))
			throw new EntidadNoEncontrada("idEntrenador: no existe el id");
		
		Entrenador entrenador = (Entrenador) repositorioUsuario.findById(idEntrenador).orElse(null);
		EntrenadorDTO dto = new EntrenadorDTO(entrenador.getTel(), entrenador.getDniDelantera(), entrenador.getDniTrasera(), entrenador.getCertificadoDelitosSexuales());
		return dto;
	}

	@Override
	public EquiposIdsDTO getEquiposDeEntrenador(String idEntrenador) throws IllegalArgumentException, EntidadNoEncontrada{
		if (idEntrenador == null || idEntrenador.isEmpty())
			throw new IllegalArgumentException("idEntrenador: no debe ser nulo ni vacio");

		if(!repositorioUsuario.existsById(idEntrenador))
			throw new EntidadNoEncontrada("idEntrenador: no existe el id");
		
		Entrenador entrenador = (Entrenador) repositorioUsuario.findById(idEntrenador).orElse(null);

		List<EquipoIdYNombreDTO> equipos = entrenador.getEquipos().stream()
				.map(equipo -> {
					Equipo e = repositorioEquipo.findById(equipo).orElse(null);
					return new EquipoIdYNombreDTO(equipo, e.getNombre());
				}).collect(Collectors.toList());	
		
		return new EquiposIdsDTO(equipos);
	}
	
	@Override
	public void modificarEntrenador(String telAntiguo, String telNuevo, String nombre, String fechaNac, String email, String pass,
			long dniDelantera, long dniTrasera, long certificadoDelitosSexuales) {
		Usuario usuario = repositorioUsuario.findById(telAntiguo).orElse(null);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        List<String> equipos = ((Entrenador) usuario).getEquipos();
		for (String string : equipos) {
			Equipo e = repositorioEquipo.findById(string).orElse(null);
			e.removeEntrenador(telAntiguo);
			repositorioEquipo.save(e);
		}
		
		((Entrenador) usuario).modificar(telNuevo, nombre, LocalDate.parse(fechaNac, formatter), email, pass, dniDelantera, dniTrasera,
				certificadoDelitosSexuales);
		
		
		for (String string : equipos) {
			Equipo e = repositorioEquipo.findById(string).orElse(null);
			e.addEntrenador((Entrenador)usuario);
			repositorioEquipo.save(e);
		}
		
		repositorioUsuario.deleteById(telAntiguo);
		repositorioUsuario.save(usuario);
		return;
	}

	@Override
	public boolean cambiarPass(String idUsuario, String oldPass, String newPass) throws IllegalArgumentException, EntidadNoEncontrada{
		
		if (idUsuario == null || idUsuario.isEmpty())
			throw new IllegalArgumentException("idUsuario: no debe ser nulo ni vacio");
		
		Usuario usuario = repositorioUsuario.findById(idUsuario).orElseGet(null);
		
		if ((usuario == null)||(!passwordEncoder.matches(oldPass, usuario.getPass())))
			throw new EntidadNoEncontrada("usuario: no existe el id");
		
		usuario.setPass(passwordEncoder.encode(newPass));
		((Entrenador)usuario).setDebeCambiarPassword(false);
		repositorioUsuario.save(usuario);
		
		return true;
	}
	
	@Override
	public void borrarEntrenador(String idEntrenador) throws IllegalArgumentException{
		if (idEntrenador == null || idEntrenador.isEmpty())
			throw new IllegalArgumentException("idEntrenador: no debe ser nulo ni vacio");

		Usuario usuario = repositorioUsuario.findById(idEntrenador).orElseGet(null);
		
		((Entrenador)usuario).getEquipos().stream().forEach(e ->{
																Equipo eq = repositorioEquipo.findById(e).orElse(null);
																eq.removeEntrenador(idEntrenador);
																repositorioEquipo.save(eq);
		});
		
		repositorioUsuario.deleteById(idEntrenador);

		return;
	}

}
