package com.umu.springboot.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umu.springboot.modelo.Archivo;

public interface RepositorioArchivo extends JpaRepository<Archivo, Long>{

}
