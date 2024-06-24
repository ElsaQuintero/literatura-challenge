package com.alurachallenge.literatura.principal;

import com.alurachallenge.literatura.model.Autor;
import com.alurachallenge.literatura.model.Inicio;
import com.alurachallenge.literatura.model.Libro;
import com.alurachallenge.literatura.repository.AutorRepository;
import com.alurachallenge.literatura.repository.LibroRepository;
import com.alurachallenge.literatura.service.ConsumoAPI;
import com.alurachallenge.literatura.service.ConvierteDatos;

import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();

    private LibroRepository libroRepositorio;
    private AutorRepository autorRepositorio;

    public Principal(LibroRepository libroRepositorio, AutorRepository autorRepositorio) {
        this.libroRepositorio = libroRepositorio;
        this.autorRepositorio = autorRepositorio;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    Elija la opción a través de se número:
                    1 - Buscar libro por titulo 
                    2 - Mostrar lista de libros registrados
                    3 - Mostrar lista de autores registrados
                    4 - Mostrar lista de autores vivos en un determinado año
                    5 - Mostrar lista de libros por idioma
                                  
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroWeb();
                    break;
                case 2:
                    mostrarLibrosRegistrados();
                    break;
                case 3:
                    mostrarAutoresRegistrados();
                    break;
                case 4:
                    mostrarAutoresVivosEnFechaDeterminada();
                    break;
                case 5:
                    mostrarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void mostrarLibrosRegistrados(){
        List<Libro> libros = libroRepositorio.findAll();
        if (!libros.isEmpty()) {
            for (Libro libro : libros) {
                System.out.println("\n\n-------------- LIBROS ------------\n");
                System.out.println(" Titulo: " + libro.getTitulo());
                System.out.println(" Autor: " + libro.getAutor().getNombre());
                System.out.println(" Idioma: " + libro.getLenguaje());
                System.out.println(" Descargas: " + libro.getNumeroDeDescargas());
                System.out.println("\n-------------------------\n\n");
            }
        } else {
            System.out.println("\n\n ----- No encontré ese libro ---- \n\n");
        }
    }

    private void mostrarAutoresRegistrados() {
        List<Autor> autores = autorRepositorio.findAll();

        if (!autores.isEmpty()) {
            for (Autor autor : autores) {
                System.out.println("\n\n------------ Autores -----------\n");
                System.out.println(" Nombre: " + autor.getNombre());
                System.out.println(" Fecha de Nacimiento: " + autor.getFechaDeNacimiento());
                System.out.println(" Fecha de Fallecimiento: " + autor.getFechaDeFallecimiento());
                System.out.println(" Libros: " + autor.getLibros().getTitulo());
                System.out.println("\n-------------------------\n\n");
            }
        } else {
            System.out.println("\n\n ----- No encontré ese autor ---- \n\n");
        }
    }

    private void mostrarAutoresVivosEnFechaDeterminada() {
        System.out.println("Escribe el año para encontrar autores vivos en ese año:");
        int fecha = teclado.nextInt();
        teclado.nextLine();

        List<Autor> autoresVivosEnFecha = autorRepositorio.findAutoresVivosEnFecha(fecha);

        if (autoresVivosEnFecha.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año " + fecha);
        } else {
            System.out.println("Autores vivos en el año " + fecha + ":");
            for (Autor datosAutor : autoresVivosEnFecha) {
                System.out.println("\n\n---------- Autores ----------\n");
                System.out.println(" Nombre: " + datosAutor.getNombre());
                System.out.println(" Fecha de nacimiento: " + datosAutor.getFechaDeNacimiento());
                System.out.println(" Fecha de fallecimiento: " + datosAutor.getFechaDeFallecimiento());
                System.out.println(" Libros: " + datosAutor.getLibros().getTitulo());
                System.out.println("\n-------------------------\n\n");
            }
        }
    }

    private void mostrarLibrosPorIdioma() {
        var menu = """
                Seleccione un Idioma:
                    1.- Español
                    2.- Inglés
                    3.- Francés
                    4.- Alemán
                    5.- Italiano
                
                    """;
        System.out.println(menu);
        var idioma = teclado.nextInt();
        teclado.nextLine();

        String seleccion = "";

        switch (idioma) {
            case 1:
                seleccion = "es";
                break;
            case 2:
                seleccion = "en";
                break;
            case 3:
                seleccion = "fr";
                break;
            case 4:
                seleccion = "de";
                break;
            case 5:
                seleccion = "it";
                break;
            default:
                System.out.println("Opción inválida. Selecciona un idioma válido.");
                return;
        }

        List<Libro> libros = libroRepositorio.findForLanguaje(seleccion);

        if (!libros.isEmpty()) {
            for (Libro libro : libros) {
                System.out.println("\n\n---------- LIBROS POR IDIOMA-------\n");
                System.out.println(" Titulo: " + libro.getTitulo());
                System.out.println(" Autor: " + libro.getAutor().getNombre());
                System.out.println(" Idioma: " + libro.getLenguaje());
                System.out.println(" Descargas: " + libro.getNumeroDeDescargas());
                System.out.println("\n-------------------------\n\n");
            }
        } else {
            System.out.println("\n\n ----- No se encontró ese libro ---- \n\n");
        }
    }

    private void buscarLibroWeb() {
        Inicio datos = getDatosSerie();
        if (!datos.results().isEmpty()) {
            Libro libro = new Libro(datos.results().get(0));
            libro = libroRepositorio.save(libro);
        }
        System.out.println("Datos: ");
        System.out.println(datos);
    }

    private Inicio getDatosSerie() {
        System.out.println("Ingresa el nombre del libro que deseas buscar: ");
        var titulo = teclado.nextLine();
        titulo = titulo.replace(" ", "+");
        System.out.println("Titlulo : " + titulo);
        System.out.println(URL_BASE + titulo);
        var json = consumoAPI.obtenerDatos(URL_BASE + titulo);
        System.out.println(json);
        Inicio datos = conversor.obtenerDatos(json, Inicio.class);
        return datos;
    }
}