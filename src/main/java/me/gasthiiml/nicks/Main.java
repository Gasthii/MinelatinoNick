package me.gasthiiml.nicks;

import lombok.Getter;
import me.gasthiiml.nicks.commands.NickCMD;
import me.gasthiiml.nicks.commands.RealCMD;
import me.gasthiiml.nicks.commands.ResetCMD;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Main extends JavaPlugin {

    @Getter
    private static Main instance;
    private NickManager manager;

    @Override
    public void onEnable() {
        instance = this;

        manager = new NickManager();

        getCommand("nombre").setExecutor(new NickCMD());
        getCommand("realname").setExecutor(new RealCMD());
        getCommand("nickreset").setExecutor(new ResetCMD());
    }

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
