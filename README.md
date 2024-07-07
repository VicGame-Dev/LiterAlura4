### LiterAlura4

Este proyecto es una aplicación Java desarrollada como parte del reto 2 de LiterAlura. Permite gestionar libros y autores a través de una interfaz interactiva con el usuario.

### Funcionalidades

1. **Obtener Libro**
   - Permite buscar un libro por su título y mostrar detalles del mismo.
   
2. **Listar Libros por Idioma**
   - Muestra una lista de idiomas disponibles y permite listar libros en un idioma específico.
   
3. **Listar Libros**
   - Muestra todos los libros registrados en la base de datos.
   
4. **Listar Autores**
   - Muestra todos los autores registrados en la base de datos.
   
5. **Listar Autores Vivos**
   - Permite encontrar autores que estaban vivos en un año específico.

### Estructura del Proyecto

- **Modelo**: Contiene las clases de modelo como `Autor`, `Libro` y `DatosLibro`.
- **Repositorio**: Contiene las interfaces de repositorio para acceder a los datos de autores y libros.
- **Servicio**: Contiene las clases de servicio para la lógica de negocio, incluyendo la interacción con la API.
- **Principal**: Clase principal que gestiona la interacción con el usuario.
- **Aplicación**: Clase principal `LiterAlura4Application` para ejecutar la aplicación.

### Tecnologías Utilizadas

- Java: Lenguaje de programación principal.
- Spring Boot: Framework para aplicaciones Java.
- Spring Data JPA: Facilita el acceso a datos en aplicaciones Java.
- JSON: Formato de intercambio de datos.
- API Rest: Comunicación con servicios web.
- Maven: Herramienta de gestión de proyectos.

### Instrucciones de Uso

1. Ejecutar la clase `LiterAlura4Application` para iniciar la aplicación.
2. Seguir las instrucciones en consola para interactuar con la aplicación.
