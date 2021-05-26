package com.sber;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class InventoryTest {

    private ByteArrayOutputStream output = new ByteArrayOutputStream();

    @Test
    @DisplayName("Проверка метода add стандарт")
    public void testAddItem(){
        Inventory inventory = new Inventory();
        inventory.add(new Item("1", "1", Moveable.MOBILE));
        boolean result = inventory.getItems().size() == 1;
        Assert.assertTrue(result);
    }

    @Test
    @DisplayName("Проверка метода add null")
    public void testAddItemNull(){
        Inventory inventory = new Inventory();
        inventory.add(null);
        boolean result = inventory.getItems().size() == 0;
        Assert.assertTrue(result);
    }

    @Test
    @DisplayName("Проверка метода remove стандарт")
    public void testRemoveItem() {
        Inventory inventory = new Inventory();
        Item item = new Item("1", "1", Moveable.MOBILE);
        inventory.add(item);
        inventory.remove(item);
        boolean result = inventory.getItems().size() == 0;
        Assert.assertTrue(result);
    }

    @Test
    @DisplayName("Проверка метода remove удаление несуществующего")
    public void testRemoveNonExistingItem() {
        Inventory inventory = new Inventory();
        Item item = new Item("1", "1", Moveable.MOBILE);
        inventory.add(item);
        inventory.remove(new Item("1", "2", Moveable.MOBILE));
        boolean result = inventory.getItems().size() == 1;
        Assert.assertTrue(result);
    }

    @Test
    @DisplayName("Проверка метода remove удаление у пустого инвентаря")
    public void testRemoveItemFromEmpty() {
        Inventory inventory = new Inventory();
        inventory.remove(null);
        boolean result = inventory.getItems().size() == 0;
        Assert.assertTrue(result);
    }

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(output));
    }

    @Test
    @DisplayName("Проверка отображения инвентаря")
    public void testShow() {

        Inventory inventory = new Inventory();
        inventory.add(new Item("1", "1", Moveable.MOBILE));
        inventory.add(new Item("2", "2", Moveable.MOBILE));
        inventory.add(new Item("3", "3", Moveable.MOBILE));
        inventory.show();
        Assert.assertEquals(
                "Item(name=1, description=1, moveable=MOBILE)\n" +
                "Item(name=2, description=2, moveable=MOBILE)\n" +
                "Item(name=3, description=3, moveable=MOBILE)\n",
                output.toString()
        );
    }

    @Test
    @DisplayName("Проверка отображения пустого инвентаря")
    public void testShowEmpty() {

        Inventory inventory = new Inventory();
        inventory.show();
        Assert.assertEquals( "Empty :(\n",
                output.toString()
        );
    }


    @Test
    @DisplayName("проверка find() стандарт")
    public void testFind() {
        Inventory inventory = new Inventory();
        inventory.add(new Item("1", "1", Moveable.MOBILE));
        inventory.add(new Item("2", "2", Moveable.MOBILE));
        inventory.add(new Item("3", "3", Moveable.MOBILE));

        Assert.assertEquals(inventory.find("1"), inventory.getItems().get(0));
    }

    @Test
    @DisplayName("проверка find() не найдено")
    public void testNotFind() {
        Inventory inventory = new Inventory();
        inventory.add(new Item("1", "1", Moveable.MOBILE));
        inventory.add(new Item("2", "2", Moveable.MOBILE));
        inventory.add(new Item("3", "3", Moveable.MOBILE));

        Assert.assertNull(inventory.find("4"));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }
}
