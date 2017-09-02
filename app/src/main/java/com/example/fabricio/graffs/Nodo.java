package com.example.fabricio.graffs;

/**
 * Created by Fabricio on 20/8/17.
 */

public class Nodo {
    private float x, y;
    private int id;
    private String color;
    private boolean selected = false;
    private int start,feed; // Entrada para el primer recorrido, y para el feedback el segundo valor

    public Nodo(float x, float y, int id,String color,int start,int feed) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.color = color;
        this.start = start;
        this.feed = feed;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getFeed() {
        return feed;
    }

    public void setFeed(int feed) {
        this.feed = feed;
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