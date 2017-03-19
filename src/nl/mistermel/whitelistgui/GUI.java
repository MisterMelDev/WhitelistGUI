package nl.mistermel.whitelistgui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import net.md_5.bungee.api.ChatColor;

public class GUI implements Listener {
	
	private static Inventory mainMenu;
	private static Inventory whitelistedPlayers;
	private static Inventory addPlayers;
	private static String name = ChatColor.DARK_AQUA + "Whitelist" + ChatColor.GOLD + "GUI";
	
	private static ArrayList<UUID> addPlayer = new ArrayList<UUID>();
	
	public static void init() {
		mainMenu = Bukkit.createInventory(null, InventoryType.HOPPER, name);
		whitelistedPlayers = Bukkit.createInventory(null, InventoryType.CHEST, name);
		addPlayers = Bukkit.createInventory(null, InventoryType.CHEST, name);
		update();
		updateWhitelistedPlayers();
		updateAddPlayers();
	}
	
	public static void update() {
		ItemStack red = new ItemStack(Material.REDSTONE_BLOCK);
		ItemStack green = new ItemStack(Material.EMERALD_BLOCK);
		ItemStack paper = new ItemStack(Material.PAPER);
		ItemStack map = new ItemStack(Material.EMPTY_MAP);
		ItemStack filler = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 11);
		
		ItemMeta redMeta = red.getItemMeta();
		redMeta.setDisplayName(ChatColor.DARK_AQUA + "The whitelist is currently: " + ChatColor.RED + "Off");
		redMeta.setLore(Arrays.asList(ChatColor.GRAY + "Click to turn the whitelist on."));
		red.setItemMeta(redMeta);
		
		ItemMeta greenMeta = red.getItemMeta();
		greenMeta.setDisplayName(ChatColor.DARK_AQUA + "The whitelist is currently: " + ChatColor.GREEN + "On");
		greenMeta.setLore(Arrays.asList(ChatColor.GRAY + "Click to turn the whitelist off."));
		green.setItemMeta(greenMeta);
		
		ItemMeta paperMeta = paper.getItemMeta();
		paperMeta.setDisplayName(ChatColor.DARK_AQUA + "Whitelisted Players");
		paperMeta.setLore(Arrays.asList(ChatColor.GRAY + "Click to view all whitelisted players."));
		paper.setItemMeta(paperMeta);
		
		ItemMeta mapMeta = map.getItemMeta();
		mapMeta.setDisplayName(ChatColor.DARK_AQUA + "Add players to the whitelist");
		mapMeta.setLore(Arrays.asList(ChatColor.GRAY + "Click to add players to the whitelist."));
		map.setItemMeta(mapMeta);
		
		ItemMeta fillerMeta = filler.getItemMeta();
		fillerMeta.setDisplayName(" ");
		filler.setItemMeta(fillerMeta);
		
		if(Bukkit.hasWhitelist()) {
			mainMenu.setItem(2, green);
		} else {
			mainMenu.setItem(2, red);
		}
		
		mainMenu.setItem(4, paper);
		mainMenu.setItem(0, map);
		
		mainMenu.setItem(1, filler);
		mainMenu.setItem(3, filler);
	}
	
	public static void updateWhitelistedPlayers() {
		whitelistedPlayers.clear();
		
		ItemStack back = new ItemStack(Material.ARROW);
		ItemStack filler = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 11);
		
		ItemMeta backMeta = back.getItemMeta();
		backMeta.setDisplayName(ChatColor.DARK_AQUA + "Go back");
		back.setItemMeta(backMeta);
		
		ItemMeta fillerMeta = filler.getItemMeta();
		fillerMeta.setDisplayName(" ");
		filler.setItemMeta(fillerMeta);
		
		whitelistedPlayers.setItem(18, back);
		for(int i = 19; i < 27; i++) {
			whitelistedPlayers.setItem(i, filler);
		}
		
		for(OfflinePlayer p : Bukkit.getWhitelistedPlayers()) {
			ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
			SkullMeta headMeta = (SkullMeta) head.getItemMeta();
			headMeta.setOwner(p.getName());
			headMeta.setDisplayName(p.getName());
			headMeta.setLore(Arrays.asList(ChatColor.RED + "Click to remove this player from the whitelist."));
			head.setItemMeta(headMeta);
			whitelistedPlayers.addItem(head);
		}
	}
	
	public static void updateAddPlayers() {
		addPlayers.clear();
		
		ItemStack back = new ItemStack(Material.ARROW);
		ItemStack sign = new ItemStack(Material.SIGN);
		ItemStack filler = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 11);
		
		ItemMeta backMeta = back.getItemMeta();
		backMeta.setDisplayName(ChatColor.DARK_AQUA + "Go back");
		back.setItemMeta(backMeta);
		
		ItemMeta signMeta = sign.getItemMeta();
		signMeta.setDisplayName(ChatColor.DARK_AQUA + "Enter Name");
		signMeta.setLore(Arrays.asList(ChatColor.GRAY + "Click here to enter a", ChatColor.GRAY + "name manually."));
		sign.setItemMeta(signMeta);
		
		ItemMeta fillerMeta = filler.getItemMeta();
		fillerMeta.setDisplayName(" ");
		filler.setItemMeta(fillerMeta);
		
		addPlayers.setItem(18, back);
		for(int i = 19; i < 27; i++) {
			addPlayers.setItem(i, filler);
		}
		addPlayers.setItem(22, sign);
		
		for(OfflinePlayer p : Bukkit.getOfflinePlayers()) {
			ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
			SkullMeta headMeta = (SkullMeta) head.getItemMeta();
			headMeta.setOwner(p.getName());
			headMeta.setDisplayName(p.getName());
			if(p.isWhitelisted()) {
				headMeta.setLore(Arrays.asList(ChatColor.GREEN + "This player is already on the whitelist."));
			} else {
				headMeta.setLore(Arrays.asList(ChatColor.GREEN + "Click to add this player to the whitelist."));
			}
			head.setItemMeta(headMeta);
			addPlayers.addItem(head);
		}
	} 
	
	public static void open(Player p) {
		update();
		p.openInventory(mainMenu);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if(e.getInventory().getName() != name) return;
		if(e.getCurrentItem() == null) return;
		e.setCancelled(true);
		if(e.getCurrentItem().getType() == Material.REDSTONE_BLOCK) {
			Bukkit.setWhitelist(true);
			e.getWhoClicked().sendMessage(Main.prefix + ChatColor.GOLD + "The whitelist has been turned " + ChatColor.GREEN + "on.");
			update();
			return;
		}
		if(e.getCurrentItem().getType() == Material.EMERALD_BLOCK) {
			Bukkit.setWhitelist(false);
			e.getWhoClicked().sendMessage(Main.prefix + ChatColor.GOLD + "The whitelist has been turned " + ChatColor.RED + "off.");
			update();
			return;
		}
		if(e.getCurrentItem().getType() == Material.PAPER) {
			e.getWhoClicked().closeInventory();
			updateWhitelistedPlayers();
			e.getWhoClicked().openInventory(whitelistedPlayers);
		}
		if(e.getCurrentItem().getType() == Material.ARROW) {
			e.getWhoClicked().closeInventory();
			update();
			e.getWhoClicked().openInventory(mainMenu);
		}
		if(e.getCurrentItem().getType() == Material.EMPTY_MAP) {
			e.getWhoClicked().closeInventory();
			updateAddPlayers();
			e.getWhoClicked().openInventory(addPlayers);
		}
		if(e.getCurrentItem().getType() == Material.SIGN) {
			e.getWhoClicked().closeInventory();
			e.getWhoClicked().sendMessage(Main.prefix + ChatColor.GOLD + "Type the name that you want to add to the whitelist in chat, or type cancel to cancel.");
			addPlayer.add(e.getWhoClicked().getUniqueId());
		}
		if(e.getCurrentItem().getType() == Material.SKULL_ITEM) {
			if(e.getCurrentItem().getItemMeta().getLore().equals(Arrays.asList(ChatColor.RED + "Click to remove this player from the whitelist."))) {
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "whitelist remove " + e.getCurrentItem().getItemMeta().getDisplayName());
				e.getWhoClicked().sendMessage(Main.prefix + ChatColor.GOLD + "Removed player " + ChatColor.DARK_AQUA + e.getCurrentItem().getItemMeta().getDisplayName() + ChatColor.GOLD + " from the whitelist.");
				updateWhitelistedPlayers();
			}
			if(e.getCurrentItem().getItemMeta().getLore().equals(Arrays.asList(ChatColor.GREEN + "Click to add this player to the whitelist."))) {
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "whitelist add " + e.getCurrentItem().getItemMeta().getDisplayName());
				e.getWhoClicked().sendMessage(Main.prefix + ChatColor.GOLD + "Added player " + ChatColor.DARK_AQUA + e.getCurrentItem().getItemMeta().getDisplayName() + ChatColor.GOLD + " to the whitelist.");
				updateAddPlayers();
			}
		}
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		if(addPlayer.contains(e.getPlayer().getUniqueId())) {
			if(e.getMessage().equalsIgnoreCase("cancel")) {
				e.getPlayer().sendMessage(Main.prefix + ChatColor.RED + "Cancelled.");
				addPlayer.remove(e.getPlayer().getUniqueId());
				e.setCancelled(true);
				return;
			}
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "whitelist add " + e.getMessage());
			e.getPlayer().sendMessage(Main.prefix + ChatColor.GOLD + "Added player " + ChatColor.DARK_AQUA + e.getMessage() + ChatColor.GOLD + " to the whitelist!");
			addPlayer.remove(e.getPlayer().getUniqueId());
			e.setCancelled(true);
		}
	}
	
}
