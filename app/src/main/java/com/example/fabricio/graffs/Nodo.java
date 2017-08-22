package com.example.fabricio.graffs;

/**
 * Created by Fabricio on 20/8/17.
 */

public class Nodo {
    private float x, y;
    private int id;
    private String color;
    private boolean selected = false;

    public Nodo(float x, float y, int id,String color) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSelected(boolean selected) { this.selected = selected; }

    public boolean getSelected(){ return this.selected; }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getColor(){ return color;}

    public void setColor(String color){ this.color=color;}

}