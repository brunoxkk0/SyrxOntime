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
        }else{
            PlayerTime playerTime = new PlayerTime(Bukkit.getOfflinePlayer(player.getUniqueId()));
            playerTime.update();
            update(playerTime);
        }
    }

    private static boolean exist(OfflinePlayer player){
        //return player != null && playerTimes.containsKey(player);
        return playerTimes.containsKey(player);
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

    public static Collection<PlayerTime> datas(){
        return playerTimes.values();
    }


    public static PlayerTime get(Player player){
        if(exist(player)){
            return playerTimes.get(Bukkit.getOfflinePlayer(player.getUniqueId()));
        }
        return null;
    }

    public static void wipe(){
        playerTimes.clear();
    }
}
