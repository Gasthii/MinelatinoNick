package me.gasthiiml.nicks.commands;

import me.gasthiiml.nicks.Main;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabFeature;
import me.neznamy.tab.api.TabPlayer;
import me.neznamy.tab.api.team.UnlimitedNametagManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NickCMD implements CommandExecutor {

    private final TabAPI api = TabAPI.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player))
            return false;

        Player player = (Player) sender;
        TabPlayer tabPlayer = api.getPlayer(player.getUniqueId());

        if(!player.hasPermission("minelatino.nick.use") && !player.isOp())
            return false;

        if(args.length == 0) {
            player.sendMessage(ChatColor.translateAlternateColorCodes(
                    '&', "&cDebes especificar el nombre.")
            );
            return false;
        }

        String name = Main.color(args[0]);

        if(api.getTablistFormatManager() != null)
            api.getTablistFormatManager().setName(tabPlayer, name);

        if(api.getTeamManager() instanceof UnlimitedNametagManager) {
            UnlimitedNametagManager manager = (UnlimitedNametagManager) api.getTeamManager();

            manager.setName(tabPlayer, name);
        }

        player.setDisplayName(name);
        player.setCustomName(name);

        Main.getInstance().getManager().addPlayer(player, ChatColor.stripColor(name));
        player.sendMessage(ChatColor.translateAlternateColorCodes(
                '&', "&bTu nuevo nombre es &f" + name
        ));

        return false;
    }

}
