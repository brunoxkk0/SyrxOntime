package br.com.brunoxkk0.syrxontime.data.store;

import br.com.brunoxkk0.syrxontime.data.PlayerTime;
import br.com.brunoxkk0.syrxontime.data.Provider;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_7_R4.CraftOfflinePlayer;
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
            PlayerTime playerTime = new PlayerTime(Provider.getOfflinePlayer(player));
            playerTime.update();
            update(playerTime);
        }
    }

    public static void resetLastUpdate(Player player){
        if(get(player) != null){
            PlayerTime playerTime = get(player);
            if(playerTime != null){
                playerTime.resetLastUpdate();
                update(playerTime);
            }
        }else{
            PlayerTime playerTime = new PlayerTime(Provider.getOfflinePlayer(player));
            playerTime.resetLastUpdate();
            update(playerTime);
        }
    }

    private static boolean exist(OfflinePlayer player){
        //return player != null && playerTimes.containsKey(player);
        return playerTimes.containsKey(player);
    }

    public static boolean exist(Player player){
        return exist(Provider.getOfflinePlayer(player));
    }

    public static boolean exist(CraftOfflinePlayer player){
        return playerTimes.containsKey(player);
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
            return playerTimes.get(Provider.getOfflinePlayer(player));
        }
        return null;
    }

    public static void wipe(){
        playerTimes.clear();
    }

    public static Collection<OfflinePlayer> keys(){
        return playerTimes.keySet();
    }
}
