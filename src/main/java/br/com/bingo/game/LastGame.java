package br.com.bingo.game;

import br.com.bingo.kits.KitType;
import br.com.bingo.quests.EntityHead;
import br.com.bingo.quests.Quest;
import br.com.bingo.quests.QuestInstance;
import br.com.bingo.rank.RankCalculator;
import br.com.bingo.team.TeamType;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.*;

public class LastGame {

    public GameType gameType;
    public Map<QuestInstance, UUID> playerQuests = new HashMap<>();
    public Map<QuestInstance, TeamType>   teamQuests = new HashMap<>();
    public Map<QuestInstance, Integer> questOrder = new HashMap<>();
    public Map<UUID, TeamType>  playerTeam = new HashMap<>();
    public Map<UUID, Integer> playerPoints = new HashMap<>();
    public Map<TeamType, Integer> teamPoints = new HashMap<>();
    public Map<UUID, KitType> playerKit = new HashMap<>();
    public Boolean kit;
    public TeamType teamWinner;
    public boolean ranked;

    public LastGame(GameType gameType, Map<QuestInstance, UUID> playerQuests, Map<QuestInstance, TeamType> teamQuests, Map<QuestInstance, Integer> questOrder, Map<UUID, TeamType>  playerTeam, Map<UUID, Integer> playerPoints, Map<TeamType, Integer> teamPoints, Map<UUID, KitType> playerKit, TeamType teamWinner, boolean ranked) {
        this.gameType = gameType;
        this.playerQuests = playerQuests;
        this.teamQuests = teamQuests;
        this.questOrder = questOrder;
        this.playerTeam = playerTeam;
        this.playerPoints = playerPoints;
        this.teamPoints = teamPoints;
        this.playerKit = playerKit;
        this.kit = playerKit != null;
        this.teamWinner = teamWinner;
        this.ranked = ranked;
        
        generateReport();
    }


    public void openLastGame(Player player) throws NoSuchFieldException, IllegalAccessException {
        UUID uuid = player.getUniqueId();
        Inventory bingoInventory = Bukkit.createInventory(null, 54, ChatColor.DARK_PURPLE + "Última Partida - " + gameType.toString());
        for(int i = 0; i<9; i++){
            bingoInventory.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
            if (i < 2 || i > 6){
                for(int j = 9; j < 54; j = j+9){
                    bingoInventory.setItem(i + j, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
                }
            }
        }

        ItemStack menu = new ItemStack(Material.WRITABLE_BOOK);
        ItemMeta menuMeta = menu.getItemMeta();
        menuMeta.setDisplayName(ChatColor.GREEN + "Placar");
        menu.setItemMeta(menuMeta);
        bingoInventory.setItem(8, menu);

        int slot = 11;
        for(QuestInstance questInstance : playerQuests.keySet()){
            Quest quest = questInstance.quest();
            ItemStack questItem = null;
            ChatColor questColor = null;

            if(quest.getIcon() instanceof Material){
                questItem = new ItemStack((Material) quest.getIcon());

                if(gameType.equals(GameType.SOLO)){
                    if(playerQuests.get(questInstance) == null){
                        questColor = ChatColor.GREEN;
                    }else if (playerQuests.get(questInstance) == uuid) {
                        questColor = ChatColor.GOLD;
                        questItem = new ItemStack(Material.LIME_STAINED_GLASS_PANE);

                    } else {
                        questColor = ChatColor.RED;
                        questItem = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                    }

                } else{
                    if(teamQuests.get(questInstance) == null){
                        questColor = ChatColor.GREEN;

                    } else if (teamQuests.get(questInstance) == playerTeam.get(uuid)) {
                        questColor = ChatColor.GOLD;
                        questItem = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                    } else {
                        questColor = ChatColor.RED;
                        questItem = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                    }
                }
                ItemMeta meta = questItem.getItemMeta();
                meta.setDisplayName(questColor + quest.getName());
                if(!questColor.equals(ChatColor.GREEN)){
                    List<String> lore = new ArrayList<>();
                    lore.add(questColor + "Concluida por " + Bukkit.getOfflinePlayer(playerQuests.get(questInstance)).getName());
                    lore.add(questColor + questOrder.get(questInstance).toString() + "ª Quest Concluida");
                    meta.setLore(lore);
                }
                questItem.setItemMeta(meta);

            } else if (quest.getIcon() instanceof EntityHead){
                UUID headUuid = UUID.fromString((String) ((EntityHead) quest.getIcon()).UUID);
                String texture = ((EntityHead) quest.getIcon()).texture;

                GameProfile profile = new GameProfile(headUuid, "pizza");
                profile.getProperties().put("textures", new Property("textures", texture));

                questItem = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta meta = (SkullMeta) questItem.getItemMeta();

                assert meta != null;
                try {
                    // Pega o campo correto para a versão 1.21.4
                    Field profileField = meta.getClass().getDeclaredField("profile");
                    profileField.setAccessible(true);

                    // Converte o GameProfile para o novo tipo ResolvableProfile
                    Object resolvableProfile = Class.forName("net.minecraft.world.item.component.ResolvableProfile")
                            .getConstructor(GameProfile.class)
                            .newInstance(profile);

                    profileField.set(meta, resolvableProfile); // Define o perfil corretamente
                } catch (Exception e) {
                    e.printStackTrace();
                }
                questItem.setItemMeta(meta);


                ItemMeta newMeta = null;

                if(gameType.equals(GameType.SOLO)){
                    if(playerQuests.get(questInstance) == null){
                        questColor = ChatColor.GREEN;

                    } else if (playerQuests.get(questInstance) == player.getUniqueId()) {
                        questColor = ChatColor.GOLD;
                        questItem = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                        newMeta = questItem.getItemMeta();
                    } else {
                        questColor = ChatColor.RED;
                        questItem = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                        newMeta = questItem.getItemMeta();
                    }

                }else{
                    if(teamQuests.get(questInstance) == null){
                        questColor = ChatColor.GREEN;

                    } else if (teamQuests.get(questInstance) == playerTeam.get(uuid)) {
                        questColor = ChatColor.GOLD;
                        questItem = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                        newMeta = questItem.getItemMeta();
                    } else {
                        questColor = ChatColor.RED;
                        questItem = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                        newMeta = questItem.getItemMeta();
                    }
                }

                if(newMeta == null){
                    meta.setDisplayName(questColor + quest.getName());
                    questItem.setItemMeta(meta);
                }else {
                    newMeta.setDisplayName(questColor + quest.getName());
                    List<String> lore = new ArrayList<>();
                    lore.add(questColor + "Concluida por " + Bukkit.getOfflinePlayer(playerQuests.get(questInstance)).getName());
                    lore.add(questColor + questOrder.get(questInstance).toString() + "ª Quest Concluida");
                    newMeta.setLore(lore);
                    questItem.setItemMeta(newMeta);
                }
            }
            bingoInventory.setItem(slot, questItem);
            slot++;
            if(slot == 16 || slot == 25 || slot == 34 || slot == 43) slot = slot+ 4;
        }

        player.openInventory(bingoInventory);
    }

    public void generateReport(){
        if(gameType.equals(GameType.SOLO)){
            Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "Partidas solo não são contabilizadas");
            return;
        }
        if(teamWinner == null){
            Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "A partida não foi contabilizada pois não terminou naturalmente");
            return;
        }
        if(!ranked){
            Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "A partida não foi contabilizada pois não é ranqueada");
            return;
        }

        // Calculate rank results using the new RankCalculator
        List<RankCalculator.RankResult> rankResults = RankCalculator.calculateRankResults(playerTeam, playerQuests, teamWinner);
        
        // Update player ranks
        RankCalculator.updatePlayerRanks(rankResults);

        // Display results
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "---------------- Saldo de Pontos ----------------");
        Bukkit.broadcastMessage("");
        
        for(RankCalculator.RankResult result : rankResults) {
            String status = result.isWinner ? ChatColor.GREEN + "Vencedor" : ChatColor.RED + "Perdedor";
            Bukkit.broadcastMessage(ChatColor.GOLD + result.playerName + ": " + status);
            Bukkit.broadcastMessage(ChatColor.GRAY + "Quests Faceis: " + ChatColor.GREEN + result.easyQuests);
            Bukkit.broadcastMessage(ChatColor.GRAY + "Quests Medias: " + ChatColor.GREEN + result.mediumQuests);
            Bukkit.broadcastMessage(ChatColor.GRAY + "Quests Dificeis: " + ChatColor.GREEN + result.hardQuests);
            Bukkit.broadcastMessage(ChatColor.GRAY + "Pontos: " + ChatColor.GREEN + result.points);
            Bukkit.broadcastMessage("");
        }
        
        Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "----------------------------------------");
    }
}

