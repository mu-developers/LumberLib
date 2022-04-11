package com.mugames.lumberlib;

import com.mugames.lumberlib.colors.Color;
import com.mugames.lumberlib.version.Version;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class LumberLib extends JavaPlugin {

    private static Version version;

    public static Version getVersion() {
        return version;
    }

    @Override
    public void onEnable() {

        // set version;
        String versionString = getServer().getClass().getPackage().getName();
        version = Version.getVersion(versionString);

        Bukkit.getLogger().info(Color.colored("Your server is running in version " + version.getVersion()));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
