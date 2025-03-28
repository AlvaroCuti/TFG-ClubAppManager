package com.umu.springboot.repositorios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.umu.springboot.modelo.Usuario;

@NoRepositoryBean
public interface RepositorioUsuario extends PagingAndSortingRepository<Usuario, String>{
	
	Page<Usuario> filtrarUsuarios(String nombre, String tel, String fechaNac, String email, String emailTutor1,
			String emailTutor2, Pageable pageable);
	
	Page<Usuario> findByRol(String rol, Pageable paginacion);
}
