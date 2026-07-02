package br.com.bingo.kits.listeners;

import br.com.bingo.game.CartographerManager;
import br.com.bingo.game.GameManager;
import br.com.bingo.kits.MappedStructure;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.UUID;

public class CartographerListener implements Listener {

    private static final String MAP_TITLE = ChatColor.DARK_RED + "Mapa de Estruturas";
    private static final String DRAFT_TITLE = ChatColor.DARK_RED + "Draft de Estruturas";
    private static final int DRAFT_BUTTON_SLOT = 26;
    private static final String COMPASS_NAME = ChatColor.GOLD + "Cartographer's Compass";

    private final GameManager gameManager;

    public CartographerListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onCompassUse(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        ItemMeta meta = event.getItem().getItemMeta();
        if (meta == null || !COMPASS_NAME.equals(meta.getDisplayName())) return;
        if (event.getAction().name().contains("RIGHT")) {
            openMap(event.getPlayer());
        }
    }

    public void openMap(Player player) {
        CartographerManager manager = gameManager.getCartographerManager();
        Inventory menu = Bukkit.createInventory(null, 9 * 3, MAP_TITLE);

        for (MappedStructure structure : manager.getOwned(player.getUniqueId())) {
            ItemStack icon = new ItemStack(structure.getIcon());
            ItemMeta iconMeta = icon.getItemMeta();
            iconMeta.setDisplayName(ChatColor.GREEN + structure.getDisplayName());
            iconMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            icon.setItemMeta(iconMeta);
            menu.addItem(icon);
        }

        menu.setItem(DRAFT_BUTTON_SLOT, buildDraftButton(manager, player.getUniqueId()));
        player.openInventory(menu);
    }

    private ItemStack buildDraftButton(CartographerManager manager, UUID uuid) {
        boolean available = manager.isDraftAvailable(uuid);
        ItemStack button = new ItemStack(available ? Material.EMERALD_BLOCK : Material.REDSTONE_BLOCK);
        ItemMeta meta = button.getItemMeta();
        if (available) {
            meta.setDisplayName(ChatColor.GREEN + "Draftar estrutura");
        } else {
            meta.setDisplayName(ChatColor.RED + "Aguarde " + manager.remainingCooldownSeconds(uuid) + "s");
        }
        button.setItemMeta(meta);
        return button;
    }

    private void openDraft(Player player) {
        CartographerManager manager = gameManager.getCartographerManager();
        List<MappedStructure> options = manager.rollDraft(player.getUniqueId());
        if (options.isEmpty()) {
            player.sendMessage(ChatColor.RED + "Nenhuma nova estrutura disponivel para draft.");
            return;
        }
        Inventory draft = Bukkit.createInventory(null, 9, DRAFT_TITLE);
        int slot = (draft.getSize() - options.size()) / 2; // centraliza (3 -> slots 3,4,5; 5 -> 2..6)
        for (MappedStructure structure : options) {
            ItemStack icon = new ItemStack(structure.getIcon());
            ItemMeta meta = icon.getItemMeta();
            meta.setDisplayName(ChatColor.GOLD + structure.getDisplayName());
            icon.setItemMeta(meta);
            draft.setItem(slot, icon);
            slot++;
        }
        player.openInventory(draft);
    }

    private MappedStructure matchStructure(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return null;
        String name = ChatColor.stripColor(item.getItemMeta().getDisplayName());
        if (name == null) return null;
        for (MappedStructure s : MappedStructure.values()) {
            if (s.getDisplayName().equals(name)) return s;
        }
        return null;
    }

    private void pointCompass(Player player, Location location, String name) {
        if (location == null) return;
        ItemStack compass = null;
        for (ItemStack i : player.getInventory().getContents()) {
            if (i != null && i.getType() == Material.COMPASS) {
                compass = i;
                break;
            }
        }
        if (compass == null) return;
        CompassMeta meta = (CompassMeta) compass.getItemMeta();
        meta.setLodestone(location);
        meta.setLodestoneTracked(false);
        compass.setItemMeta(meta);
        player.setCompassTarget(location);
        player.sendMessage(ChatColor.GREEN + "Bussola apontada para: " + ChatColor.GOLD + name);
        player.closeInventory();
    }

    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {
        String title = event.getView().getTitle();
        if (!title.equals(MAP_TITLE) && !title.equals(DRAFT_TITLE)) return;
        event.setCancelled(true);
        if (event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta()) return;

        Player player = (Player) event.getWhoClicked();
        CartographerManager manager = gameManager.getCartographerManager();

        if (title.equals(MAP_TITLE)) {
            if (event.getRawSlot() == DRAFT_BUTTON_SLOT) {
                if (manager.isDraftAvailable(player.getUniqueId())) {
                    openDraft(player);
                } else {
                    player.sendMessage(ChatColor.RED + "Draft em cooldown: " + manager.remainingCooldownSeconds(player.getUniqueId()) + "s restantes.");
                }
                return;
            }
            MappedStructure clicked = matchStructure(event.getCurrentItem());
            if (clicked == null || !manager.getOwned(player.getUniqueId()).contains(clicked)) return;
            pointCompass(player, manager.getLocation(clicked), clicked.getDisplayName());
        } else {
            MappedStructure chosen = matchStructure(event.getCurrentItem());
            if (chosen == null) return;
            if (manager.chooseFromDraft(player.getUniqueId(), chosen)) {
                player.sendMessage(ChatColor.GREEN + "Estrutura adicionada: " + ChatColor.GOLD + chosen.getDisplayName());
                openMap(player);
            }
        }
    }
}
