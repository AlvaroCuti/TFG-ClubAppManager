package com.umu.springboot.repositorios;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.umu.springboot.modelo.Usuario;

public interface RepositorioUsuario extends PagingAndSortingRepository<Usuario, String>{

}
