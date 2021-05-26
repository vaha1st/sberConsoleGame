package com.sber.initialization;

import com.sber.*;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class GameInitializer {

    int mapSize;
    int goal;
    Player player;
    List<Item> items;
    List<Item> treasures;
    List<String> locationNames;
    Location map;
    static int count;

    public GameInitializer(int mapSize) {

        // Set item list
        this.mapSize = mapSize;
        goal = mapSize * 10;
        items = new ArrayList<>();
        items.add(new Item("Bandage", "Put on wound to stop bleeding. " +
                "Or use it with Hydrogen Peroxide", 1, 0, Moveable.MOBILE));
        items.add(new Item("HydrogenPeroxide", "Disinfects well. Try to use it with Bandage", 1, 0, Moveable.MOBILE));
        items.add(new Item("Medkit", "Really helps", 50, 0, Moveable.MOBILE));
        items.add(new Item("Coin", "Just a coin", 0, 1, Moveable.MOBILE));
        items.add(new Item("Silver bar", "Silver good against vampires", 0, 20, Moveable.MOBILE));
        items.add(new Item("Gold bar", "Do not show anybody", 0, 100, Moveable.MOBILE));

        // Set treasures list
        treasures = new ArrayList<>();
        treasures.add(new Item("Gold bar", "Do not show anybody", 0, 100, Moveable.MOBILE));
        treasures.add(new Item("Golden Axe", "Hello sega", 0, 200, Moveable.MOBILE));
        treasures.add(new Item("Ruby", "Red. Shiny", 0, 70, Moveable.MOBILE));
        treasures.add(new Item("Gems", "Handful of beautiful stones", 0, 150, Moveable.MOBILE));

        // Set location names
        locationNames = new ArrayList<>();
        locationNames.add("Ground.Just ground and grass.");
        locationNames.add("Dark room.Very dark.");
        locationNames.add("Tomb.Ancient halls.");
        locationNames.add("Lake.Beautiful waters.");

        // Map
        try {
            map = generateMap(mapSize, null, null);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        // Place player
        player = new Player(map, getAvailableCombos());

        // Place chests with treasures
        count = mapSize/8;
        while (count > 0) {
            placeOnMap(
                    map,
                    new BasicPlacer(),
                    new Item("Chest", "Need a key to open", Moveable.STATIONARY),
                    null
            );
        }

        // Place keys
        count = mapSize/12;
        while (count > 0) {
            placeOnMap(
                    map,
                    new BasicPlacer(),
                    new Item("Key", "Opens something", Moveable.MOBILE),
                    null
            );
        }

        // Place monsters
        count = mapSize/6;
        while (count > 0) {
            placeOnMap(
                    map,
                    new BasicPlacer(),
                    new Item("Monster", "Fights everyone nearby", Moveable.STATIONARY),
                    null
            );
        }
    }

    public Item getRandomItem(List<Item> itemsList) throws CloneNotSupportedException {
        Random random = new Random();
        Item item = itemsList.get(random.nextInt(itemsList.size()));
        return (Item) item.clone();
    }

    private Location getRandomLocation(List<String> names) {
        Random random = new Random();
        String[] name = names.get(random.nextInt(names.size())).split("\\.");
        if (name.length != 2)
            return new Location();
        return new Location(name[0], name[1]);
    }

    private Direction getOppositeDierction(Direction direction) {
        switch (direction) {
            case UP:
                return Direction.DOWN;
            case DOWN:
                return Direction.UP;
            case NORTH:
                return Direction.SOUTH;
            case SOUTH:
                return Direction.NORTH;
            case WEST:
                return Direction.EAST;
            case EAST:
                return Direction.WEST;
        }
        return null;
    }

    public Location generateMap(int size, Direction prevDirection, Location previous) throws CloneNotSupportedException {
        Location current = getRandomLocation(locationNames);
        Random random = new Random();

        if (size > 0) {
            int directions = random.nextInt(Math.min(size, 5)) + 1;
            int itemAmount = random.nextInt(4);
            for (int j = 0; j < itemAmount; j++) {
                if (random.nextInt(100) > 50) {
                    current.getInventory().add(getRandomItem(items));
                }
            }
            if (directions > size) {
                directions = size;
            }
            size -= directions;
            for (int i = 0; i < directions; i++) {
                List<Direction> availableDirections = new ArrayList<>();
                for (Direction dir : Direction.values()) {
                    if (!(current.getDirections().containsKey(dir))) {
                        availableDirections.add(dir);
                    }
                }
                Direction curDirection = availableDirections.get(random.nextInt(availableDirections.size()));
                current.getDirections().put(curDirection,
                        generateMap(size / directions, getOppositeDierction(curDirection), current)
                );
            }
        }
        if (prevDirection != null && previous != null)
            current.getDirections().put(prevDirection, previous);
        return current;
    }

    public void placeOnMap(Location map, Placer placer, Item item, Location previous) {
        if (count <= 0) {
            return;
        }
        if (previous == null) {
            previous = new Location();
        } else {
            count -= placer.place(map, item);
        }
        if (!map.getDirections().entrySet().isEmpty()) {
            Location finalPrevious = previous;
            for (Location location : map.getDirections().values().stream()
                    .filter(location -> !location.equals(finalPrevious))
                    .collect(Collectors.toList())) {
                placeOnMap(location, placer, item, map);
            }
        }
    }

    private List<Combo> getAvailableCombos() {
        List<Combo> combos = new ArrayList<>();
        combos.add(new Combo(
                items.get(1),
                items.get(2),
                items.get(3),
                "Alchemy at work. You've git a medkit"
        ));
        try {
            combos.add(new Combo(
                    new Item("Chest", "Need a key to open", Moveable.STATIONARY),
                    new Item("Key", "Opens something", Moveable.MOBILE),
                    getRandomItem(treasures),
                    "Alchemy at work. You've git a medkit"
            ));
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        List<Combo> opposite = new ArrayList<>();
        for (Combo combo : combos) {
            opposite.add(new Combo(combo.getSubject(), combo.getObject(), combo.getResult(), combo.getMessage()));
        }
        combos.addAll(opposite);
        return combos;
    }
}
