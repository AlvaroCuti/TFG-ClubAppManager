package com.umu.springboot.repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.umu.springboot.modelo.Usuario;

public interface RepositorioUsuarioMongo extends RepositorioUsuario, MongoRepository<Usuario, String>{

}
