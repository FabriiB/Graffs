package com.example.fabricio.graffs;

/**
 * Created by Fabricio on 20/8/17.
 */

public class Arista {
    //El punto 1 es el del nodo A y el punto 2 sera del nodo B para asi poder unirlos y luego hacer verificacion de conexiones
    private float x1,y1,x2,y2;
    private boolean dir = false;
    private Nodo uno;
    private Nodo dos;
    private int holgura,peso;


    public Arista(float x1, float y1, float x2, float y2, boolean dir, Nodo uno, Nodo dos) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.dir = dir;
        this.uno = uno;
        this.dos = dos;
    }
    public double pendiente()
    {
        double m=0;
        if(x1>x2 && y1>y2)
        {
            m=(x1-x2)/(y1-y2);
        }
        else if(x2>x1 && y2>y1)
        {
            m=(x2-x1)/(y2-y1);
        }
        return m;
    }
    public int getHolgura() {
        return holgura;
    }

    public void setHolgura(int holgura) {
        this.holgura = holgura;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public float getX1() {
        return x1;
    }

    public void setX1(float x1) {
        this.x1 = x1;
    }

    public float getY1() {
        return y1;
    }

    public void setY1(float y1) {
        this.y1 = y1;
    }

    public float getX2() {
        return x2;
    }

    public void setX2(float x2) {
        this.x2 = x2;
    }

    public float getY2() {
        return y2;
    }

    public void setY2(float y2) {
        this.y2 = y2;
    }

    public boolean isDir() {
        return dir;
    }

    public void setDir(boolean dir) {
        this.dir = dir;
    }

    public Nodo getUno() {
        return uno;
    }

    public void setUno(Nodo uno) {
        this.uno = uno;
    }

    public Nodo getDos() {
        return dos;
    }

    public void setDos(Nodo dos) {
        this.dos = dos;
    }
}
