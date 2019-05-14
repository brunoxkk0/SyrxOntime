package br.com.brunoxkk0.syrxontime.manager;

import br.com.brunoxkk0.syrxontime.SyrxOntime;
import br.com.brunoxkk0.syrxontime.data.TimeObj;
import br.com.brunoxkk0.syrxontime.data.store.Cache;
import br.com.brunoxkk0.syrxontime.utils.Clock;
import br.com.brunoxkk0.syrxontime.utils.ConfigAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class ConfigManager {

    public ConfigAPI config;
    public ConfigAPI reward;
    public static ConfigAPI cache;

    private boolean isLoad = false;

    public ConfigManager(){

        if(!isLoad){
            config= new ConfigAPI(SyrxOntime.getProvidingPlugin(SyrxOntime.class), "config.yml", true, false);
            reward= new ConfigAPI(SyrxOntime.getProvidingPlugin(SyrxOntime.class), "reward.yml", true, false);
            load();
        }

    }

    public void load(){
        isLoad = true;
    }

    public void save(){
        reward.save();
        config.save();
    }

    public static void saveCache(){

        if(cache == null){
            cache = new ConfigAPI(SyrxOntime.getProvidingPlugin(SyrxOntime.class), "cache.yml");
        }

        cache.setValue("currentDay", Clock.getCurrentDay());

        for(TimeObj timeObj : Cache.cache.values()){

            if(timeObj.isSaved){
               return;
            }

            UUID uuid = timeObj.getPlayer().getUniqueId();
            Long time = timeObj.getTotalTimeToday();

            cache.setValue("TimeObj." + uuid + ".timeToday", time);

            timeObj.isSaved = true;
            Cache.cache.replace(timeObj.player, timeObj);
        }

        cache.save();
    }

    public void loadCache() {
        SyrxOntime.info("Carregando CACHE...");
        cache = new ConfigAPI(SyrxOntime.getProvidingPlugin(SyrxOntime.class), "cache.yml");

        int cacheDay = cache.getInt("currentDay", 0);

        if (Clock.getCurrentDay() > cacheDay || Clock.getCurrentDay() != cacheDay) {

            if(cacheDay == 0){
                SyrxOntime.info("Cache em branco, iniciando...");
                cache.setValue("currentDay", Clock.getCurrentDay());
                cache.save();
            }else{
                SyrxOntime.info("Dados antigos encontrados... LIMPANDO.");
                cache.setValue("currentDay", Clock.getCurrentDay());

                //Limpa os dados antigos
                cache.setValue("TimeObj", null);

                cache.save();
            }
        }

        SyrxOntime.info("Carregando dados dos jogadores...");
        for (String key : cache.getKeys("TimeObj")) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(key));
            Long timeToday = cache.getLong("TimeObj." + key + ".timeToday", 0);

            TimeObj timeObj = new TimeObj(player, timeToday);
            Cache.cache.put(player, timeObj);
        }
        SyrxOntime.info("Carregado " + Cache.cache.size() + " dados.");
        SyrxOntime.info("Sucesso!");
    }
}
