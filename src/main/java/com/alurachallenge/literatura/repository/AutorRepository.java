package com.alurachallenge.literatura.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alurachallenge.literatura.model.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    @Query("SELECT a FROM Autor a WHERE :fecha between a.fechaDeNacimiento AND a.fechaDeFallecimiento")
    List<Autor> findAutoresVivosEnFecha(int fecha);
}