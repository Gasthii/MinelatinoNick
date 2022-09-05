package me.gasthiiml.nicks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NickManager {

    private final Map<UUID, String> nicks = new HashMap<>();

    public NickManager() {}

    public void addPlayer(Player player, String nick) {
        nicks.put(player.getUniqueId(), nick);
    }

    public void removePlayer(Player player) {
        nicks.remove(player.getUniqueId());
    }

    public String getRealName(String nick) {
        for (Map.Entry<UUID, String> entry : nicks.entrySet()) {
            if(entry.getValue().equalsIgnoreCase(nick) && Bukkit.getPlayer(entry.getKey()) != null)
                return Bukkit.getPlayer(entry.getKey()).getName();
        }

        return null;
    }

}
