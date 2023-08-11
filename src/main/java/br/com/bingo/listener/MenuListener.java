package br.com.bingo.listener;

import br.com.bingo.ui.BingoMenu;
import br.com.bingo.ChangeLog;
import br.com.bingo.game.GameManager;
import br.com.bingo.game.GameType;
import br.com.bingo.kits.KitManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MenuListener implements Listener {

    GameManager gameManager;
    Boolean kit;
    GameType gameType;
    int difficulty;

    Boolean ranked;
    private final List<Material> difficultyMaterials = new ArrayList<>(Arrays.asList(
            Material.COAL,
            Material.BRICK,
            Material.COPPER_INGOT,
            Material.IRON_INGOT,
            Material.GOLD_INGOT,
            Material.DIAMOND,
            Material.EMERALD,
            Material.NETHERITE_INGOT,
            Material.DRAGON_EGG,
            Material.NETHER_STAR
    ));

    public MenuListener(GameManager gameManager){
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onMenuClick(InventoryClickEvent event) throws NoSuchFieldException, IllegalAccessException {
        if(event.getView().getTitle().equals(ChatColor.DARK_RED + "Menu")){
            event.setCancelled(true);

            ItemStack clickedItem = event.getCurrentItem();
            if(clickedItem == null){
                return;
            }

            switch (clickedItem.getType()) {
                case GRASS_BLOCK:
                    event.getWhoClicked().closeInventory();
                    gameManager.teleportToGameWorld((Player) event.getWhoClicked());
                    break;
                case END_PORTAL_FRAME:
                    event.getWhoClicked().closeInventory();
                    gameManager.finishCommand((Player) event.getWhoClicked());
                    break;
                case BARRIER:
                    event.getWhoClicked().closeInventory();
                    gameManager.cancelCommand((Player) event.getWhoClicked());
                    break;
                case PAPER:
                    event.getWhoClicked().closeInventory();
                    if(gameManager.isGameStarted()){
                        ItemStack item = new ItemStack(Material.PAPER);
                        ItemMeta meta = item.getItemMeta();
                        meta.setDisplayName(ChatColor.GOLD + "Cartela do Bingo");
                        item.setItemMeta(meta);
                        event.getWhoClicked().getInventory().addItem(item);
                    }else {
                        event.getWhoClicked().sendMessage(ChatColor.RED + "Nenhum partida iniciada. Crie e inicie uma nova partida para abrir o inventario.");
                    }
                    break;
                case DARK_PRISMARINE:
                    event.getWhoClicked().closeInventory();
                    gameManager.teleportToDefaultWorld((Player) event.getWhoClicked());
                    break;
                case LIME_TERRACOTTA:
                    event.getWhoClicked().closeInventory();
                    gameManager.startCommand((Player) event.getWhoClicked());
                    break;
                case CRAFTING_TABLE:
                    event.getWhoClicked().closeInventory();
                    openCreatorInventory((Player) event.getWhoClicked());
                    break;
                case CHEST:
                    event.getWhoClicked().closeInventory();
                    KitManager.openKitMenu((Player) event.getWhoClicked());
                    if(!gameManager.kit){
                        event.getWhoClicked().sendMessage(ChatColor.RED + "Kits nao estao habilitados");
                    }
                    break;
                case BOOK:
                    event.getWhoClicked().closeInventory();
                    ChangeLog.openChangeLog((Player) event.getWhoClicked());
                    break;
                case MUSIC_DISC_CAT:
                    event.getWhoClicked().closeInventory();
                    if(gameManager.hasLastGame()){
                        gameManager.lastGame.openLastGame((Player) event.getWhoClicked());
                    }else{
                        event.getWhoClicked().sendMessage(ChatColor.RED + "Nenhuma partida anterior encontrada");
                    }


            }

        }
    }

    public void openCreatorInventory(Player player){

        ItemStack teamAuto = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta teamAutoMeta = teamAuto.getItemMeta();
        teamAutoMeta.setDisplayName(ChatColor.GOLD + "Equipes Automaticas");
        teamAuto.setItemMeta(teamAutoMeta);

        ItemStack teamManual = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta teamManualMeta = teamManual.getItemMeta();
        teamManualMeta.setDisplayName(ChatColor.GOLD + "Equipes Manuais");
        teamManual.setItemMeta(teamManualMeta);

        ItemStack teamSolo = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta teamSoloMeta = teamSolo.getItemMeta();
        teamSoloMeta.setDisplayName(ChatColor.GOLD + "Solo");
        teamSolo.setItemMeta(teamSoloMeta);

        ItemStack confirm = new ItemStack(Material.LIME_CONCRETE);
        ItemMeta confirmMeta = confirm.getItemMeta();
        confirmMeta.setDisplayName(ChatColor.GOLD + "Confirmar");
        confirm.setItemMeta(confirmMeta);

        ItemStack cancel = new ItemStack(Material.RED_CONCRETE);
        ItemMeta cancelMeta = cancel.getItemMeta();
        cancelMeta.setDisplayName(ChatColor.GOLD + "Cancelar");
        cancel.setItemMeta(cancelMeta);

        ItemStack kit = new ItemStack(Material.GRAY_DYE);
        ItemMeta kitMeta = kit.getItemMeta();
        kitMeta.setDisplayName(ChatColor.GOLD + "Kit");
        kit.setItemMeta(kitMeta);

        ItemStack difficulty = new ItemStack(Material.GOLD_INGOT , 5);
        ItemMeta difficultyMeta = difficulty.getItemMeta();
        difficultyMeta.setDisplayName(ChatColor.GOLD + "Dificuldade");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GREEN + "Botao esquerdo para Aumentar a dificuldade");
        lore.add(ChatColor.GREEN + "Botao direito para Diminuir a dificuldade");
        difficultyMeta.setLore(lore);
        difficulty.setItemMeta(difficultyMeta);

        ItemStack ranked = new ItemStack(Material.INK_SAC);
        ItemMeta rankedMeta = ranked.getItemMeta();
        rankedMeta.setDisplayName(ChatColor.GOLD + "Ranked");
        rankedMeta.setLore(Collections.singletonList(ChatColor.RED + "Desativada"));
        ranked.setItemMeta(rankedMeta);

        Inventory creator = Bukkit.createInventory(null, 3 * 9, ChatColor.DARK_RED + "Criar Partida");

        creator.setItem(10, teamAuto);
        creator.setItem(11, teamManual);
        creator.setItem(12, teamSolo);
        creator.setItem(14, kit);
        creator.setItem(16, difficulty);
        creator.setItem(23, confirm);
        creator.setItem(22, ranked);
        creator.setItem(21, cancel);

        this.kit = false;
        this.gameType = null;
        this.difficulty = 5;
        this.ranked = false;
        player.openInventory(creator);

    }

    @EventHandler
    public void onCreationClick(InventoryClickEvent event){
        if(event.getView().getTitle().equals(ChatColor.DARK_RED + "Criar Partida")) {
            event.setCancelled(true);

            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null) {
                return;
            }
            ItemMeta clickedMeta = clickedItem.getItemMeta();
            if (clickedMeta == null) {
                return;
            }


            if (clickedMeta.getDisplayName().equals(ChatColor.GOLD + "Equipes Automaticas")) {
                gameType = GameType.TEAM_AUTO;
                ItemStack teamAuto = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                ItemMeta teamAutoMeta = teamAuto.getItemMeta();
                teamAutoMeta.setDisplayName(ChatColor.GOLD + "Equipes Automaticas");
                teamAuto.setItemMeta(teamAutoMeta);
                event.getInventory().setItem(10, teamAuto);

                ItemStack others = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                ItemMeta othersMeta = others.getItemMeta();

                othersMeta.setDisplayName(ChatColor.GOLD + "Equipes Manuais");
                others.setItemMeta(othersMeta);
                event.getInventory().setItem(11, others);

                othersMeta.setDisplayName(ChatColor.GOLD + "Solo");
                others.setItemMeta(othersMeta);
                event.getInventory().setItem(12, others);
            }
            else if (clickedMeta.getDisplayName().equals(ChatColor.GOLD + "Equipes Manuais")) {
                gameType = GameType.TEAM_MANUAL;
                ItemStack teamManual = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                ItemMeta teamManualMeta = teamManual.getItemMeta();
                teamManualMeta.setDisplayName(ChatColor.GOLD + "Equipes Manuais");
                teamManual.setItemMeta(teamManualMeta);
                event.getInventory().setItem(11, teamManual);

                ItemStack others = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                ItemMeta othersMeta = others.getItemMeta();
                othersMeta.setDisplayName(ChatColor.GOLD + "Equipes Automaticas");
                others.setItemMeta(othersMeta);
                event.getInventory().setItem(10, others);

                othersMeta.setDisplayName(ChatColor.GOLD + "Solo");
                others.setItemMeta(othersMeta);
                event.getInventory().setItem(12, others);
            }
            else if (clickedMeta.getDisplayName().equals(ChatColor.GOLD + "Solo")) {
                gameType = GameType.SOLO;
                ItemStack solo = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                ItemMeta soloMeta = solo.getItemMeta();
                soloMeta.setDisplayName(ChatColor.GOLD + "Solo");
                solo.setItemMeta(soloMeta);
                event.getInventory().setItem(12, solo);

                ItemStack others = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                ItemMeta othersMeta = others.getItemMeta();
                othersMeta.setDisplayName(ChatColor.GOLD + "Equipes Automaticas");
                others.setItemMeta(othersMeta);
                event.getInventory().setItem(10, others);
                othersMeta.setDisplayName(ChatColor.GOLD + "Equipes Manuais");
                others.setItemMeta(othersMeta);
                event.getInventory().setItem(11, others);

            }
            else if (clickedMeta.getDisplayName().equals(ChatColor.GOLD + "Confirmar")) {
                if(gameType == null){
                    return;
                } else{
                    event.getWhoClicked().closeInventory();
                    gameManager.createCommand((Player) event.getWhoClicked(), gameType, kit, difficulty, ranked);
                }
            }
            else if (clickedMeta.getDisplayName().equals(ChatColor.GOLD + "Cancelar")) {
                event.getWhoClicked().closeInventory();
            }
            else if (clickedMeta.getDisplayName().equals(ChatColor.GOLD + "Kit")) {
                if(kit){
                    kit = false;
                    ItemStack kitItem = new ItemStack(Material.GRAY_DYE);
                    ItemMeta kitItemMeta = kitItem.getItemMeta();
                    kitItemMeta.setDisplayName(ChatColor.GOLD + "Kit");
                    kitItem.setItemMeta(kitItemMeta);
                    event.getInventory().setItem(14, kitItem);
                }else {
                    kit = true;
                    ItemStack kitItem = new ItemStack(Material.LIME_DYE);
                    ItemMeta kitItemMeta = kitItem.getItemMeta();
                    kitItemMeta.setDisplayName(ChatColor.GOLD + "Kit");
                    kitItem.setItemMeta(kitItemMeta);
                    event.getInventory().setItem(14, kitItem);
                }
            }
            else if(clickedMeta.getDisplayName().equals(ChatColor.GOLD + "Dificuldade")){
                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.GREEN + "Botao esquerdo para Aumentar a dificuldade");
                lore.add(ChatColor.GREEN + "Botao direito para Diminuir a dificuldade");
                if(event.getClick().equals(ClickType.LEFT)){
                    if(difficulty < 10){
                        difficulty++;
                        ItemStack difficultyItem = new ItemStack(difficultyMaterials.get(difficulty - 1), difficulty);
                        ItemMeta difficultyItemMeta = difficultyItem.getItemMeta();
                        difficultyItemMeta.setDisplayName(ChatColor.GOLD + "Dificuldade");
                        difficultyItemMeta.setLore(lore);
                        difficultyItem.setItemMeta(difficultyItemMeta);
                        event.getInventory().setItem(16, difficultyItem);
                    } else if (difficulty == 10) {
                        difficulty++;
                        ItemStack difficultyItem = new ItemStack(Material.SUSPICIOUS_STEW);
                        ItemMeta difficultyItemMeta = difficultyItem.getItemMeta();
                        difficultyItemMeta.setDisplayName(ChatColor.GOLD + "Dificuldade");
                        lore.add("");
                        lore.add(ChatColor.LIGHT_PURPLE + "Dificuldade Aleatória.");
                        difficultyItemMeta.setLore(lore);
                        difficultyItem.setItemMeta(difficultyItemMeta);
                        event.getInventory().setItem(16, difficultyItem);
                    }

                } else if(event.getClick().equals(ClickType.RIGHT)){
                    if(difficulty > 1){
                        difficulty--;
                        ItemStack difficultyItem = new ItemStack(difficultyMaterials.get(difficulty - 1), difficulty);
                        ItemMeta difficultyItemMeta = difficultyItem.getItemMeta();
                        difficultyItemMeta.setDisplayName(ChatColor.GOLD + "Dificuldade");
                        difficultyItemMeta.setLore(lore);
                        difficultyItem.setItemMeta(difficultyItemMeta);
                        event.getInventory().setItem(16, difficultyItem);
                    }
                }
            } else if (clickedMeta.getDisplayName().equals(ChatColor.GOLD + "Ranked")) {
                if(ranked){
                    ranked = false;
                    ItemStack rankedItem = new ItemStack(Material.INK_SAC);
                    ItemMeta rankedItemMeta = rankedItem.getItemMeta();
                    rankedItemMeta.setDisplayName(ChatColor.GOLD + "Ranked");
                    rankedItemMeta.setLore(Collections.singletonList(ChatColor.RED + "Desativada"));
                    rankedItem.setItemMeta(rankedItemMeta);
                    event.getInventory().setItem(22, rankedItem);
                }else {
                    ranked = true;
                    ItemStack rankedItem = new ItemStack(Material.GLOW_INK_SAC);
                    ItemMeta rankedItemMeta = rankedItem.getItemMeta();
                    rankedItemMeta.setDisplayName(ChatColor.GOLD + "Ranked");
                    rankedItemMeta.setLore(Collections.singletonList(ChatColor.GREEN + "Ativada"));
                    rankedItem.setItemMeta(rankedItemMeta);
                    event.getInventory().setItem(22, rankedItem);
                }

            }
            return;
        }
        return;
    }

    @EventHandler
    public void openMenu(PlayerInteractEvent event){
        if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            if(event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.BOOK)){
                if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Menu")){
                    BingoMenu.openMenu(event.getPlayer());
                }
            }
        }
    }
}
