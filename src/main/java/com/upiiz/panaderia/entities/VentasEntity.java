package com.upiiz.panaderia.entities;
import jakarta.persistence.*;

@Entity(name="ventas")
public class VentasEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id_ventas;
   @ManyToOne
   @JoinColumn(name = "id_producto")
   private ProductoEntity producto;
   private String nombre;
   private double total;
   private int cantidad;

    public VentasEntity() {
    }

    public VentasEntity(int cantidad, String nombre, Long id_ventas, double total, ProductoEntity producto) {
        this.cantidad = cantidad;
        this.nombre = nombre;
        this.id_ventas = id_ventas;
        this.total = total;
        this.producto = producto;
    }

    public Long getId_ventas() {
        return id_ventas;
    }

    public void setId_ventas(Long id_ventas) {
        this.id_ventas = id_ventas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ProductoEntity getProducto() {
        return producto;
    }

    public void setProducto(ProductoEntity producto) {
        this.producto = producto;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
