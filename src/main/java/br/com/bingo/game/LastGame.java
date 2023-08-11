package br.com.bingo.game;

import br.com.bingo.EntityHead;
import br.com.bingo.kits.KitType;
import br.com.bingo.quests.Quest;
import br.com.bingo.rank.models.match.MatchesData;
import br.com.bingo.rank.models.match.PlayersFromMatch;
import br.com.bingo.rank.models.match.QuestsFromMatch;
import br.com.bingo.rank.models.players.PlayersData;
import br.com.bingo.rank.models.quests.QuestsData;
import br.com.bingo.rank.utils.match.MatchesStorageUtil;
import br.com.bingo.rank.utils.players.PlayersStorageUtil;
import br.com.bingo.rank.utils.quests.QuestsStorageUtil;
import br.com.bingo.team.TeamType;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class LastGame {

    public GameType gameType;
    public Map<Quest, UUID> playerQuests = new HashMap<>();
    public Map<Quest, TeamType>   teamQuests = new HashMap<>();
    public Map<Quest, Integer> questOrder = new HashMap<>();
    public Map<UUID, TeamType>  playerTeam = new HashMap<>();
    public Map<UUID, Integer> playerPoints = new HashMap<>();
    public Map<TeamType, Integer> teamPoints = new HashMap<>();
    public Map<UUID, KitType> playerKit = new HashMap<>();
    public Boolean kit;
    public TeamType teamWinner;
    public boolean ranked;

    public LastGame(GameType gameType, Map<Quest, UUID> playerQuests, Map<Quest, TeamType> teamQuests, Map<Quest, Integer> questOrder, Map<UUID, TeamType>  playerTeam, Map<UUID, Integer> playerPoints, Map<TeamType, Integer> teamPoints, Map<UUID, KitType> playerKit, TeamType teamWinner, boolean ranked) {
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
        for(Quest quest : playerQuests.keySet()){
            ItemStack questItem = null;
            ChatColor questColor = null;

            if(quest.getIcon() instanceof Material){
                questItem = new ItemStack((Material) quest.getIcon());

                if(gameType.equals(GameType.SOLO)){
                    if(playerQuests.get(quest) == null){
                        questColor = ChatColor.GREEN;
                    }else if (playerQuests.get(quest) == uuid) {
                        questColor = ChatColor.GOLD;
                        questItem = new ItemStack(Material.LIME_STAINED_GLASS_PANE);

                    } else {
                        questColor = ChatColor.RED;
                        questItem = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                    }

                } else{
                    if(teamQuests.get(quest) == null){
                        questColor = ChatColor.GREEN;

                    } else if (teamQuests.get(quest) == playerTeam.get(uuid)) {
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
                    lore.add(questColor + "Concluida por " + Bukkit.getOfflinePlayer(playerQuests.get(quest)).getName());
                    lore.add(questColor + questOrder.get(quest).toString() + "ª Quest Concluida");
                    meta.setLore(lore);
                    questItem.setItemMeta(meta);
                }

            } else if (quest.getIcon() instanceof EntityHead){
                UUID headUuid = UUID.fromString((String) ((EntityHead) quest.getIcon()).UUID);

                String texture = ((EntityHead) quest.getIcon()).texture;
                GameProfile profile = new GameProfile(headUuid, "pizza");
                profile.getProperties().put("textures", new Property("textures", texture ));
                Field profileField;

                questItem = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta meta = (SkullMeta) questItem.getItemMeta();

                assert meta != null;
                profileField = meta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(meta, profile);
                ItemMeta newMeta = null;

                if(gameType.equals(GameType.SOLO)){
                    if(playerQuests.get(quest) == null){
                        questColor = ChatColor.GREEN;

                    } else if (playerQuests.get(quest) == player.getUniqueId()) {
                        questColor = ChatColor.GOLD;
                        questItem = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                        newMeta = questItem.getItemMeta();
                    } else {
                        questColor = ChatColor.RED;
                        questItem = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                        newMeta = questItem.getItemMeta();
                    }

                }else{
                    if(teamQuests.get(quest) == null){
                        questColor = ChatColor.GREEN;

                    } else if (teamQuests.get(quest) == playerTeam.get(uuid)) {
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
                    lore.add(questColor + "Concluida por " + Bukkit.getOfflinePlayer(playerQuests.get(quest)).getName());
                    lore.add(questColor + questOrder.get(quest).toString() + "ª Quest Concluida");
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
        ArrayList<QuestsFromMatch> questsFromMatches = new ArrayList<>();
        Map<UUID, Integer> easyQuestCompleted = new HashMap<>();
        Map<UUID, Integer> mediumQuestCompleted = new HashMap<>();
        Map<UUID, Integer> hardQuestCompleted = new HashMap<>();
        ArrayList<QuestsData> questsData = new ArrayList<>();
        for(Quest quest : playerQuests.keySet()){
            if(playerQuests.get(quest) != null){
                questsData.add(new QuestsData(quest.getName(), quest.getDifficulty(), 1, 0));
                questsFromMatches.add(new QuestsFromMatch(quest.getName(), quest.getDifficulty(), playerQuests.get(quest)));
                switch (quest.getDifficulty()){
                    case 1:
                        if(easyQuestCompleted.containsKey(playerQuests.get(quest))){
                            easyQuestCompleted.put(playerQuests.get(quest), easyQuestCompleted.get(playerQuests.get(quest)) + 1);
                        }else{
                            easyQuestCompleted.put(playerQuests.get(quest), 1);
                        }
                        break;
                    case 2:
                        if(mediumQuestCompleted.containsKey(playerQuests.get(quest))){
                            mediumQuestCompleted.put(playerQuests.get(quest), mediumQuestCompleted.get(playerQuests.get(quest)) + 1);
                        }else{
                            mediumQuestCompleted.put(playerQuests.get(quest), 1);
                        }
                        break;
                    case 3:
                        if(hardQuestCompleted.containsKey(playerQuests.get(quest))){
                            hardQuestCompleted.put(playerQuests.get(quest), hardQuestCompleted.get(playerQuests.get(quest)) + 1);
                        }else{
                            hardQuestCompleted.put(playerQuests.get(quest), 1);
                        }
                        break;
                }
            }else{
                questsData.add(new QuestsData(quest.getName(), quest.getDifficulty(), 0, 1));
                questsFromMatches.add(new QuestsFromMatch(quest.getName(), quest.getDifficulty(), null));
            }
        }
        ArrayList<PlayersData> playersData = new ArrayList<>();
        ArrayList<PlayersFromMatch> playersFromMatches = new ArrayList<>();
        for(UUID uuid : playerTeam.keySet()){
            int easy = 0;
            int medium = 0;
            int hard = 0;
            if(easyQuestCompleted.containsKey(uuid)){
                easy = easyQuestCompleted.get(uuid);
            }
            if(mediumQuestCompleted.containsKey(uuid)){
                medium = mediumQuestCompleted.get(uuid);
            }
            if(hardQuestCompleted.containsKey(uuid)){
                hard = hardQuestCompleted.get(uuid);
            }
            boolean winner = playerTeam.get(uuid) == teamWinner;
            playersFromMatches.add(new PlayersFromMatch(uuid, Bukkit.getOfflinePlayer(uuid).getName(),winner, easy, medium, hard));
            if(winner){
                playersData.add(new PlayersData(uuid, 1,1, easy, medium, hard));
            }else{
                playersData.add(new PlayersData(uuid, 0,1, easy, medium, hard));
            }
        }

        String matchId = UUID.randomUUID().toString();
        MatchesData matchesData = new MatchesData(matchId, questsFromMatches, playersFromMatches);
        //playersData
        //questsData

        //atualizar o playersData e o questData com os dados novos
        for(PlayersData playerData : playersData){
            if(PlayersStorageUtil.checkPlayer(playerData)){
                PlayersStorageUtil.updatePlayer(playerData);
                continue;
            }
            PlayersStorageUtil.createPlayer(playerData);
        }
        for(QuestsData questData : questsData){
            if(QuestsStorageUtil.checkQuest(questData)){
                QuestsStorageUtil.updateQuest(questData);
                continue;
            }
            QuestsStorageUtil.addQuest(questData);
        }
        MatchesStorageUtil.addMatch(matchesData);


        try {
            MatchesStorageUtil.saveMatches();
            PlayersStorageUtil.savePlayers();
            QuestsStorageUtil.saveQuests();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

