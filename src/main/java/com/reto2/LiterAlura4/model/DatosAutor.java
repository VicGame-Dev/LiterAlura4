package com.reto2.LiterAlura4.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(
        @JsonAlias("birth_year") int nacimiento,
        @JsonAlias("death_year") int fallecimiento,
        @JsonAlias("name") String nombre
) {
}
