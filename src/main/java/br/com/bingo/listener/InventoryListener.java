package br.com.bingo.listener;

import br.com.bingo.quests.QuestInstance;
import br.com.bingo.ui.BingoMenu;
import br.com.bingo.quests.EntityHead;
import br.com.bingo.game.GameManager;
import br.com.bingo.game.GameStatus;
import br.com.bingo.game.GameType;
import br.com.bingo.quests.Quest;
import com.google.common.collect.ImmutableMultimap;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import com.mojang.authlib.GameProfile;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InventoryListener implements Listener {
    private final GameManager gameManager;

    public InventoryListener(GameManager gameManager){
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) throws NoSuchFieldException, IllegalAccessException {
        if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            ItemStack item = event.getItem();
            Player player = event.getPlayer();
            if(item != null && item.getType() == Material.PAPER && item.hasItemMeta()){
                ItemMeta meta = item.getItemMeta();
                if(meta.getDisplayName().equals(ChatColor.GOLD + "Cartela do Bingo") && (gameManager.isGameStarted() || gameManager.getGameStatus().equals(GameStatus.FINISHED)) && gameManager.checkPlayerTeam(player)){
                    event.setCancelled(true);
                    openBingoInventory(player);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClickInOldGame(InventoryClickEvent event){
        InventoryView inventory = event.getView();
        if(inventory != null && inventory.getTitle().contains("Última Partida")){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        InventoryView inventory = event.getView();
        if (inventory != null && inventory.getTitle().equals(ChatColor.DARK_PURPLE + "Cartela do Bingo")) {
            event.setCancelled(true);
            if(event.getClickedInventory() == null) return;
            if(event.getCurrentItem() == null) return;
            if(event.getCurrentItem().hasItemMeta()){
                ItemMeta meta = event.getCurrentItem().getItemMeta();
                if(meta.getDisplayName().equals(ChatColor.GREEN + "Menu")){
                    event.getWhoClicked().closeInventory();
                    BingoMenu.openMenu((Player) event.getWhoClicked());
                }
            }
        }
    }

    public void openBingoInventory(Player player) throws NoSuchFieldException, IllegalAccessException {
        Inventory bingoInventory = Bukkit.createInventory(null, 54, ChatColor.DARK_PURPLE + "Cartela do Bingo");
        for(int i = 0; i<9; i++){
            bingoInventory.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
            if (i < 2 || i > 6){
                for(int j = 9; j < 54; j = j+9){
                    bingoInventory.setItem(i + j, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
                }
            }
        }
        ItemStack menu = new ItemStack(Material.BOOK);
        ItemMeta menuMeta = menu.getItemMeta();
        menuMeta.setDisplayName(ChatColor.GREEN + "Menu");
        menu.setItemMeta(menuMeta);
        bingoInventory.setItem(8, menu);
        int slot = 11;
        if(gameManager.getGameType() == GameType.SOLO){

            for(QuestInstance questInstance : gameManager.playerQuests.keySet()){
                Quest quest = questInstance.quest();
                ItemStack questItem = null;
                ChatColor questColor = null;



                if(quest.getIcon() instanceof Material){
                    questItem = new ItemStack((Material) quest.getIcon());



                    if(gameManager.playerQuests.get(questInstance) == null){
                        questColor = ChatColor.GREEN;

                    } else if (gameManager.playerQuests.get(questInstance) == player.getUniqueId()) {
                        questColor = ChatColor.GOLD;
                        questItem = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                        
                    } else {
                        questColor = ChatColor.RED;
                        questItem = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                    }
                    ItemMeta meta = questItem.getItemMeta();

                    meta.setDisplayName(questColor + quest.getName());
                    if(!questColor.equals(ChatColor.GREEN)){
                        List<String> lore = new ArrayList<>();
                        lore.add(questColor + "Concluida por " + Bukkit.getOfflinePlayer(gameManager.playerQuests.get(questInstance)).getName());
                        lore.add(questColor + gameManager.questOrder.get(questInstance).toString() + "ª Quest Concluida");
                        meta.setLore(lore);
                    }
                    questItem.setItemMeta(meta);
                }
                else if (quest.getIcon() instanceof EntityHead) {
                    UUID headUuid = UUID.fromString((String) ((EntityHead) quest.getIcon()).UUID);
                    String texture = ((EntityHead) quest.getIcon()).texture;

                    PropertyMap properties = new PropertyMap(ImmutableMultimap.of("textures", new Property("textures", texture)));
                    GameProfile profile = new GameProfile(headUuid, "pizza", properties);

                    questItem = new ItemStack(Material.PLAYER_HEAD);
                    SkullMeta meta = (SkullMeta) questItem.getItemMeta();

                    assert meta != null;
                    try {
                        // Pega o campo correto para a versão 1.21.4
                        Field profileField = meta.getClass().getDeclaredField("profile");
                        profileField.setAccessible(true);

                        // Converte o GameProfile para o novo tipo ResolvableProfile
                        Object resolvableProfile = Class.forName("net.minecraft.world.item.component.ResolvableProfile")
                                .getMethod("createResolved", GameProfile.class)
                                .invoke(null, profile);

                        profileField.set(meta, resolvableProfile); // Define o perfil corretamente
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    questItem.setItemMeta(meta);


                    ItemMeta newMeta = null;
                    if(gameManager.playerQuests.get(questInstance) == null){
                        questColor = ChatColor.GREEN;

                    } else if (gameManager.playerQuests.get(questInstance) == player.getUniqueId()) {
                        questColor = ChatColor.GOLD;
                        questItem = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                        newMeta = questItem.getItemMeta();
                    } else {
                        questColor = ChatColor.RED;
                        questItem = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                        newMeta = questItem.getItemMeta();
                    }
                    if(newMeta == null){
                        meta.setDisplayName(questColor + quest.getName());
                        questItem.setItemMeta(meta);
                    }else {
                        newMeta.setDisplayName(questColor + quest.getName());
                        List<String> lore = new ArrayList<>();
                        lore.add(questColor + "Concluida por " + Bukkit.getOfflinePlayer(gameManager.playerQuests.get(questInstance)).getName());
                        lore.add(questColor + gameManager.questOrder.get(questInstance).toString() + "ª Quest Concluida");
                        newMeta.setLore(lore);
                        questItem.setItemMeta(newMeta);
                    }


                }

                bingoInventory.setItem(slot, questItem);
                slot++;
                if(slot == 16 || slot == 25 || slot == 34 || slot == 43) slot = slot+ 4;
            }
        }
        else if (gameManager.getGameType() == GameType.TEAM_AUTO || gameManager.getGameType() == GameType.TEAM_MANUAL) {
            for(QuestInstance questInstance : gameManager.teamQuests.keySet()){
                Quest quest = questInstance.quest();
                ItemStack questItem = null;
                ChatColor questColor = null;

                if(quest.getIcon() instanceof Material){
                    questItem = new ItemStack((Material) quest.getIcon());



                    if(gameManager.teamQuests.get(questInstance) == null){
                        questColor = ChatColor.GREEN;

                    } else if (gameManager.teamQuests.get(questInstance) == gameManager.getPlayerTeam(player)) {
                        questColor = ChatColor.GOLD;
                        questItem = new ItemStack(Material.LIME_STAINED_GLASS_PANE);


                    } else {
                        questColor = ChatColor.RED;
                        questItem = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                    }
                    ItemMeta meta = questItem.getItemMeta();

                    meta.setDisplayName(questColor + quest.getName());
                    if(!questColor.equals(ChatColor.GREEN)){
                        List<String> lore = new ArrayList<>();
                        lore.add(questColor + "Concluida por " + Bukkit.getOfflinePlayer(gameManager.playerQuests.get(questInstance)).getName());
                        lore.add(questColor + gameManager.questOrder.get(questInstance).toString() + "ª Quest Concluida");
                        meta.setLore(lore);
                    }
                    questItem.setItemMeta(meta);
                } else if (quest.getIcon() instanceof EntityHead) {
                    UUID headUuid = UUID.fromString((String) ((EntityHead) quest.getIcon()).UUID);
                    String texture = ((EntityHead) quest.getIcon()).texture;

                    PropertyMap properties = new PropertyMap(ImmutableMultimap.of("textures", new Property("textures", texture)));
                    GameProfile profile = new GameProfile(headUuid, "pizza", properties);

                    questItem = new ItemStack(Material.PLAYER_HEAD);
                    SkullMeta meta = (SkullMeta) questItem.getItemMeta();

                    assert meta != null;
                    try {
                        // Pega o campo correto para a versão 1.21.4
                        Field profileField = meta.getClass().getDeclaredField("profile");
                        profileField.setAccessible(true);

                        // Converte o GameProfile para o novo tipo ResolvableProfile
                        Object resolvableProfile = Class.forName("net.minecraft.world.item.component.ResolvableProfile")
                                .getMethod("createResolved", GameProfile.class)
                                .invoke(null, profile);

                        profileField.set(meta, resolvableProfile); // Define o perfil corretamente
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    questItem.setItemMeta(meta);


                    ItemMeta newMeta = null;
                    if(gameManager.teamQuests.get(questInstance) == null){
                        questColor = ChatColor.GREEN;

                    } else if (gameManager.teamQuests.get(questInstance) == gameManager.getPlayerTeam(player)) {
                        questColor = ChatColor.GOLD;
                        questItem = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                        newMeta = questItem.getItemMeta();
                    } else {
                        questColor = ChatColor.RED;
                        questItem = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                        newMeta = questItem.getItemMeta();
                    }
                    if(newMeta == null){
                        meta.setDisplayName(questColor + quest.getName());
                        questItem.setItemMeta(meta);
                    }else {
                        newMeta.setDisplayName(questColor + quest.getName());
                        List<String> lore = new ArrayList<>();
                        UUID uuid = gameManager.playerQuests.get(questInstance);
                        lore.add(questColor + "Concluida por " + Bukkit.getOfflinePlayer(gameManager.playerQuests.get(questInstance)).getName());
                        lore.add(questColor + gameManager.questOrder.get(questInstance).toString() + "ª Quest Concluida");
                        newMeta.setLore(lore);
                        questItem.setItemMeta(newMeta);
                    }


                }


                bingoInventory.setItem(slot, questItem);
                slot++;
                if(slot == 16 || slot == 25 || slot == 34 || slot == 43) slot = slot+ 4;

            }
        }

        player.openInventory(bingoInventory);
    }
}
