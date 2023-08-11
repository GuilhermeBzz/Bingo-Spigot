package br.com.bingo.kits.listeners;

import br.com.bingo.game.GameManager;
import br.com.bingo.game.GameType;
import br.com.bingo.kits.Kit;
import br.com.bingo.kits.KitType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class GamblerListener implements Listener {

    GameManager gameManager;
    Map<UUID, List<KitType>> kits = new HashMap<>();

    public GamblerListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onKitSelect(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals(ChatColor.DARK_PURPLE + "Gambler Kits")) {
            return;
        }
        event.setCancelled(true);


        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType().equals(Material.AIR)) {
            return;
        }
        ItemMeta clickedMeta = clickedItem.getItemMeta();
        String clickedName = clickedMeta.getDisplayName();
        clickedName = clickedName.replace(ChatColor.AQUA.toString(), "");


        List<KitType> allKits = new ArrayList<>();
        Collections.addAll(allKits, KitType.values());

        gameManager.playerKit.remove(event.getWhoClicked().getUniqueId());
        for (KitType kitType : allKits) {
            if (kitType.getName().equals(clickedName)) {
                gameManager.playerKit.put(event.getWhoClicked().getUniqueId(), kitType);
                event.getWhoClicked().sendMessage(ChatColor.GREEN + "Você selecionou o kit " + ChatColor.AQUA + kitType.getName());
                event.getWhoClicked().closeInventory();
                event.getWhoClicked().getInventory().remove(Material.EYE_ARMOR_TRIM_SMITHING_TEMPLATE);
                kits.remove(event.getWhoClicked().getUniqueId());
                Kit kit = kitType.getKit();
                kit.startKit((Player) event.getWhoClicked());
                return;
            }
        }
    }

    @EventHandler
    public void onItemUse(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        if (!event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Escolher Kit")) return;
        if (event.getAction().name().contains("RIGHT")) {
            selectKit(event.getPlayer());
        }
    }


    public void selectKit(Player player) {
        Inventory gamblerMenu = Bukkit.createInventory(null, 3 * 9, ChatColor.DARK_PURPLE + "Gambler Kits");

        List<KitType> gamblerKits = new ArrayList<>();
        if (kits.containsKey(player.getUniqueId())) {
            gamblerKits = kits.get(player.getUniqueId());
        } else {
            List<KitType> allKits = new ArrayList<>();
            Collections.addAll(allKits, KitType.values());
            allKits.remove(KitType.GAMBLER);
            allKits.remove(KitType.SURPRISE);
            if (gameManager.getGameType().equals(GameType.SOLO)) allKits.remove(KitType.PAO);
            Collections.shuffle(allKits);
            gamblerKits = new ArrayList<>(allKits.subList(0, 3));
            kits.put(player.getUniqueId(), gamblerKits);
        }


        int slot = 11;
        for (KitType kitType : gamblerKits) {
            ItemStack kitItem = new ItemStack(kitType.getIcon());
            ItemMeta kitItemMeta = kitItem.getItemMeta();
            kitItemMeta.setDisplayName(ChatColor.AQUA + kitType.getName());
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + kitType.getDescription());
            lore.add("");
            lore.add(ChatColor.YELLOW + "Clique para selecionar!");
            kitItemMeta.setLore(lore);
            kitItem.setItemMeta(kitItemMeta);
            gamblerMenu.setItem(slot, kitItem);
            slot += 2;
        }


        player.openInventory(gamblerMenu);
    }

}