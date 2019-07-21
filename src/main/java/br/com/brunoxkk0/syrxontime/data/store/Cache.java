package br.com.brunoxkk0.syrxontime.data.store;

import br.com.brunoxkk0.syrxontime.data.PlayerTime;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;

public class Cache {

    private volatile static HashMap<OfflinePlayer, PlayerTime> playerTimes = new HashMap<>();

    public static void add(PlayerTime playerTime){
        update(playerTime);
    }

    public static void update(PlayerTime playerTime){
        playerTimes.put(playerTime.player,playerTime);
    }

    public static void update(Player player){
        if(get(player) != null){
            PlayerTime playerTime = get(player);
            if(playerTime != null){
                playerTime.update();
                update(playerTime);
            }
        }
    }

    public static boolean exist(OfflinePlayer player){
        if(player != null && playerTimes.containsKey(player.getPlayer())){
            return true;
        }

        return  false;
    }

    public static boolean exist(Player player){
        return exist(Bukkit.getOfflinePlayer(player.getUniqueId()));
    }

    public static void create(Player player){
        add(new PlayerTime(player));
    }

    public static int size(){
        return playerTimes.size();
    }

    public static Collection<PlayerTime> datas(){ return playerTimes.values();}


    public static PlayerTime get(Player player){
        if(exist(Bukkit.getOfflinePlayer(player.getUniqueId()))){
            return playerTimes.get(Bukkit.getOfflinePlayer(player.getUniqueId()));
        }
        return null;
    }

    public static void wipe(){
        playerTimes.clear();
    }
}
