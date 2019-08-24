package br.com.brunoxkk0.syrxontime;

import br.com.brunoxkk0.syrxontime.addons.PlaceholderAPI;
import br.com.brunoxkk0.syrxontime.commands.Lang;
import br.com.brunoxkk0.syrxontime.data.store.Cache;
import br.com.brunoxkk0.syrxontime.manager.ConfigManager;
import br.com.brunoxkk0.syrxontime.manager.EventListener;
import br.com.brunoxkk0.syrxontime.rewards.RewardManager;
import br.com.brunoxkk0.syrxontime.threads.TopTask;
import br.com.brunoxkk0.syrxontime.threads.UpdateTask;
import br.com.brunoxkk0.syrxontime.utils.Clock;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class SyrxOntime extends JavaPlugin {

    private static SyrxOntime instance;
    private static Economy econ = null;
    private static Lang lang;

    public static SyrxOntime getInstance() {
        return instance;
    }

    public static Logger logger(){return Logger.getLogger("SyrxOntime"); }

    public static boolean debug = false;

    public static void debug(String s) {
        logger().info("[DEBUG] " + s);
    }

    public static Lang getLang() {
        return lang;
    }

    @Override
    public void onEnable(){
        instance = this;
        lang = new Lang();

        /*
        Registrando os Modulos do sistema.
         */
        Clock.setup(); //Sistema de controle do tempo.
        ConfigManager.setup(); //Sistema de controle das configurações.
        EventListener.setup(); //Sistema de Eventos.
        RewardManager.setup(); //Sistema de Recompensas.


        /*
        Inicializando os threads.
         */
        TopTask.initialize(); //Task de ontime top.
        UpdateTask.initialize(); //Task para atualizar os tempos.

        /*
        Iniciando a parte de compatibilidade.
         */
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){    // Hook com PlaceHolder API.
            logger().info("PlaceholderAPI encontrado, adicionando suporte.");  //
            new PlaceholderAPI().register();                                         //
        }                                                                            //

        if(setupEconomy()){                                                 //
            logger().info("Vault econtrando, adicionando suporte.");  // Hook com o vault.
        }                                                                   //

    }

    @Override
    public void onDisable(){

        UpdateTask.shutdown(); //Desliga o sistema de atualização do tempo.

        ConfigManager.saveCache(); //Salva os dados de jogadores em "Cache".
        ConfigManager.saveRewardCache(); //Salva os dados referente as recompensas em "Cache".

        Cache.wipe(); //Limpa o "Cache".

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
