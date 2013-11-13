package com.minecraftdimensions.slashserver;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.concurrent.TimeUnit;

public class ServerCommand extends Command {

    String name;
    private ServerInfo server;

    public ServerCommand( String name, String permission, String... aliases ) {
        super( name, permission, aliases );
        this.name = name;
        server = ProxyServer.getInstance().getServerInfo( name );
    }


    @Override
	public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)){
            return;
        }
		final ProxiedPlayer pp = (ProxiedPlayer)sender;
        if(pp.getServer().getInfo().getName().equalsIgnoreCase( name )){
             pp.sendMessage( SlashServer.ALREADY_ON_SERVER.replace( "{name}", name ) );
        }else{
            if(SlashServer.tasks.contains( pp )){
                pp.sendMessage( SlashServer.ALREADY_TELEPORTING.replace( "{name}", name ) );
                return;
            }
            pp.sendMessage( SlashServer.TELEPORTING.replace( "{name}", name ).replace( "{time}", (SlashServer.time.get( name )/1000)+"" ));
            SlashServer.tasks.add( pp );
            ProxyServer.getInstance().getScheduler().schedule( SlashServer.INSTANCE, new Runnable() {
                @Override
                public void run() {
                    if(SlashServer.tasks.contains( pp )){
                        if(pp!=null){
                            pp.connect( server );
                        }
                        SlashServer.tasks.remove( pp );
                    }

                }
            },SlashServer.time.get( name ), TimeUnit.MILLISECONDS );

        }

	}

}
