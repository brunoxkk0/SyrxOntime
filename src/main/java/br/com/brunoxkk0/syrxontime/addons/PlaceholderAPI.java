package br.com.brunoxkk0.syrxontime.addons;

import br.com.brunoxkk0.syrxontime.data.Provider;
import br.com.brunoxkk0.syrxontime.utils.TimeAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlaceholderAPI extends PlaceholderExpansion {

    @Override
    public String getIdentifier() {
        return "ontime";
    }

    @Override
    public String getPlugin() {
        return null;
    }

    @Override
    public String getAuthor() {
        return "brunoxkk0";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, String s) {

        if(s.startsWith("formated_1_")){
            s = s.split("formated_1_")[1];

            Player p = Bukkit.getPlayer(s);

            if(p != null){
                return "" + TimeAPI.formatSec(Provider.getPlayerTimeS(p).intValue());
            }

        }

        if(s.startsWith("formated_2_")){
            s = s.split("formated_2_")[1];

            Player p = Bukkit.getPlayer(s);

            if(p != null){
                return "" + TimeAPI.formatSec(Provider.getPlayerTimeS(p).intValue());
            }

        }

        switch (s){
            case "today":{
                return "" + TimeAPI.formatSec(Provider.getPlayerToday(player).intValue());
            }

            case "today2":{
                return "" + TimeAPI.formatSec2(Provider.getPlayerToday(player).intValue());
            }

            case "days":{
                return "" + Provider.getPlayerTimeD(player);
            }

            case "hours":{
                return "" + Provider.getPlayerTimeH(player);
            }

            case "minutes":{
                return "" + Provider.getPlayerTimeM(player);
            }

            case "seconds":{
                return "" + Provider.getPlayerTimeS(player);
            }

            case "server":{
                return Provider.getServerTime();
            }

            case "formated_1":{
                return TimeAPI.formatSec(Provider.getPlayerTimeS(player).intValue());
            }

            case "formated_2":{
                return TimeAPI.formatSec2(Provider.getPlayerTimeS(player).intValue());
            }

            case "joined":{
                return Provider.getPlayerJoinedTime(player);
            }
        }

        return "";
    }
}
