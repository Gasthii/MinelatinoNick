package me.gasthiiml.nicks.commands;

import me.gasthiiml.nicks.Main;
import me.neznamy.tab.api.TabPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RealCMD implements CommandExecutor  {
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player))
            return false;

        Player player = (Player) sender;

        if(!player.hasPermission("minelatino.nick.realname") && !player.isOp())
            return false;

        if(args.length == 0) {
            player.sendMessage(ChatColor.translateAlternateColorCodes(
                    '&', "&cDebes especificar el nombre.")
            );
            return false;
        }

        String name = args[0];
        String real = Main.getInstance().getManager().getRealName(name);

        if(real == null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes(
                    '&', "&cNo se encontro el usuario con ese nick.")
            );

            return false;
        }

        player.sendMessage(ChatColor.translateAlternateColorCodes(
            '&', "&bEl nombre real de &f" + name + " &bes &f" + real
        ));

        return false;
    }
}
