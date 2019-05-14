package br.com.brunoxkk0.syrxontime;

import br.com.brunoxkk0.syrxontime.addons.PlaceholderAPI;
import br.com.brunoxkk0.syrxontime.commands.Ontime;
import br.com.brunoxkk0.syrxontime.data.store.Cache;
import br.com.brunoxkk0.syrxontime.manager.AfkManager;
import br.com.brunoxkk0.syrxontime.manager.ConfigManager;
import br.com.brunoxkk0.syrxontime.manager.EventListener;
import br.com.brunoxkk0.syrxontime.threads.UpdateTask;
import br.com.brunoxkk0.syrxontime.utils.Clock;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.beans.EventHandler;

public class SyrxOntime extends JavaPlugin {

    public static SyrxOntime instance;
    public static ConfigManager configManager;

    public static SyrxOntime getInstance() {
        return instance;
    }

    public static void info(String msg){
        instance.getLogger().info("[Info] " + msg);
    }

    public static void debug(String msg){
        instance.getLogger().info("[Debug] " + msg);
    }

    public static boolean debug = false;

    @Override
    public void onEnable(){
        instance = this;
        new Clock();
        configManager = new ConfigManager();
        configManager.loadCache();
        new EventListener();
        //new AfkManager();
        UpdateTask.initialize();
        //getCommand("ontime").setExecutor(new Ontime());

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            info("PlaceholderAPI encontrado, adicionando suporte.");
            new PlaceholderAPI().register();
        }

    }

    @Override
    public void onDisable(){
        UpdateTask.shutdown();
        configManager.save();
        configManager.saveCache();
        Cache.cache.clear();
    }

}
