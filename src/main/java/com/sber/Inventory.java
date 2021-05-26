package com.sber;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class Inventory {

    private List<Item> items;

    public Inventory() {
        items = new ArrayList<>();
    }

    public void add(Item item) {
        if (item != null)
            items.add(item);
    }

    public void remove(Item item) {
        items.remove(item);
    }

    public void show() {
        if (items.size() != 0)
            items.forEach(System.out::println);
        else
            System.out.println("Empty :(");
    }

    public Item find(String name) {
        return items.stream().filter(item -> name.toLowerCase().equals(item.getName().toLowerCase())).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(Item item : items) {
            result.append(item.getName() + " - " + item.getDescription() + "\n");
        }
        return result.toString();
    }
}
