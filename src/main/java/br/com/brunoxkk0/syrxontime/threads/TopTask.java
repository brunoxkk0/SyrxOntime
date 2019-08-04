package br.com.brunoxkk0.syrxontime.threads;

import br.com.brunoxkk0.syrxontime.data.PlayerTime;
import br.com.brunoxkk0.syrxontime.data.Provider;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class TopTask extends Thread {

    private static TopTask task;
    private volatile HashMap<UUID, Long> map = new HashMap<>();
    private static volatile LinkedHashMap<UUID,Long> finalMap = new LinkedHashMap<>();
    private static long lastTime = 0;

    public static void initialize(){
        if(task == null){
            task = new TopTask();
            task.start();
        }
    }

    public static LinkedHashMap<UUID, Long> getMap(){

        if(System.currentTimeMillis() - lastTime < 600000){
            for(Player player : Bukkit.getServer().getOnlinePlayers()){
                finalMap.replace(player.getUniqueId(), (long) player.getStatistic(Statistic.PLAY_ONE_TICK) / (20 * 60));

                new Thread(){
                    @Override
                    public void run() {
                        organize();
                    }
                }.start();
            }
        }

        return finalMap;
    }



    private String levelName() throws IOException {
        FileReader fileReader = new FileReader(new File("server.properties"));
        BufferedReader reader = new BufferedReader(fileReader);

        String line;

        while ((line = reader.readLine()) != null){
            if(line.startsWith("level-name=")){
                return line.replace("level-name=","");
            }
        }

        return null;
    }

    @Override
    public void run() {

        long times = System.currentTimeMillis();

        try {
            File file = new File(levelName() + "/stats");
            if(file.isDirectory() && file.exists()){
                for(File fl : Objects.requireNonNull(file.listFiles())){
                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse(new FileReader(fl));
                    JSONObject jsonObject = (JSONObject) obj;

                    map.put(UUID.fromString(fl.getName().replace(".json","")),(Long) jsonObject.get("stat.playOneMinute"));
                }
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        ArrayList<Entry<UUID,Long>> vet = new ArrayList<>();
        for(Entry<UUID,Long> longEntry : map.entrySet()){
            vet.add(longEntry);
        }

        Collections.sort(vet, new Comparator<Entry<UUID, Long>>() {
            @Override
            public int compare(Entry<UUID, Long> o1, Entry<UUID, Long> o2) {
                long v1 = (o1.getValue() != null)? o1.getValue() : 0;
                long v2 = (o2.getValue() != null)? o2.getValue() : 0;

                return Long.compare(v1, v2);
            }
        });

        Collections.reverse(vet);

        for(Entry<UUID,Long> longEntry : vet){
            finalMap.put(longEntry.getKey(), longEntry.getValue());
        }

        lastTime = System.currentTimeMillis();
        System.out.println("Consumed Time: " + (lastTime-times)+" MS. Loaded and Organized: " + finalMap.size() + " values.");
    }

    private static void organize(){
        ArrayList<Entry<UUID,Long>> vet = new ArrayList<>();
        for(Entry<UUID,Long> longEntry : finalMap.entrySet()){
            vet.add(longEntry);
        }

        Collections.sort(vet, new Comparator<Entry<UUID, Long>>() {
            @Override
            public int compare(Entry<UUID, Long> o1, Entry<UUID, Long> o2) {
                long v1 = (o1.getValue() != null)? o1.getValue() : 0;
                long v2 = (o2.getValue() != null)? o2.getValue() : 0;

                return Long.compare(v1, v2);
            }
        });

        Collections.reverse(vet);

        for(Entry<UUID,Long> longEntry : vet){
            finalMap.put(longEntry.getKey(), longEntry.getValue());
        }
    }



}
