package me.gasthiiml.nicks.commands;

import me.gasthiiml.nicks.Main;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import me.neznamy.tab.api.team.UnlimitedNametagManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ResetCMD implements CommandExecutor {

    private final TabAPI api = TabAPI.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player))
            return false;

        Player player = (Player) sender;
        TabPlayer tabPlayer = api.getPlayer(player.getUniqueId());

        if(!player.hasPermission("minelatino.nick.reset") && !player.isOp())
            return false;

        if(api.getTablistFormatManager() != null)
            api.getTablistFormatManager().resetName(tabPlayer);

        if(api.getTeamManager() instanceof UnlimitedNametagManager) {
            UnlimitedNametagManager manager = (UnlimitedNametagManager) api.getTeamManager();

            manager.resetName(tabPlayer);
        }

        player.setDisplayName(player.getName());
        player.setCustomName(player.getName());
        player.setPlayerListName(player.getName());

        Main.getInstance().getManager().removePlayer(player);

        player.sendMessage(ChatColor.translateAlternateColorCodes(
                '&', "&bTu nombre fue reestablecido"
        ));

        return false;
    }

}

