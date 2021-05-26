package com.sber;

import com.sber.initialization.GameInitializer;
import org.junit.Assert;
import org.junit.Test;

public class GameInitializerTest {

    @Test
    public void testRandomItem() throws CloneNotSupportedException {
        GameInitializer gameInitializer = new GameInitializer(10);
        Item item = gameInitializer.getRandomItem(gameInitializer.getItems());
        Assert.assertNotNull(item);
    }

    @Test
    public void testMapGenerator() {
        GameInitializer gameInitializer = new GameInitializer(10);
        Assert.assertNotNull(gameInitializer);
    }
}
