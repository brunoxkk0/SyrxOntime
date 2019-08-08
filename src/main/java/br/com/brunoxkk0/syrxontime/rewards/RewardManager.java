package br.com.brunoxkk0.syrxontime.rewards;


import br.com.brunoxkk0.syrxontime.SyrxOntime;
import br.com.brunoxkk0.syrxontime.data.Provider;
import br.com.brunoxkk0.syrxontime.manager.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.omg.CORBA.TIMEOUT;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    private static List<Reward> getAll(TimeUnit unit){

        List<Reward> list = new ArrayList<>();

        for(Reward reward : rewards){
            if(reward.timeUnit().equals(unit)){
                list.add(reward);
            }
        }

        return list;
    }

    public static boolean process(OfflinePlayer player, long time){
        if(rewards_cache.containsKey(player)){
            int id = rewards_cache.getOrDefault(player, -1);
            for(Reward reward : rewards){
                if(id < reward.Reward_UUID()){
                    if(time > Provider.convert(reward.time(), reward.timeUnit())){
                        rewards_cache.replace(player, reward.Reward_UUID());
                        execute(player,reward);
                        return true;
                    }
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
            //TODO: adicionar vault
        }

        if(reward.xp() > 0){
            if(Bukkit.getPlayer(player.getUniqueId()) != null){
                Bukkit.getPlayer(player.getUniqueId()).giveExp(((Double)reward.xp()).intValue());
            }
        }

    }

    public static void setup(){

        SyrxOntime.logger().info("Registrando rewards...");

        for(String keys : ConfigManager.getRewards().getKeys("rewards")){
            IReward reward = new IReward() {
                @Override
                public TimeUnit timeUnit() {
                    return TimeUnit.valueOf(ConfigManager.getRewards().getString("rewards."+keys+".timeUnit"));
                }

                @Override
                public int time() {
                    return ConfigManager.getRewards().getInt("rewards."+keys+".time");
                }

                @Override
                public String[] commands() {
                    return ConfigManager.getRewards().getStringList("rewards." + keys + ".commands").toArray(new String[0]);
                }

                @Override
                public double money() {
                    return ConfigManager.getRewards().getInt("rewards."+keys+".money", 0);
                }

                @Override
                public double xp() {
                    return ConfigManager.getRewards().getInt("rewards."+keys+".xp", 0);
                }
            };

            register(reward);
        }

        SyrxOntime.logger().info("Registrado " + rewards.size() + " rewards.");

        for(Reward reward : rewards){
            SyrxOntime.logger().info("REWARDID: " +reward.Reward_UUID()+ " TIMEUNIT: " + reward.timeUnit()+" TIME: " + reward.time());
        }
    }

    public static long timeToNextReward(OfflinePlayer player, long currentTime){
        long t = 0;

        if(rewards_cache.containsKey(player)){
            for(Reward reward : rewards){
               if(reward.Reward_UUID() > rewards_cache.getOrDefault(player, -1)){
                   t = Provider.convert(reward.time(), reward.timeUnit());
                   break;
               }
            }
        }
        return (t - currentTime) / 1000;
    }
}
