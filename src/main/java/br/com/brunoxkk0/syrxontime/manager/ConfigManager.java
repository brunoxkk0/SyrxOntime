package br.com.brunoxkk0.syrxontime.manager;

import br.com.brunoxkk0.syrxontime.SyrxOntime;
import br.com.brunoxkk0.syrxontime.data.PlayerTime;
import br.com.brunoxkk0.syrxontime.data.store.Cache;
import br.com.brunoxkk0.syrxontime.rewards.RewardManager;
import br.com.brunoxkk0.syrxontime.utils.Clock;
import br.com.brunoxkk0.syrxontime.utils.ConfigAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class ConfigManager {

    private static ConfigAPI config, reward, cache;

    public static void setup(){

        config = new ConfigAPI(SyrxOntime.getInstance(), "config.yml");
        reward = new ConfigAPI(SyrxOntime.getInstance(), "reward.yml");
        cache = new ConfigAPI(SyrxOntime.getInstance(), "cache.yml");

        loadCache();

    }

    private static void loadCache() {

        SyrxOntime.logger().info("Carregandos dados...");

        if(cache.getInt("day", 0) < Clock.getCurrentDay()) {
            SyrxOntime.logger().info("Dados antigos encontrados... limpando!");

            cache.setValue("day", Clock.getCurrentDay());
            cache.setValue("data",null);
            cache.save();

            SyrxOntime.logger().info("Limpo!");

        }

        for (String key : cache.getKeys("data")) {

            OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(key));

            if(player != null){
                Cache.add(new PlayerTime(player, cache.getLong("data." + key + ".time", 0)));
                RewardManager.rewards_cache.put(player, cache.getInt("data" + key + ".reward", -1));
            }
        }

        if(Cache.size() > 0){
            SyrxOntime.logger().info("Encontrado e carregado dados de " + Cache.size() + " jogadores.");
        }else {
            SyrxOntime.logger().info("Nenhum jogador consta nos registros.");
        }
    }

    public static void saveCache(){

        if(SyrxOntime.debug) SyrxOntime.logger().info("Salvando dados...");

        cache.setValue("day", Clock.getCurrentDay());

        for(PlayerTime playerTime : Cache.datas()){

            if(playerTime.isSaved){
                continue;
            }

            cache.setValue("data."+playerTime.getPlayer().getUniqueId()+".time", playerTime.getTotalTimeToday());
            cache.setValue("data."+playerTime.getPlayer().getUniqueId()+".reward", RewardManager.rewards_cache.getOrDefault(playerTime.player, -1));

            playerTime.isSaved = true;
            Cache.update(playerTime);
        }

        cache.save();

        if(SyrxOntime.debug) SyrxOntime.logger().info("Sucesso.");
    }

    public static void wipeCache(){

        SyrxOntime.logger().info("Apagando dados...");

        cache.setValue("day", Clock.getCurrentDay());
        cache.setValue("data",null);
        cache.save();

        SyrxOntime.logger().info("Sucesso.");
    }

    public static ConfigAPI getRewards(){
        return reward;
    }

    public static ConfigAPI getConfig(){
        return reward;
    }

}
