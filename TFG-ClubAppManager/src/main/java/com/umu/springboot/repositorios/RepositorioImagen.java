package com.umu.springboot.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umu.springboot.modelo.Imagen;

public interface RepositorioImagen extends JpaRepository<Imagen, Long>{

}
