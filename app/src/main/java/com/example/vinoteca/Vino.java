package com.example.vinoteca;

public class Vino {
    String nombre;
    String DenominacionOrigen;
    String Tipo;
    Boolean Probado;
    String Valoracion;
    public Vino(){

    }

    public Vino(String nombre, String denominacionOrigen) {
        this.nombre = nombre;
        DenominacionOrigen = denominacionOrigen;
    }

    public Vino(String nombre, String tipo, Boolean probado, String valoracion) {
        this.nombre = nombre;
        this.Tipo = tipo;
        this.Probado = probado;
        this.Valoracion = valoracion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDenominacionOrigen() {
        return DenominacionOrigen;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public Boolean getProbado() {
        return Probado;
    }

    public void setProbado(Boolean probado) {
        Probado = probado;
    }

    public String getValoracion() {
        return Valoracion;
    }

    public void setValoracion(String valoracion) {
        Valoracion = valoracion;
    }
}
