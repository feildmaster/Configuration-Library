package com.feildmaster.lib.configuration;

import org.junit.*;
import java.io.File;
import java.util.*;

public class EnhancedTest {
    private EnhancedConfiguration getConfig() {
        return new EnhancedConfiguration(new File("config.yml"), null);
    }

    @Test
    public void testSave() {
        EnhancedConfiguration config = getConfig(); // get config
        config.save(); // Save to file
        Assert.assertTrue(config.fileExists()); // Check it exists
        config.getFile().delete(); // Delete file
    }

    @Test
    public void testLists() {
        EnhancedConfiguration config = getConfig(); // Get config
        List<Object> list = Arrays.asList((Object) "One", "Two", "Three", 4, "5", 6.0, true, "false"); // List to put into config
        config.set("list", list); // Set "list" path
        config.save(); // Save to file
        config = getConfig(); // Get new config
        Assert.assertEquals(list, config.getList("list")); // Is the list the same?
        Assert.assertEquals(list, config.get("list")); // This time, lets not convert to list.
        config.getFile().delete(); // Delete file
        Assert.assertFalse(config.fileExists()); // Make sure it's deleted
    }

    @Test
    public void testNullList() {
        EnhancedConfiguration config = getConfig(); // Get config
        Assert.assertEquals(new ArrayList(), config.getList("list")); // Should be an empty list
    }
}
