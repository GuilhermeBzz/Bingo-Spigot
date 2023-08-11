package br.com.bingo.ui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BingoMenu {


    public static void giveMenuOpener(Player player){
        ItemStack item = new ItemStack(Material.BOOK);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setDisplayName(ChatColor.GREEN + "Menu");
        item.setItemMeta(itemMeta);
        player.getInventory().setItem(5, item);
    }

    public static void openMenu(Player player){
        Inventory menu = Bukkit.createInventory(null, 3 * 9, ChatColor.DARK_RED + "Menu");

        ItemStack crafting = new ItemStack(Material.CRAFTING_TABLE);
        ItemMeta craftingMeta = crafting.getItemMeta();
        craftingMeta.setDisplayName(ChatColor.GREEN + "Criar nova Partida");
        crafting.setItemMeta(craftingMeta);

        ItemStack endPortalFrame = new ItemStack(Material.END_PORTAL_FRAME);
        ItemMeta endPortalFrameMeta = endPortalFrame.getItemMeta();
        endPortalFrameMeta.setDisplayName(ChatColor.RED + "Finalizar Partida");
        endPortalFrame.setItemMeta(endPortalFrameMeta);

        ItemStack barrier = new ItemStack(Material.BARRIER);
        ItemMeta barrierMeta = barrier.getItemMeta();
        barrierMeta.setDisplayName(ChatColor.RED + "Cancelar Partida");
        barrier.setItemMeta(barrierMeta);

        ItemStack paper = new ItemStack(Material.PAPER);
        ItemMeta paperMeta = paper.getItemMeta();
        paperMeta.setDisplayName(ChatColor.GOLD + "Pegar Cartela");
        paper.setItemMeta(paperMeta);

        ItemStack grass = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta grassMeta = grass.getItemMeta();
        grassMeta.setDisplayName(ChatColor.AQUA + "TP para o GameWorld");
        grass.setItemMeta(grassMeta);

        ItemStack prismarine = new ItemStack(Material.DARK_PRISMARINE);
        ItemMeta prismarineMeta = prismarine.getItemMeta();
        prismarineMeta.setDisplayName(ChatColor.AQUA + "TP para o Lobby");
        prismarine.setItemMeta(prismarineMeta);

        ItemStack limeTerracota = new ItemStack(Material.LIME_TERRACOTTA);
        ItemMeta limeTerracotaMeta = limeTerracota.getItemMeta();
        limeTerracotaMeta.setDisplayName(ChatColor.GREEN + "Iniciar Partida");
        limeTerracota.setItemMeta(limeTerracotaMeta);

        ItemStack chest = new ItemStack(Material.CHEST);
        ItemMeta chestMeta = chest.getItemMeta();
        chestMeta.setDisplayName(ChatColor.GREEN + "Kits");
        chest.setItemMeta(chestMeta);

        ItemStack book = new ItemStack(Material.BOOK);
        ItemMeta bookMeta = book.getItemMeta();
        bookMeta.setDisplayName(ChatColor.GREEN + "Patch Notes");
        book.setItemMeta(bookMeta);

        ItemStack record = new ItemStack(Material.MUSIC_DISC_CAT);
        ItemMeta recordMeta = record.getItemMeta();
        recordMeta.setDisplayName(ChatColor.GREEN + "Última Partida");
        record.setItemMeta(recordMeta);


        menu.setItem(0, record);
        menu.setItem(10, crafting);
        menu.setItem(12, limeTerracota);
        menu.setItem(14, endPortalFrame);
        menu.setItem(16, barrier);
        menu.setItem(20, prismarine);
        menu.setItem(24, grass);
        menu.setItem(22, paper);
        menu.setItem(4, chest);
        menu.setItem(8, book);

        player.openInventory(menu);
    }
}
