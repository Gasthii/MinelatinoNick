package me.gasthiiml.nicks;

import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import me.neznamy.tab.api.team.UnlimitedNametagManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NickManager {

    private final Map<UUID, String> nicks = new HashMap<>();
    private final TabAPI api = TabAPI.getInstance();

    public NickManager() {}

    public void injectPlayer(Player player) {
        if(!nicks.containsKey(player.getUniqueId()))
            return;

        TabPlayer tabPlayer = api.getPlayer(player.getUniqueId());
        String nick = nicks.get(player.getUniqueId());

        if(api.getTablistFormatManager() != null)
            api.getTablistFormatManager().setName(tabPlayer, nick);

        if(api.getTeamManager() instanceof UnlimitedNametagManager) {
            UnlimitedNametagManager manager = (UnlimitedNametagManager) api.getTeamManager();

            manager.setName(tabPlayer, nick);
        }

        player.setDisplayName(nick);
        player.setCustomName(nick);
    }

    public void addPlayer(Player player, String nick) {
        nicks.put(player.getUniqueId(), nick);
    }

    public void removePlayer(Player player) {
        nicks.remove(player.getUniqueId());
    }

    public String getRealName(String nick) {
        for (Map.Entry<UUID, String> entry : nicks.entrySet()) {
            if(ChatColor.stripColor(entry.getValue()).equalsIgnoreCase(nick) && Bukkit.getPlayer(entry.getKey()) != null)
                return Bukkit.getPlayer(entry.getKey()).getName();
        }

        return null;
    }


}
