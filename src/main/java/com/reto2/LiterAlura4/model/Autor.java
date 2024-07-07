package com.reto2.LiterAlura4.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long autor_id;
    private Integer nacimiento;
    private Integer fallecimiento;
    @Column(unique = true)
    private String nombre;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor() {
    }

    public Autor(Long autor_id, Integer nacimiento, Integer fallecimiento, String nombre) {
        this.autor_id = autor_id;
        this.nacimiento = nacimiento;
        this.fallecimiento = fallecimiento;
        this.nombre = nombre;
    }

    public Autor(DatosAutor autor) {
        this.nacimiento = autor.nacimiento();
        this.fallecimiento = autor.fallecimiento();
        this.nombre = autor.nombre();
    }

    public Long getAutor_id() {
        return autor_id;
    }

    public void setAutor_id(Long autor_id) {
        this.autor_id = autor_id;
    }

    public Integer getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(Integer nacimiento) {
        this.nacimiento = nacimiento;
    }

    public Integer getFallecimiento() {
        return fallecimiento;
    }

    public void setFallecimiento(Integer fallecimiento) {
        this.fallecimiento = fallecimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    public String librosAutor (List<Libro> libros) {
        return libros.stream()
                .map(Libro::getTitulo).collect(Collectors.joining("\n"));
    }
    @Override
    public String toString() {
        return
                "nacimiento=" + nacimiento +
                ", fallecimiento=" + fallecimiento +
                ", nombre='" + nombre + '\'';
    }
}
