package com.integralvending.ivdetectiondemo.models;

import androidx.annotation.NonNull;

public class MCharola {

    private int idRectangulo;
    private int numero;
    private int nuevonumero;

    public MCharola(int idRectangulo, int numero, int nuevonumero) {
        this.idRectangulo = idRectangulo;
        this.numero = numero;
        this.nuevonumero = nuevonumero;
    }

    public int getIdRectangulo() {
        return idRectangulo;
    }

    public int getNumero() {
        return numero;
    }

    public int getNuevonumero() {
        return nuevonumero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    @NonNull
    @Override
    public String toString() {
        return "MCharola{" +
                "idRectangulo=" + idRectangulo +
                ", Numero='" + numero + '\'' +
                ", Nuevonumero='" + nuevonumero + '\'' +
                '}';
    }
}
