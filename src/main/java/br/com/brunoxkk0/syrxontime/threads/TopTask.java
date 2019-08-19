package br.com.brunoxkk0.syrxontime.threads;

import br.com.brunoxkk0.syrxontime.SyrxOntime;
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

        if(finalMap.isEmpty() || System.currentTimeMillis() - lastTime > 600000){
            for(Player player : Bukkit.getServer().getOnlinePlayers()){
                finalMap.replace(player.getUniqueId(), (long) player.getStatistic(Statistic.PLAY_ONE_TICK) / (20));
                new Thread(TopTask::organize).start();
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
            if(file.exists() && file.isDirectory()){
                for(File fl : Objects.requireNonNull(file.listFiles())){
                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse(new FileReader(fl));
                    JSONObject jsonObject = (JSONObject) obj;

                    UUID uuid = UUID.fromString(fl.getName().replace(".json",""));

                    if(uuid != null){
                        try{
                            Long time = (Long) jsonObject.get("stat.playOneMinute");
                            map.put(uuid ,time/20);
                        }catch (Exception ignored){
                          if(SyrxOntime.debug)SyrxOntime.debug("Erro ao carregar a data: " + uuid);
                        }
                    }

                }
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        ArrayList<Entry<UUID, Long>> vet = new ArrayList<>(map.entrySet());

        vet.sort((o1, o2) -> {
            long v1 = (o1.getValue() != null) ? o1.getValue() : 0;
            long v2 = (o2.getValue() != null) ? o2.getValue() : 0;

            return Long.compare(v1, v2);
        });

        Collections.reverse(vet);

        for(Entry<UUID,Long> longEntry : vet){
            finalMap.put(longEntry.getKey(), longEntry.getValue());
        }

        lastTime = System.currentTimeMillis();
        System.out.println("Consumed Time: " + (lastTime-times)+" MS. Loaded and Organized: " + finalMap.size() + " values.");
    }

    private static void organize(){
        ArrayList<Entry<UUID, Long>> vet = new ArrayList<>(finalMap.entrySet());

        vet.sort((o1, o2) -> {
            long v1 = (o1.getValue() != null) ? o1.getValue() : 0;
            long v2 = (o2.getValue() != null) ? o2.getValue() : 0;

            return Long.compare(v1, v2);
        });

        Collections.reverse(vet);

        for(Entry<UUID,Long> longEntry : vet){
            finalMap.put(longEntry.getKey(), longEntry.getValue());
        }

        lastTime = System.currentTimeMillis();
    }



}
