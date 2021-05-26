package com.sber.initialization;

import com.sber.Item;
import com.sber.Location;

@FunctionalInterface
public interface Placer {
    int place(Location location, Item item);
}
