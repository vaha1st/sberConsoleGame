package com.sber.initialization;

import com.sber.Item;
import com.sber.Location;
import com.sber.Moveable;

import java.util.Random;

public class BasicPlacer implements Placer{
    Random random = new Random();

    @Override
    public int place(Location location, Item item) {
        if (random.nextInt(100) < 10) {
            location.putOn(item);
            return 1;
        }
        return 0;
    }
}
