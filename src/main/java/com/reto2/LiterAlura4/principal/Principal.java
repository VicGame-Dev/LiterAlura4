package com.reto2.LiterAlura4.principal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reto2.LiterAlura4.model.Autor;
import com.reto2.LiterAlura4.model.DatosAutor;
import com.reto2.LiterAlura4.model.DatosLibro;
import com.reto2.LiterAlura4.model.Libro;
import com.reto2.LiterAlura4.repository.AutorRepository;
import com.reto2.LiterAlura4.repository.LibroRepository;
import com.reto2.LiterAlura4.service.AutorService;
import com.reto2.LiterAlura4.service.ConsumoAPI;
import com.reto2.LiterAlura4.service.ConvierteDatos;
import com.reto2.LiterAlura4.service.LibroService;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    public LibroRepository libroRepository;
    public AutorRepository autorRepository;
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private DatosLibro datosLibro;
    private DatosAutor datosAutor;
    private LibroService libroService = new LibroService();
    private AutorService autorService = new AutorService();

    private List<Libro> libros;
    private List<Autor> autores;
    private Autor autorActual;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    private int eleccion = -100;

    public void interaccionUsuario() throws Exception {
        var Menu = "Bienvenido a LiterAlura4 \n" +
                "OPCIONES \n" +
                "1. Buscar Libro \n" +
                "2. Listar libros registrados \n" +
                "3. Listar autores registrados \n" +
                "4. Listar autores vivos en el año (...) \n" +
                "5. Listar libros por idioma \n" +
                "0. Salir";

        do {
            System.out.println(Menu);
            while (!teclado.hasNextInt()) {
                System.out.println("Por favor, ingrese un número válido \n");
                teclado.next();
            }
            var eleccion = teclado.nextInt();
            teclado.nextLine();
            switch (eleccion) {
                case 1:
                    obtenerLibro();
                    break;
                case 2:
                    listarLibros();
                    break;
               case 3:
                    listarAutores();
                    break;
               case 4:
                    listarAutoresVivos();
                    break;
               case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opcion incorrecta");
            }
        } while (eleccion != 0);
    }


    public void obtenerLibro() throws Exception {
        System.out.println("Introduce el titulo del libro");
        var tituloLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(tituloLibro);
        System.out.println(json);

        JSONObject jsonObject = new JSONObject(json);
        JSONArray jsonArray = jsonObject.getJSONArray("results");

        if (jsonArray.isEmpty()) {
            System.out.println("No se ha encontrado el libro");
            return;
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            System.out.println(i+1+". "+jsonArray.getJSONObject(i).getString("title"));
        }

        System.out.println("Introduce el número del libro");
        while (!teclado.hasNextInt()) {
        System.out.println("Por favor, introduce un número válido \n");
        teclado.next();
        }
        var eleccion2 = teclado.nextInt();
        teclado.nextLine();
        jsonObject = new JSONObject(jsonArray.getJSONObject(eleccion2-1).toString());
        System.out.println(jsonObject.toString());
        datosLibro = convierteDatos.obtenerDatos(jsonObject.toString(), DatosLibro.class);
        System.out.println(datosLibro);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(jsonObject.toString(), Map.class);
        String autorResult = objectMapper.writeValueAsString((List<Map<String, Object>>) map.get("authors"));
        String jsonAutor = autorResult.substring(1, autorResult.length()-1);
        datosAutor = convierteDatos.obtenerDatos(jsonAutor.toString(), DatosAutor.class);
        System.out.println(datosAutor);
        String idioma = obtenerIdioma(map);
        System.out.println(idioma);

        autores = autorRepository.findAll();
        Optional<Autor> autor =autores.stream()
                    .filter(a -> a.getNombre().equals(datosAutor.nombre()))
                    .findFirst();
        if (autor.isPresent()) {
            autorActual = autor.get();
        } else {
            autorActual = new Autor(datosAutor);
            autorRepository.save(autorActual);
        }

        Libro libro = new Libro(datosLibro, autorActual, idioma);
        libroRepository.save(libro);
        System.out.println("Libro registrado con exito");
        System.out.println(libro);
    }

    // gracias a mi Bro Humberto
    public void hacerPause() {
        System.out.println( );
        Terminal terminal = null;
        try {
            terminal = TerminalBuilder.builder().build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LineReader lineReader = LineReaderBuilder.builder().terminal(terminal).build();

        System.out.println("Presione cualquier tecla para continuar...");

        // Esperar a que el usuario presione cualquier tecla (no necesita Enter)
        lineReader.readLine();

        // Cerrar la terminal
    }

    public String obtenerIdioma (Map<String, Object> idioma) {
        List<String> idiomas = (List<String>) idioma.get("languages");
        return idiomas.get(0);
    }

    public void listarLibros() {
        libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados");
        } else {
            System.out.println("Tus libros registrados son los siguientes: \n");
            libros.forEach(System.out::println);
            hacerPause();
        }
    }
   public void listarAutores() {
       autores = autorRepository.findAll();
       if (autores.isEmpty()) {
           System.out.println("No hay autores registrados");
       } else {
           System.out.println("Tus autores registrados son los siguientes: \n");
           autores.forEach(System.out::println);
           hacerPause();
       }
    }

    public void listarAutoresVivos() {
        System.out.println("Introduce el año");
        while (!teclado.hasNextInt()) {
            System.out.println("Por favor, introduce un número válido.");
            teclado.next();
        }
        int year = teclado.nextInt();
        teclado.nextLine();

        autores = autorRepository.findAll();
        List<Autor> autoresVivos = autores.stream()
                .filter(autor -> (autor.getNacimiento() <= year) &&
                        (autor.getFallecimiento() == null || autor.getFallecimiento() >= year))
                .toList();

        if (autoresVivos.isEmpty()) {
            System.out.println("No hay autores vivos en el año " + year + ".");
        } else {
            System.out.println("Autores vivos en el año " + year + ":");
            for (Autor autor : autoresVivos) {
                System.out.println(autor);
            }
        }
    }

    public void listarLibrosPorIdioma() {
        mostrarListaIdiomas();
        String idioma = obtenerCodigoIdioma();

        libros = libroRepository.findByIdioma(idioma);

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma '" + idioma + "'.");
        } else {
            System.out.println("Libros en el idioma '" + idioma + "':");
            for (Libro libro : libros) {
                System.out.println(libro);
                hacerPause();
            }
        }
    }

    public void mostrarListaIdiomas() {
        System.out.println("Selecciona un idioma por su código: (aquí unos ejemplos");
        System.out.println("af - Afrikáans");
        System.out.println("es - Español");
        System.out.println("en - Inglés");
        System.out.println("fr - Francés");
        System.out.println("de - Alemán");
        System.out.println("it - Italiano");
        System.out.println("pt - Portugués");
        System.out.println("ru - Ruso");
        System.out.println("zh - Chino (simplificado)");
        System.out.println("ja - Japonés");
        System.out.println("ko - Coreano");
        System.out.println("ar - Árabe");
        System.out.println("hi - Hindi");
        System.out.println("id - Indonesio");
        System.out.println("sw - Suajili");
        System.out.println("th - Tailandés");
        System.out.println("tr - Turco");
        System.out.println("vi - Vietnamita");
    }

    public String obtenerCodigoIdioma() {
        System.out.print("Introduce el código del idioma: ");
        var idiomaSeleccionado = teclado.nextLine().trim().toLowerCase();
        return idiomaSeleccionado;
    }
}