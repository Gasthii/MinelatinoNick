package me.gasthiiml.nicks.commands;

import me.gasthiiml.nicks.Main;
import me.gasthiiml.nicks.utils.ChatColorUtils;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import me.neznamy.tab.api.team.UnlimitedNametagManager;
import org.bukkit.Bukkit;
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

        if(!player.hasPermission("minelatino.nick.use") && !player.isOp())
            return false;

        if(args.length == 0) {
            player.sendMessage(ChatColor.translateAlternateColorCodes(
                    '&', "&cDebes especificar el nombre.")
            );
            return false;
        }

        String name = Main.color(args[0]);

        if(!player.hasPermission("minelatino.nick.color"))
            name = ChatColorUtils.stripColors(name);

        if(!player.hasPermission("minelatino.nick.formats"))
            name = ChatColorUtils.stripFormats(name);

        if(name.length() > 16) {
            player.sendMessage(Main.color("&cEl nombre que escogiste es demasiado largo."));
            return false;
        }

        Player checker = Bukkit.getPlayer(ChatColor.stripColor(name));

        if(checker != null && !(checker.getUniqueId().equals(player.getUniqueId()))) {
            player.sendMessage(Main.color("&cEl nombre que escogiste pertenece a un jugador que esta conectado."));
            return false;
        }

        if(args.length == 2 && player.hasPermission("minelatino.nick.others")) {
            Player query = Bukkit.getPlayer(args[1]);

            if(query != null && query.isOnline()) {
                nickPlayer(query, name);
                player.sendMessage(Main.color("&bEl nuevo nombre de &f" + args[1] + " &bes " + name));
                return false;
            }

            player.sendMessage(Main.color("&cNo se encontro el jugador de nombre &f" + args[1]));
            return false;
        }

        nickPlayer(player, name);

        return false;
    }

    public void nickPlayer(Player player, String name) {
        TabPlayer tabPlayer = api.getPlayer(player.getUniqueId());

        if(api.getTablistFormatManager() != null)
            api.getTablistFormatManager().setName(tabPlayer, name);

        if(api.getTeamManager() instanceof UnlimitedNametagManager) {
            UnlimitedNametagManager manager = (UnlimitedNametagManager) api.getTeamManager();

            manager.setName(tabPlayer, name);
        }

        player.setDisplayName(name);
        player.setCustomName(name);

        Main.getInstance().getManager().addPlayer(player, name);
        player.sendMessage(ChatColor.translateAlternateColorCodes(
                '&', "&bTu nuevo nombre es &f" + name
        ));
    }

}
