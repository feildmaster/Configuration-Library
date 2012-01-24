package lib.feildmaster.configuration;

public abstract class PluginWrapper extends org.bukkit.plugin.java.JavaPlugin {
    private EnhancedConfiguration config;

    public EnhancedConfiguration getConfig() {
        if(config == null) {
            reloadConfig();
        }
        return config;
    }

    public void reloadConfig() {
        if(config == null) {
            config = new EnhancedConfiguration(this);
        }
        config.load();
    }

    public void saveConfig() {
        config.save();
    }

    public void saveDefaultConfig() {
        config.saveDefaults();
    }
}
