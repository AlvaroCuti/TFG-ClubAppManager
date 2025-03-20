package com.umu.springboot.repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.umu.springboot.modelo.Entrenamiento;

public interface RepositorioEntrenamientoMongo extends RepositorioEntrenamiento, MongoRepository<Entrenamiento, String>{

}
