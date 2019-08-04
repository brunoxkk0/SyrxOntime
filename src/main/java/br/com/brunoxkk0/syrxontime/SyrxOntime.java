package br.com.brunoxkk0.syrxontime;

import br.com.brunoxkk0.syrxontime.addons.PlaceholderAPI;
import br.com.brunoxkk0.syrxontime.data.store.Cache;
import br.com.brunoxkk0.syrxontime.manager.ConfigManager;
import br.com.brunoxkk0.syrxontime.manager.EventListener;
import br.com.brunoxkk0.syrxontime.rewards.RewardManager;
import br.com.brunoxkk0.syrxontime.threads.TopTask;
import br.com.brunoxkk0.syrxontime.threads.UpdateTask;
import br.com.brunoxkk0.syrxontime.utils.Clock;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class SyrxOntime extends JavaPlugin {

    public static SyrxOntime instance;

    public static SyrxOntime getInstance() {
        return instance;
    }

    public static Logger logger(){return Logger.getLogger("SyrxOntime"); }

    public static boolean debug = false;

    @Override
    public void onEnable(){
        instance = this;

        Clock.setup();
        ConfigManager.setup();
        EventListener.setup();
        //RewardManager.setup();

        TopTask.initialize();
        UpdateTask.initialize();

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            logger().info("PlaceholderAPI encontrado, adicionando suporte.");
            new PlaceholderAPI().register();
        }

    }

    @Override
    public void onDisable(){
        UpdateTask.shutdown();
        ConfigManager.saveCache();
        Cache.wipe();
    }

}
