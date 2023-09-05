package com.minecraftdimensions.slashserver;

import com.minecraftdimensions.slashserver.configlibrary.Config;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.ScheduledTask;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SlashServer extends Plugin {
	ProxyServer proxy;
    public static Config c;
    public static HashMap<String, Integer> time = new HashMap<>();
    public static ArrayList<ProxiedPlayer> tasks = new ArrayList<>(  );
    public static SlashServer INSTANCE;
    public static String ALREADY_ON_SERVER;
    public static String TELEPORTING;
    public static String ALREADY_TELEPORTING;

	public void onEnable() {
        try {
            Class<?> c = Class.forName("org.yaml.snakeyaml.representer.Representer");
            System.out.println("Methods of Representer: "
                    + Arrays.toString(
                    c.getConstructors()));
        } catch(ClassNotFoundException e) {
            System.out.println(e.getException().toString());
        }
        INSTANCE = this;
		proxy = ProxyServer.getInstance();
		registerCommands();
        setupConfig();
	}

    private void setupConfig() {
        String configpath = File.separator + "plugins" + File.separator + "SlashServer" + File.separator + "config.yml";
        c = new Config( configpath );
        for(String data: proxy.getServers().keySet()){
            time.put( data, c.getInt( data,0 ) );
        }
        ALREADY_ON_SERVER = color(c.getString( "ALREADY_ON_SERVER", "&cYou are already on that server!" ));
        TELEPORTING = color(c.getString( "TELEPORTING", "&2Teleporting your to the server {name}" ));
        ALREADY_TELEPORTING = color(c.getString( "ALREADY_TELEPORTING", "&cAlready teleporting you to a server" ));
    }

    public static String color( String input ){
        return ChatColor.translateAlternateColorCodes('&',input);
    }

    private void registerCommands() {
		for (String data : proxy.getServers().keySet()) {
			ProxyServer.getInstance().getPluginManager().registerCommand( this, new ServerCommand( data, "slashserver."+data ) );
		}

        ProxyServer.getInstance().getPluginManager().registerCommand( this, new SlashServerReloadCommand( "reloadss", "slashserver.reload", "reloadslashserver", "slashserverreload", "ssreload" ) );
	}



}
