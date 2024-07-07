package com.reto2.LiterAlura4.repository;

import com.reto2.LiterAlura4.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    List<Autor> findAutorByNombre(String nombre);

    @Query(value= "SELECT a FROM Autor a WHERE a.fallecimiento >:year AND a.nacimiento <:year")
    List<Autor> findAutorByFecha(int year);
}
