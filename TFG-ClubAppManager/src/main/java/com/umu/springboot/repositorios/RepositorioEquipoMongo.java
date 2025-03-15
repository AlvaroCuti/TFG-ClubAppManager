package com.umu.springboot.repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.umu.springboot.modelo.Equipo;

public interface RepositorioEquipoMongo extends RepositorioEquipo, MongoRepository<Equipo, String>{

}
