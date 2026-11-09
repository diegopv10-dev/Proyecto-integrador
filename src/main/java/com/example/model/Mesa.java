package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "mesas")
public class Mesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String numeroMesa;
    private String capacidadPersonas;
    private String ubicacion; //terraza, salon principal, balcon
    private String estado; // Libre, ocupada, reservada
    

    // ===================
    // Constructores
    // ===================
    public Mesa() {
    }

    public Mesa(String nombre, String numeroMesa, String capacidadPersonas, String ubicacion, String estado) {
        this(null, nombre, numeroMesa, capacidadPersonas, ubicacion, estado);
    }

    public Mesa(Long id, String nombre, String numeroMesa, String capacidadPersonas, String ubicacion, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.numeroMesa = numeroMesa;
        this.capacidadPersonas = capacidadPersonas;
        this.ubicacion = ubicacion;
        this.estado = estado;
    }

    // ===================
    // Getters y setters
    // ===================
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

    public String getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(String numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public String getCapacidadPersonas() {
        return capacidadPersonas;
    }

    public void setCapacidadPersonas(String capacidadPersonas) {
        this.capacidadPersonas = capacidadPersonas;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Mesa [id=" + id + ", nombre=" + nombre + ", numero Mesa =" + numeroMesa + "Capacidad de personas = " + capacidadPersonas + "Ubicacion = " + ubicacion + "estado de mesa = " + estado + "]";
    }
}
