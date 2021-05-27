package com.sber;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

@Data
public class Player implements Serializable {
    private int health;
    private int gold;
    private Location location;
    private Inventory inventory;
    private List<Combo> combos;

    public Player(Location location, List<Combo> combos) {
        this.location = location;
        this.combos = combos;
        health = 100;
        inventory = new Inventory();
    }

    public void lookAround() {
        System.out.println("Player " + System.getProperty("user.name") + ": " + getStatus());
        System.out.println(location.getName() + ". " + location.getDescription());
        System.out.println("Items on location:");
        location.getInventory().show();
        System.out.println("Available directions:");
        location.getDirections().forEach((key, value) ->
        {
            if ((value.getInventory().find("Monster") == null)) {
                System.out.println(key);
            } else {
                System.out.println(key + " !!!MONSTER!!!");
            }
        });
    }

    public void go(String direction) {
        Location tmp = location.findNext(direction.toUpperCase());
        if (tmp != null) {
            if(!location.getDescription().contains("(CHECKED)")) {
                location.setDescription("(CHECKED)" + location.getDescription());
            }
            location = tmp;
            while (location.getInventory().find("Monster") != null) {
                location.getInventory().remove(location.getInventory().find("Monster"));
                Random random = new Random();
                int fightResult = random.nextInt(51);
                health -= fightResult;
                if (health > 0) {
                    System.out.println("You killed the monster"
                            + (fightResult > 0 ? ", but you injured. -"
                            + fightResult + "HP" : ", without a scratch"));
                } else {
                    System.out.println("You have been eaten by the monster :(");
                }
            }
            if (health > 0) {
                lookAround();
            }
        } else {
            System.out.println("Cannot go to " + direction);
        }
    }

    public void take(String itemName) {
        Item item = location.getInventory().find(itemName);
        if (item != null) {
            if (item.getMoveable() == Moveable.MOBILE) {
                inventory.add(location.pickUp(itemName.toLowerCase()));
                System.out.println("Success");
            } else {
                System.out.println("Cannot pick up " + itemName + ". To heavy.");
            }
        } else {
            System.out.println("No such item: " + itemName);
        }
    }

    public void use(String obj, String subj) {
        Item object;
        Item subject;
        boolean objectFromInventory = false;
        boolean subjectFromInventory = false;
        if ((object = location.getInventory().find(obj.toLowerCase())) == null) {
            object = inventory.find(obj.toLowerCase());
            objectFromInventory = true;
        }
        if ((subject = location.getInventory().find(subj.toLowerCase())) == null) {
            subject = inventory.find(subj.toLowerCase());
            subjectFromInventory = true;
        }
        Combo tmp;
        if (object == null || subject == null) {
            System.out.println("Not enough components");
            return;
        }
        Item finalObject = object;
        Item finalSubject = subject;
        tmp = combos.stream()
                .filter(combo -> finalObject.equals(combo.getObject()) && finalSubject.equals(combo.getSubject()))
                .findFirst()
                .orElse(null);
        if (tmp == null) {
            System.out.println("Cannot use " + obj + " and " + subj);
        } else {
            if (objectFromInventory) {
                inventory.remove(object);
            } else {
                location.getInventory().remove(object);
            }
            if (subjectFromInventory) {
                inventory.remove(subject);
            } else {
                location.getInventory().remove(subject);
            }
            try {
                inventory.add((Item) tmp.getResult().clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            System.out.println("Result: " + tmp.getResult().getName());
        }
    }

    public void use(String obj) {
        Item item = inventory.find(obj);
        if (item != null && item.getHp() != 0) {
            health += item.getHp();
            if (health > 100) {
                health = 100;
            }
            System.out.println("Healed " + "+" + item.getHp() + "HP. Your health: " + health + "HP");
            inventory.remove(item);
        } else {
            System.out.println("Cannot use the object");
        }
    }

    public void inventory() {
        inventory.show();
    }

    public int getGold() {
        int sum = 0;
        for (Item item : inventory.getItems()) {
            sum += item.cost;
        }
        return sum;
    }

    private String getStatus() {
        return  "Health = " + health + " | Gold = " + getGold();
    }
}
