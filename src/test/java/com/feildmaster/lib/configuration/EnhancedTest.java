package com.feildmaster.lib.configuration;

import org.junit.*;
import java.io.File;
import java.util.*;
import org.bukkit.plugin.Plugin;

public class EnhancedTest {
    private final File file = new File("config.yml");
    private Plugin plugin;

    private EnhancedConfiguration getConfig() {
        return new EnhancedConfiguration(file, plugin);
    }

    @Test
    public void testSave() {
        EnhancedConfiguration config = getConfig(); // get config
        config.save(); // Save to file
        Assert.assertTrue(config.fileExists()); // Check it exists
        reset();
    }

    @Test
    public void testLists() {
        EnhancedConfiguration config = getConfig(); // Get config
        config.load();
        List<Object> list = Arrays.asList((Object) "One", "Two", "Three", 4, "5", 6.0, true, "false"); // List to put into config
        config.set("list", list); // Set "list" path
        config.save(); // Save to file
        config = getConfig(); // Get new config
        config.load(); // Load
        Assert.assertEquals(list, config.getList("list")); // Is the list the same?
        Assert.assertEquals(list, config.get("list")); // This time, lets not convert to list.
        reset();
    }

    @Test
    public void testReload() {
        EnhancedConfiguration config = getConfig();
        EnhancedConfiguration carbon = getConfig();
        EnhancedConfiguration copy = getConfig();

        config.load();
        carbon.load();
        copy.load();

        config.set("test", "test1");
        config.save();

        config.get("test"); // Add to the cache

        carbon.set("test", "test2");
        carbon.save();

        config.load(); // This is what reloadConfig() does
        copy.load(); // Make sure copy loads correctly

        Assert.assertEquals(config.get("test"), carbon.get("test"));
        Assert.assertEquals(config.get("test"), copy.get("test"));
        reset();
    }

    @Test
    public void testEmptyList() {
        EnhancedConfiguration config = getConfig();
        Assert.assertEquals(new ArrayList(), config.getList("list")); // Should be an empty list
    }

    @Test
    public void subSectionTest() {
        EnhancedConfiguration config = getConfig();

        config.set("sub.section", false);
        config.set("sub.test.section", true);

        // Test getting section
        EnhancedConfigurationSection section = config.getConfigurationSection("sub");
        Assert.assertTrue(!section.getBoolean("section"));
        // Test getting sub-section
        section = section.getConfigurationSection("test");
        Assert.assertTrue(section.getBoolean("section"));
    }

    @Test
    public void testHeader() {
        reset();
        EnhancedConfiguration config = getConfig();
        config.options().header("Line 1", "Line 2");
        String s = System.getProperty("line.separator");
        String expected = "# Line 1"+s+"# Line 2"+s; // We use the system line separator now
        Assert.assertEquals(config.saveToString(), expected);
    }

    private void reset() {
        file.delete();
        Assert.assertFalse(file.exists()); // Make sure it's deleted
    }
}
