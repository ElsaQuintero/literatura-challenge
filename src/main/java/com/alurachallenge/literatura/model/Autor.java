package com.alurachallenge.literatura.model;

import com.alurachallenge.literatura.anexos.Cadena;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "Autor")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("name")
    private String nombre;
    @JsonProperty("birth_year")
    private Integer fechaDeNacimiento;
    @JsonProperty("death_year")
    private Integer fechaDeFallecimiento;

    @OneToOne
    @JoinTable(
            name = "Libro",
            joinColumns = @JoinColumn(name = "autor_id"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private Libro libros;

    public Autor() {
    }

    public Autor(Autor autor) {
        this.nombre = autor.nombre;
        this.fechaDeNacimiento = autor.fechaDeNacimiento;
        this.fechaDeFallecimiento = autor.fechaDeFallecimiento;
        this.libros = autor.libros;
    }

    public Autor(DatosAutor datosAutor) {
        this.nombre = Cadena.limitarLongitud(datosAutor.name(), 200);
        if (datosAutor.birth_year() == null)
            this.fechaDeNacimiento = 1980;
        else
            this.fechaDeNacimiento = datosAutor.birth_year();
        if (datosAutor.death_year() == null)
            this.fechaDeFallecimiento = 3022;
        else
            this.fechaDeFallecimiento = datosAutor.death_year();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Integer fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public Integer getFechaDeFallecimiento() {
        return fechaDeFallecimiento;
    }

    public void setFechaDeFallecimiento(Integer fechaDeFallecimiento) {
        this.fechaDeFallecimiento = fechaDeFallecimiento;
    }

    public Libro getLibros() {
        return libros;
    }

    public void setLibros(Libro libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return " id = " + id +
                ", nombre = '" + nombre + '\'' +
                ", fecha De Nacimiento = '" + fechaDeNacimiento + '\'' +
                ", fecha De Fallecimiento = '" + fechaDeFallecimiento + '\'' +
                ", libro(s) = " + libros;
    }
}