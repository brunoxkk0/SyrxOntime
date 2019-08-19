package br.com.brunoxkk0.syrxontime;

import br.com.brunoxkk0.syrxontime.addons.PlaceholderAPI;
import br.com.brunoxkk0.syrxontime.data.store.Cache;
import br.com.brunoxkk0.syrxontime.manager.ConfigManager;
import br.com.brunoxkk0.syrxontime.manager.EventListener;
import br.com.brunoxkk0.syrxontime.rewards.RewardManager;
import br.com.brunoxkk0.syrxontime.threads.TopTask;
import br.com.brunoxkk0.syrxontime.threads.UpdateTask;
import br.com.brunoxkk0.syrxontime.utils.Clock;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class SyrxOntime extends JavaPlugin {
    //--
    private static SyrxOntime instance;
    private static Economy econ = null;

    public static SyrxOntime getInstance() {
        return instance;
    }

    public static Logger logger(){return Logger.getLogger("SyrxOntime"); }

    public static boolean debug = false;

    public static void debug(String s) {
        logger().info("[DEBUG] " + s);
    }

    @Override
    public void onEnable(){
        instance = this;

        Clock.setup();
        ConfigManager.setup();
        EventListener.setup();
        RewardManager.setup();

        TopTask.initialize();
        UpdateTask.initialize();

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            logger().info("PlaceholderAPI encontrado, adicionando suporte.");
            new PlaceholderAPI().register();
        }

        if(setupEconomy()){
            logger().info("Vault econtrando, adicionando suporte.");
        }

    }

    @Override
    public void onDisable(){
        UpdateTask.shutdown();
        ConfigManager.saveCache();
        ConfigManager.saveRewardCache();
        Cache.wipe();
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) return false;

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);

        if (rsp == null) return false;

        return (econ = rsp.getProvider()) != null;
    }

    public static Economy getEconomy() {
        return econ;
    }


}
