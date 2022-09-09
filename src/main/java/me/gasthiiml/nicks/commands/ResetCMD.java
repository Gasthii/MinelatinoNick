package me.gasthiiml.nicks.commands;

import me.gasthiiml.nicks.Main;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import me.neznamy.tab.api.team.UnlimitedNametagManager;
import org.bukkit.Bukkit;
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

        if(!player.hasPermission("minelatino.nick.reset") && !player.isOp())
            return false;

        if(args.length == 1 && player.hasPermission("minelatino.nick.others")) {
            Player query = Bukkit.getPlayer(args[0]);

            if(query != null && query.isOnline()) {
                resetNick(query);
                player.sendMessage(Main.color("&bEl nick de &f" + args[0] + " &bfue reestablecido correctamente."));
                return false;
            }

            player.sendMessage(Main.color("&cNo se encontro el jugador de nombre &f" + args[0]));
            return false;
        }

        resetNick(player);

        return false;
    }

    public void resetNick(Player player) {
        TabPlayer tabPlayer = api.getPlayer(player.getUniqueId());

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
    }

}

