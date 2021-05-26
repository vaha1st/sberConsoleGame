package com.sber;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class PlayerTest {

    private ByteArrayOutputStream output = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(output));
    }

    @Test
    @DisplayName("проверка осмотреться стандарт")
    public void testLookAround() {
        Player player = new Player(new Location(), new ArrayList<>());
        player.getLocation().getDirections().put(Direction.NORTH, new Location("floor", "floor"));
        player.getLocation().getDirections().put(Direction.UP, new Location("floor", "floor"));
        player.getLocation().getInventory().add(new Item("item1", "1", Moveable.MOBILE));
        player.lookAround();
        Assert.assertEquals("Items on location:\n" +
                "Item(name=item1, description=1, moveable=MOBILE)\n" +
                "Available directions:\nUP\nNORTH\n",
                output.toString());
    }



    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }
}
