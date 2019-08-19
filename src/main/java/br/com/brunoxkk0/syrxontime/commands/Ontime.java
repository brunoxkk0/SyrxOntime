package br.com.brunoxkk0.syrxontime.commands;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Ontime implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        boolean isConsol = false;

        if(!(sender instanceof Player)){
            isConsol = true;
        }



        return false;
    }
}
