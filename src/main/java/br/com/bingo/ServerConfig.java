package br.com.bingo;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ServerConfig {

    private final static ServerConfig instance = new ServerConfig();

    private File file;
    private YamlConfiguration config;

    public static String WEB_SERVICE_URL;
    public static Long MINI_FEAST_ONE_TIME;
    public static Long MINI_FEAST_TWO_TIME;
    public static Long MINI_FEAST_THREE_TIME;
    public static Long FEAST_TIME;
    public static Long BONUS_FEAST_TIME;

    private ServerConfig() {
    }

    public void load(){
        file = new File(Bingo.getInstance().getDataFolder(), "config.yml");

        if(!file.exists()){
            Bingo.getInstance().saveResource("config.yml", false);
        }

        config = new YamlConfiguration();
        config.options().parseComments(true);

        try{
            config.load(file);

        } catch (Exception e){
            e.printStackTrace();
        }

        WEB_SERVICE_URL = config.getString("web.url");
        MINI_FEAST_ONE_TIME = config.getLong("timers.miniFeast-1");
        MINI_FEAST_TWO_TIME = config.getLong("timers.miniFeast-2");
        MINI_FEAST_THREE_TIME = config.getLong("timers.miniFeast-3");
        FEAST_TIME = config.getLong("timers.feast");
        BONUS_FEAST_TIME = config.getLong("timers.bonusFeast");
    }

    public void save(){
        try {
            config.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ServerConfig getInstance() {
        return instance;
    }


}
