package com.alurachallenge.literatura.model;

import com.alurachallenge.literatura.anexos.Cadena;
import jakarta.persistence.*;

@Entity
@Table(name = "Libro")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String lenguaje;
    private Integer numeroDeDescargas;
    @OneToOne(mappedBy = "libros", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Autor autor;

    public Libro(){
    }

    public Libro(DatosLibro datosLibro){
        this.titulo = Cadena.limitarLongitud(datosLibro.title(), 200);
        this.numeroDeDescargas = datosLibro.download();
        if (!datosLibro.languages().isEmpty())
            this.lenguaje = datosLibro.languages().get(0);
        if (!datosLibro.autores().isEmpty()) {
            for (Autor autor : datosLibro.autores()) {
                this.autor = new Autor(autor);
                break;
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Integer numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public String getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(String lenguaje) {
        this.lenguaje = lenguaje;
    }

    @Override
    public String toString() {
        return "Libro id = " + id +
                ", titulo = " + titulo +
                ", idioma = " + lenguaje + ", descargas = " + numeroDeDescargas +
                ", autores = " + autor ;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }
}