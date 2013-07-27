package com.minecraftdimensions.slashserver;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class SlashServer extends Plugin {
	ProxyServer proxy;

	public void onEnable() {
		proxy = ProxyServer.getInstance();
		registerCommands();
	}

	private void registerCommands() {
		for (String data : proxy.getServers().keySet()) {
			ProxyServer.getInstance().getPluginManager()
					.registerCommand(this, new ServerCommand(data));
		}
	}

}
