package br.com.brunoxkk0.syrxontime.rewards;


import br.com.brunoxkk0.syrxontime.SyrxOntime;
import br.com.brunoxkk0.syrxontime.data.Provider;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RewardManager {
    static int id = 0;
    private static ArrayList<Reward> rewards = new ArrayList<>();
    public volatile static HashMap<OfflinePlayer, Integer> rewards_cache = new HashMap<>();

    public static void register(IReward reward){
        rewards.add(new Reward(reward,lastId()));
    }

    static int lastId(){
        return id++;
    }

    private static List<Reward> getAll(TimeUnit unit){

        List<Reward> list = new ArrayList<>();

        for(Reward reward : rewards){
            if(reward.timeUnit() == unit){
                list.add(reward);
            }
        }

        return list;
    }

    public static boolean process(OfflinePlayer player, long time){

        if(true){
            return false;
        }

        System.out.println("process");
        System.out.println(Provider.parse(time) + ":" + Provider.convert(Provider.parse(time), time));

        for(Reward reward : getAll(Provider.parse(time))){
            if(reward.time() <= Provider.convert(Provider.parse(time), time)){
                if(rewards_cache.getOrDefault(player, -1) < reward.Reward_UUID()){
                    rewards_cache.remove(player);
                    rewards_cache.put(player, reward.Reward_UUID());
                    System.out.println((reward.Reward_UUID()));
                    execute(player, reward);
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

        register(new IReward() {
            @Override
            public TimeUnit timeUnit() {
                return TimeUnit.SECONDS;
            }

            @Override
            public int time() {
                return 30;
            }

            @Override
            public String[] commands() {
                return new String[]{"say Reward Entregue"};
            }

            @Override
            public double money() {
                return 0;
            }

            @Override
            public double xp() {
                return 0;
            }

        });
        register(new IReward() {
            @Override
            public TimeUnit timeUnit() {
                return TimeUnit.SECONDS;
            }

            @Override
            public int time() {
                return 40;
            }

            @Override
            public String[] commands() {
                return new String[]{"say Reward Entregue 2"};
            }

            @Override
            public double money() {
                return 0;
            }

            @Override
            public double xp() {
                return 0;
            }
        });

        /*
        register(new IReward() {
            @Override
            public TimeUnit timeUnit() {
                return TimeUnit.MINUTES;
            }

            @Override
            public int time() {
                return 15;
            }

            @Override
            public String[] commands() {
                return new String[]{"points give &p 2"};
            }

            @Override
            public double money() {
                return 10;
            }

            @Override
            public double xp() {
                return 15;
            }

            @Override
            public int Reward_UUID() {
                return lastId();
            }
        });
        register(new IReward() {
            @Override
            public TimeUnit timeUnit() {
                return TimeUnit.MINUTES;
            }

            @Override
            public int time() {
                return 30;
            }

            @Override
            public String[] commands() {
                return new String[]{"points give &p 2"};
            }

            @Override
            public double money() {
                return 15;
            }

            @Override
            public double xp() {
                return 20;
            }

            @Override
            public int Reward_UUID() {
                return lastId();
            }
        });
        register(new IReward() {
            @Override
            public TimeUnit timeUnit() {
                return TimeUnit.HOURS;
            }

            @Override
            public int time() {
                return 1;
            }

            @Override
            public String[] commands() {
                return new String[]{"points give &p 5"};
            }

            @Override
            public double money() {
                return 30;
            }

            @Override
            public double xp() {
                return 25;
            }

            @Override
            public int Reward_UUID() {
                return lastId();
            }
        });
        register(new IReward() {
            @Override
            public TimeUnit timeUnit() {
                return TimeUnit.HOURS;
            }

            @Override
            public int time() {
                return 2;
            }

            @Override
            public String[] commands() {
                return new String[]{"points give &p 5"};
            }

            @Override
            public double money() {
                return 60;
            }

            @Override
            public double xp() {
                return 30;
            }

            @Override
            public int Reward_UUID() {
                return lastId();
            }
        });
        register(new IReward() {
            @Override
            public TimeUnit timeUnit() {
                return TimeUnit.HOURS;
            }

            @Override
            public int time() {
                return 3;
            }

            @Override
            public String[] commands() {
                return new String[]{"crate key &p comum","points give &p 5"};
            }

            @Override
            public double money() {
                return 90;
            }

            @Override
            public double xp() {
                return 35;
            }

            @Override
            public int Reward_UUID() {
                return lastId();
            }
        });
        register(new IReward() {
            @Override
            public TimeUnit timeUnit() {
                return TimeUnit.HOURS;
            }

            @Override
            public int time() {
                return 4;
            }

            @Override
            public String[] commands() {
                return new String[]{"crate key &p comum","points give &p 5"};
            }

            @Override
            public double money() {
                return 100;
            }

            @Override
            public double xp() {
                return 40;
            }

            @Override
            public int Reward_UUID() {
                return lastId();
            }
        });
        register(new IReward() {
            @Override
            public TimeUnit timeUnit() {
                return TimeUnit.HOURS;
            }

            @Override
            public int time() {
                return 5;
            }

            @Override
            public String[] commands() {
                return new String[]{"crate key &p rara","points give &p 10"};
            }

            @Override
            public double money() {
                return 150;
            }

            @Override
            public double xp() {
                return 45;
            }

            @Override
            public int Reward_UUID() {
                return lastId();
            }
        });
        register(new IReward() {
            @Override
            public TimeUnit timeUnit() {
                return TimeUnit.HOURS;
            }

            @Override
            public int time() {
                return 6;
            }

            @Override
            public String[] commands() {
                return new String[]{"crate key &p rara","points give &p 10"};
            }

            @Override
            public double money() {
                return 200;
            }

            @Override
            public double xp() {
                return 50;
            }

            @Override
            public int Reward_UUID() {
                return lastId();
            }
        });
        */

        SyrxOntime.logger().info("Registrado " + rewards.size() + " rewards.");

        for(Reward reward : rewards){
            SyrxOntime.logger().info("REWARDID: " +reward.Reward_UUID());
        }
    }
}
