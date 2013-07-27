package com.minecraftdimensions.slashserver;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ServerCommand extends Command {

	String server;
	
	public ServerCommand(String name) {
		super(name);
		server = name;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		
		ProxiedPlayer pp = (ProxiedPlayer)sender;
		if(pp.hasPermission("slashserver."+server) && args.length==0){
		pp.connect(ProxyServer.getInstance().getServerInfo(server));
		}else{
			pp.chat("/"+server);
		}

	}

}
