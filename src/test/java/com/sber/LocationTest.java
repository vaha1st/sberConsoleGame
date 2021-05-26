package com.sber;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class LocationTest {

    @Test
    @DisplayName("проверка найти на пустой локации")
    public void testFindNextEmpty () {
        Location location = new Location();
        Assert.assertNull(location.findNext("foo"));
    }

    @Test
    @DisplayName("проверка найти следующую локацию стандарт")
    public void testFindNext () {
        Location location = new Location();
        location.getDirections()
                .put(Direction.NORTH, new Location("asphalt", "Black as heart of our ex"));
        Assert.assertEquals(location.findNext("north"),
                new Location("asphalt", "Black as heart of our ex"));
    }

    @Test
    @DisplayName("Проверка putOn стандарт")
    public void testPutOn() {
        Location location = new Location();
        location.putOn(new Item("1", "1", Moveable.MOBILE));
        Assert.assertNotNull(location.getInventory().getItems().get(0));
    }

    @Test
    @DisplayName("Проверка pickUp стандарт")
    public void testPickUp() {
        Location location = new Location();
        location.getInventory().add(new Item("1", "1", Moveable.MOBILE));
        Assert.assertTrue(location.pickUp("1") != null && location.getInventory().getItems().size() == 0);
    }

    @Test
    @DisplayName("Проверка pickUp несуществующего объекта")
    public void testPickUpNonExist() {
        Location location = new Location();
        Assert.assertNull(location.pickUp("foo"));
    }

    @Test
    @DisplayName("Проверка pickUp неподъемного объекта")
    public void testPickUpStationary() {
        Location location = new Location();
        location.getInventory().add(new Item("1", "1", Moveable.STATIONARY));
        Assert.assertTrue(location.pickUp("1") == null
                && location.getInventory().find("1") != null);
    }
}
