package com.integralvending.ivdetectiondemo.models;

public class MArticulo {

    private int idArticulo;
    private String nombre;
    private String idDetection;
    private int cantidad;


    public MArticulo(int idArticulo, String nombre, String idDetection, int cantidad) {
        this.idArticulo = idArticulo;
        this.nombre = nombre;
        this.idDetection = idDetection;
        this.cantidad = cantidad;
    }

    public int getIdArticulo() {
        return idArticulo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIdDetection() {
        return idDetection;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "MArticulo{" +
                "idArticulo=" + idArticulo +
                ", nombre='" + nombre + '\'' +
                ", idDetection='" + idDetection + '\'' +
                ", cantidad=" + cantidad +
                '}';
    }
}
