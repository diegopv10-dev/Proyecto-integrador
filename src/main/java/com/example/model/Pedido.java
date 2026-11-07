package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numeroMesa;
    private String descripcionPlatos;
    private String totalVentas;
    private String horaPedido;
    private String estado;

    // ===================
    // Constructores
    // ===================
    public Pedido() {
    }

    public Pedido(String numeroMesa, String descripcionPlatos, String totalVentas, String horaPedido, String estado) {
        this(null, numeroMesa, descripcionPlatos, totalVentas, horaPedido, estado);
    }

    public Pedido(Long id, String numeroMesa, String descripcionPlatos, String totalVentas, String horaPedido, String estado) {
        this.id = id;
        this.numeroMesa = numeroMesa;
        this.descripcionPlatos = descripcionPlatos;
        this.totalVentas = totalVentas;
        this.horaPedido = horaPedido;
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

    public String getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(String numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public String getDescripcionPlatos() {
        return descripcionPlatos;
    }

    public void setDescripcionPlatos(String descripcionPlatos) {
        this.descripcionPlatos = descripcionPlatos;
    }

    public String getTotalVentas() {
        return totalVentas;
    }

    public void setTotalVentas(String totalVentas) {
        this.totalVentas = totalVentas;
    }

    public String getHoraPedido() {
        return horaPedido;
    }

    public void setHoraPedido(String horaPedido) {
        this.horaPedido = horaPedido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Pedido: [ id=" + id + ", numero mesa= " + numeroMesa + ", descripción Plato=" + descripcionPlatos + " total venta =" + totalVentas + " hora pedido = " + horaPedido + "estado = " + estado + " ]";
    }
}
