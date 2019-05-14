package br.com.brunoxkk0.syrxontime.commands;

import br.com.brunoxkk0.syrxontime.data.Provider;
import br.com.brunoxkk0.syrxontime.utils.TimeAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.stream.Stream;

public class Ontime implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){
            if(args.length >= 1 && args[0] != null){
                Player player = Bukkit.getPlayer(args[0]);

                if(player == null){
                    //.replace("**time", TimeAPI.formatSec(Provider.getPlayerTimeS(player).intValue()).replace("**today", TimeAPI.formatSec(Provider.getPlayerToday(player).intValue())).replace("**join", Provider.getPlayerJoinedTime(player)).replace("**lastlogin", "" +Provider.getPlayer(player).getLastSession()))
                    Stream.of(Lang.consoleformat.replace("**player", args[0]).split("||")).forEach(sender::sendMessage);
                    return true;
                }else{
                    sender.sendMessage(Lang.playernull);
                    return false;
                }
            }
        }

        return false;
    }

}
