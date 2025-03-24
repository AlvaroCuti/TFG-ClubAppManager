package com.umu.springboot.repositorios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.umu.springboot.modelo.Usuario;

public interface RepositorioUsuarioMongo extends RepositorioUsuario, MongoRepository<Usuario, String> {
	
	@Query("{ " + "'nombre': { $regex: ?0, $options: 'i' }, " + "'tel': ?1, " + "'fechaNac': ?2, " + "'email': ?3, "
			+ "'emailTutor1': ?4, " + "'emailTutor2': ?5 " + "}")
	Page<Usuario> filtrarUsuarios(String nombre, String tel, String fechaNac, String email, String emailTutor1,
			String emailTutor2, Pageable pageable);
	
}
