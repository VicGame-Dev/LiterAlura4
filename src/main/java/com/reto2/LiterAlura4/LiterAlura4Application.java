package com.reto2.LiterAlura4;

import com.reto2.LiterAlura4.model.DatosLibro;
import com.reto2.LiterAlura4.principal.Principal;
import com.reto2.LiterAlura4.repository.AutorRepository;
import com.reto2.LiterAlura4.repository.LibroRepository;
import com.reto2.LiterAlura4.service.ConsumoAPI;
import com.reto2.LiterAlura4.service.ConvierteDatos;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class LiterAlura4Application implements CommandLineRunner {

	@Autowired
	private LibroRepository libroRepository;
	@Autowired
	private AutorRepository autorRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiterAlura4Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(libroRepository, autorRepository);
		principal.interaccionUsuario();
	}
}


