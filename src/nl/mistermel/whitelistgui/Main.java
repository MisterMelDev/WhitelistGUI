package nl.mistermel.whitelistgui;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {

	public static String prefix = ChatColor.GRAY + "[" + ChatColor.YELLOW + "WhitelistGUI" + ChatColor.GRAY + "] ";

	public void onEnable() {
		GUI.init();
		getServer().getPluginManager().registerEvents(new GUI(), this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(prefix + ChatColor.RED + "This command can only be used as a player!");
			return true;
		}
		Player p = (Player) sender;
		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("info")) {
				p.sendMessage(ChatColor.GRAY + "+-----" + ChatColor.GOLD + "WhitelistGUI" + ChatColor.GRAY + "-----+");
				p.sendMessage(ChatColor.GRAY + "Author: " + ChatColor.DARK_AQUA + "MisterMel");
				p.sendMessage(ChatColor.GRAY + "Version: " + ChatColor.DARK_AQUA + "1.0");
				p.sendMessage(ChatColor.GRAY + "Use /whitelistgui to open the GUI.");
				p.sendMessage(ChatColor.GRAY + "+-----" + ChatColor.GOLD + "WhitelistGUI" + ChatColor.GRAY + "-----+");
				return true;
			}
		}
		if (!p.hasPermission("whitelistgui.use")) {
			p.sendMessage(prefix + ChatColor.GOLD
					+ "You dont have the required permission to use this command! Needed permission: " + ChatColor.AQUA
					+ "whitelistgui.use");
			return true;
		}
		GUI.open(p);
		return true;
	}

}
