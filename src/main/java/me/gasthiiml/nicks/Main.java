package me.gasthiiml.nicks;

import lombok.Getter;
import me.gasthiiml.nicks.commands.NickCMD;
import me.gasthiiml.nicks.commands.RealCMD;
import me.gasthiiml.nicks.commands.ResetCMD;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
public class Main extends JavaPlugin implements Listener {

    @Getter
    private static Main instance;
    private NickManager manager;

    @Override
    public void onEnable() {
        instance = this;

        manager = new NickManager();

        getServer().getPluginManager().registerEvents(this, this);

        getCommand("nombre").setExecutor(new NickCMD());
        getCommand("realname").setExecutor(new RealCMD());
        getCommand("nickreset").setExecutor(new ResetCMD());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if(event.getPlayer() != null && event.getPlayer().isOnline()) {
                    manager.injectPlayer(event.getPlayer());
                }
            }
        }.runTaskLater(this, 40L);
    }

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
