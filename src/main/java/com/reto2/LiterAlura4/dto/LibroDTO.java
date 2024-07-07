package com.reto2.LiterAlura4.dto;

import com.reto2.LiterAlura4.model.Autor;

public record LibroDTO(
    Long id,
    String titulo,
    Autor autor,
    String idioma,
    int numeroDeDescargas
) {
}
