package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "platos")
public class Plato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigoMenu;
    private String nombre;
    private String categoria; 
    private Double precio;
    private Boolean disponible; 

 
    public Plato() {
    }

    public Plato(String codigoMenu, String nombre, Double precio, Boolean disponible) {
        this(null, codigoMenu, nombre,"Hamburguesas", precio, disponible);
    }

    public Plato(String codigoMenu, String nombre, String categoria, Double precio, Boolean disponible) {
        this(null, codigoMenu, nombre, categoria, precio, disponible);
    }

    public Plato(Long id, String codigoMenu, String nombre, String categoria, Double precio, Boolean disponible) {
        this.id = id;
        this.codigoMenu = codigoMenu;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.disponible = disponible;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoMenu() {
        return codigoMenu;
    }

    public void setCodigoMenu(String codigoMenu) {
        this.codigoMenu = codigoMenu;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return "Plato [id=" + id + ", codigoMenu=" + codigoMenu + ", nombre=" + nombre + 
               ", categoria=" + categoria + ", precio=" + precio + ", disponible=" + disponible + "]";
    }
}