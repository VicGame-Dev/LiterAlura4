package com.reto2.LiterAlura4.repository;

import com.reto2.LiterAlura4.model.Autor;
import com.reto2.LiterAlura4.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    @Query("SELECT l FROM Libro l WHERE l.titulo = :title")
    Optional<Libro> findByTitulo(String title);

    Optional<Libro> findById(long id);

    @Query("SELECT l FROM Libro l WHERE l.autor.id = :autorId")
    List<Libro> findByAutorId(Long autorId);

    Optional<Libro> findLibroByTitulo(String titulo);

    @Query("SELECT DISTINCT l.idioma FROM Libro l")
    List<String> findDistinctIdiomas();

    @Query("SELECT l FROM Libro l WHERE l.idioma = :language")
    List<Libro> findByIdioma(String language);

    @Query("SELECT l FROM Libro l WHERE l.autor = :autor")
    List<Libro> findLibrosByAutor(Autor autor);
}
