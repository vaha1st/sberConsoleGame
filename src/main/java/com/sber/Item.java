package com.sber;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Item implements Cloneable{
    private String name;
    private String description;
    int     hp;
    int     cost;
    private Moveable moveable;

    public Item(String name, String description, Moveable moveable) {
        this.name = name;
        this.description = description;
        this.moveable = moveable;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return name + " - " + description + (hp != 0 ? ", HP=" + hp : "") + (cost != 0 ? ", cost=" + cost : "");
    }
}
