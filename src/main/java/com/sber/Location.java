package com.sber;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Data
public class Location implements Cloneable{
    private String name;
    private String description;
    private Inventory inventory;
    private Map<Direction, Location> directions;

    public Location() {
        name = "Ground";
        description = "Just ground and grass";
        inventory = new Inventory();
        directions = new HashMap<>();
    }

    public Location(String name, String description) {

        this.name = name;
        this.description = description;
        inventory = new Inventory();
        directions = new HashMap<>();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Location findNext(String name) {
        Set<Map.Entry<Direction, Location>> entries = directions.entrySet();
        Map.Entry<Direction, Location> location = entries.stream()
                .filter(entry -> name.toUpperCase().equals(entry.getKey().name()))
                .findFirst()
                .orElse(null);
        return location == null ? null : location.getValue();
    }

    public void putOn(Item item) {
        if (item != null)
            inventory.add(item);
    }

    public Item pickUp(String name) {
        Item item = inventory.find(name);
        if (item != null && item.getMoveable() == Moveable.MOBILE) {
            inventory.remove(item);
            return (item);
        }
        return null;
    }
}
