package com.reto2.LiterAlura4.service;

import com.reto2.LiterAlura4.model.Autor;
import com.reto2.LiterAlura4.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    public List<Autor> getAutoresVivos(int year) {
        return autorRepository.findAutorByFecha(year);
    }
}
