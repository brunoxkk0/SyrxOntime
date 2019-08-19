package br.com.brunoxkk0.syrxontime.rewards;


import br.com.brunoxkk0.syrxontime.SyrxOntime;
import br.com.brunoxkk0.syrxontime.data.Provider;
import br.com.brunoxkk0.syrxontime.manager.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class RewardManager {

    private static int id = 0;
    private static ArrayList<Reward> rewards = new ArrayList<>();
    public volatile static HashMap<OfflinePlayer, Integer> rewards_cache = new HashMap<>();

    private static void register(IReward reward){
        rewards.add(new Reward(reward,lastId()));
    }

    private static int lastId(){
        return id++;
    }

    public static boolean process(OfflinePlayer player, long time){
        int id;

        if(rewards_cache.containsKey(player)){
            id = rewards_cache.getOrDefault(player, -1);
        }else{
             id = -1;
        }

        for(Reward reward : rewards){
            if(id < reward.Reward_UUID()){
                if(time > Provider.convert(reward.time(), reward.timeUnit())){
                    rewards_cache.put(player, reward.Reward_UUID());
                    execute(player,reward);
                    return true;
                }
            }
        }

        return false;
    }

    private static void execute(OfflinePlayer player, Reward reward){
        for(String cmd : reward.commands()){
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("&p", player.getName()));
        }

        if(reward.money() > 0){
            if(SyrxOntime.getEconomy().hasAccount(player)){
                SyrxOntime.getEconomy().depositPlayer(player, reward.money());

                if(Bukkit.getPlayer(player.getUniqueId()) != null){
                    Bukkit.getPlayer(player.getUniqueId()).sendMessage("Dinheiro adicionado.");
                }

            }
        }

        if(reward.xp() > 0){
            if(Bukkit.getPlayer(player.getUniqueId()) != null){
                Bukkit.getPlayer(player.getUniqueId()).giveExp(((Double)reward.xp()).intValue());
            }
        }

    }

    public static void setup(){

        SyrxOntime.logger().info("Registrando rewards...");

        for(String key : ConfigManager.getRewards().getKeys("rewards")){
            try{
                if(!verifyAndRegister(key)){
                    SyrxOntime.logger().warning("Falha ao carregar a recompensa: " + key + " , verifique o arquivo reward.yml e tente novamente.");
                }
            }catch (Exception ignored){
                SyrxOntime.logger().warning("Falha ao carregar a recompensa: " + key + " , verifique o arquivo reward.yml e tente novamente.");
            }
        }

        SyrxOntime.logger().info("Registrado " + rewards.size() + " rewards.");

        for(Reward reward : rewards){
            SyrxOntime.logger().info("{ RewardID: " +reward.Reward_UUID()+ " TimeUnit: " + reward.timeUnit()+" Time: " + reward.time() + " }");
        }
    }

    public static long timeToNextReward(OfflinePlayer player, long currentTime){
        long t = -1;

        if(rewards_cache.containsKey(player)){
            for(Reward reward : rewards){
               if(reward.Reward_UUID() > rewards_cache.get(player)){
                   t = Provider.convert(reward.time(), reward.timeUnit());
                   break;
               }
            }
        }else{
            for(Reward reward : rewards){
                if(reward.Reward_UUID() > -1){
                    t = Provider.convert(reward.time(), reward.timeUnit());
                    break;
                }
            }
        }

        return Math.abs((currentTime - t) / 1000);
    }

    public static Long getNextRewardTime(OfflinePlayer player){
        long t = -1;

        if(rewards_cache.containsKey(player)){
            for(Reward reward : rewards){
                if(reward.Reward_UUID() > rewards_cache.get(player)){
                    t = Provider.convert(reward.time(), reward.timeUnit());
                    break;
                }
            }
        }else{
            for(Reward reward : rewards){
                if(reward.Reward_UUID() > -1){
                    t = Provider.convert(reward.time(), reward.timeUnit());
                    break;
                }
            }
        }

        return Math.abs((t) / 1000);
    }

    private static boolean verifyAndRegister(String key){

        String timeUnit = ConfigManager.getRewards().getString("rewards."+key+".timeUnit","");

        if(!timeUnit.equals("")){
            String[] units = {"SECONDS","MINUTES","HOURS"};

            if(!Arrays.asList(units).contains(timeUnit)){
                return false;
            }

        }

        int time = ConfigManager.getRewards().getInt("rewards."+key+".time", -1);

        if(time <= -1){
            return false;
        }

        String[] cmds = ConfigManager.getRewards().getStringList("rewards." + key + ".commands").toArray(new String[0]);
        double money = ConfigManager.getRewards().getInt("rewards."+key+".money", 0);
        double xp = ConfigManager.getRewards().getInt("rewards."+key+".xp", 0);

        IReward reward = new IReward(){
            @Override
            public TimeUnit timeUnit() {
                return TimeUnit.valueOf(timeUnit);
            }

            @Override
            public int time() {
                return time;
            }

            @Override
            public String[] commands() {
                return cmds;
            }

            @Override
            public double money() {
                return money;
            }

            @Override
            public double xp() {
                return xp;
            }
        };

        register(reward);

        return true;
    }
}
