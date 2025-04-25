package br.com.bingo.listener;

import br.com.bingo.quests.EntityHead;
import br.com.bingo.quests.Quest;
import br.com.bingo.ui.BingoMenu;
import br.com.bingo.ChangeLog;
import br.com.bingo.game.GameManager;
import br.com.bingo.game.GameType;
import br.com.bingo.kits.KitManager;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
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
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.*;

public class MenuListener implements Listener {

    GameManager gameManager;
    Boolean kit;
    GameType gameType;
    int difficulty;

    Boolean ranked;
    int questLeftWhenChange;
    int specialQuests;

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

        ItemStack confirm = new ItemStack(Material.GRAY_CONCRETE);
        ItemMeta confirmMeta = confirm.getItemMeta();
        confirmMeta.setDisplayName(ChatColor.GOLD + "Confirmar");
        confirmMeta.setLore(getErrorMsgs());
        confirm.setItemMeta(confirmMeta);

        ItemStack cancel = new ItemStack(Material.RED_CONCRETE);
        ItemMeta cancelMeta = cancel.getItemMeta();
        cancelMeta.setDisplayName(ChatColor.GOLD + "Cancelar");
        cancel.setItemMeta(cancelMeta);

        ItemStack kit = new ItemStack(Material.LIME_DYE);
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

        UUID headUuid = UUID.fromString((String) ((EntityHead) Quest.QUESTION.getIcon()).UUID);
        String texture = ((EntityHead) Quest.QUESTION.getIcon()).texture;
        GameProfile profile = new GameProfile(headUuid, "pizza");
        profile.getProperties().put("textures", new Property("textures", texture));
        ItemStack specialQuests = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) specialQuests.getItemMeta();
        assert meta != null;
        try{
            Field profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);

            Object resolvableProfile = Class.forName("net.minecraft.world.item.component.ResolvableProfile")
                    .getConstructor(GameProfile.class)
                    .newInstance(profile);
            profileField.set(meta, resolvableProfile);
        } catch (Exception e){
            e.printStackTrace();
        }
        meta.setDisplayName(ChatColor.GOLD + "Quests Especiais");
        specialQuests.setItemMeta(meta);

        meta.setDisplayName(ChatColor.GOLD + "Quests Especiais");
        List<String> specialLore = new ArrayList<>();
        specialLore.add(ChatColor.GREEN + "Botao esquerdo para Aumentar a quantidade de quests especiais");
        specialLore.add(ChatColor.GREEN + "Botao direito para Diminuir a quantidade de quests especiais");
        meta.setLore(specialLore);
        specialQuests.setItemMeta(meta);
        specialQuests.setAmount(1);

        ItemStack questLeftWhenChange = new ItemStack(Material.BLAZE_POWDER);
        ItemMeta questLeftWhenChangeMeta = questLeftWhenChange.getItemMeta();
        questLeftWhenChangeMeta.setDisplayName(ChatColor.GOLD + "Quests Restantes para a Fase 2");
        List<String> questLeftLore = new ArrayList<>();
        questLeftLore.add(ChatColor.GREEN + "Botao esquerdo para Aumentar a quantidade de quests restantes");
        questLeftLore.add(ChatColor.GREEN + "Botao direito para Diminuir a quantidade de quests restantes");
        questLeftLore.add(ChatColor.LIGHT_PURPLE + "" +
                        ChatColor.ITALIC +  "Quantidade de Quests restantes no momento em que muda a fase");
        questLeftLore.add(ChatColor.LIGHT_PURPLE + "" + ChatColor.ITALIC + " (quanto maior, mais cedo)");
        questLeftWhenChangeMeta.setLore(questLeftLore);
        questLeftWhenChange.setItemMeta(questLeftWhenChangeMeta);
        questLeftWhenChange.setAmount(13);

        ItemStack ranked = new ItemStack(Material.INK_SAC);
        ItemMeta rankedMeta = ranked.getItemMeta();
        rankedMeta.setDisplayName(ChatColor.GOLD + "Ranked");
        rankedMeta.setLore(Collections.singletonList(ChatColor.RED + "Desativada"));
        ranked.setItemMeta(rankedMeta);

        Inventory creator = Bukkit.createInventory(null, 6 * 9, ChatColor.DARK_RED + "Criar Partida");

        creator.setItem(12, teamAuto);
        creator.setItem(13, teamManual);
        creator.setItem(14, teamSolo);
        creator.setItem(31, kit);
        creator.setItem(32, difficulty);
        creator.setItem(50, confirm);
        creator.setItem(29, specialQuests);
        creator.setItem(33, questLeftWhenChange);
        creator.setItem(30, ranked);
        creator.setItem(48, cancel);

        this.kit = true;
        this.gameType = null;
        this.difficulty = 5;
        this.ranked = false;
        this.questLeftWhenChange = 13;
        this.specialQuests = 1;
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
            int clickedSlot = event.getSlot();


            if (clickedMeta.getDisplayName().equals(ChatColor.GOLD + "Equipes Automaticas")) {
                gameType = GameType.TEAM_AUTO;
                ItemStack teamAuto = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                ItemMeta teamAutoMeta = teamAuto.getItemMeta();
                teamAutoMeta.setDisplayName(ChatColor.GOLD + "Equipes Automaticas");
                teamAuto.setItemMeta(teamAutoMeta);
                event.getInventory().setItem(12, teamAuto);

                ItemStack others = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                ItemMeta othersMeta = others.getItemMeta();

                othersMeta.setDisplayName(ChatColor.GOLD + "Equipes Manuais");
                others.setItemMeta(othersMeta);
                event.getInventory().setItem(13, others);

                othersMeta.setDisplayName(ChatColor.GOLD + "Solo");
                others.setItemMeta(othersMeta);
                event.getInventory().setItem(14, others);
            }
            else if (clickedMeta.getDisplayName().equals(ChatColor.GOLD + "Equipes Manuais")) {
                gameType = GameType.TEAM_MANUAL;
                ItemStack teamManual = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                ItemMeta teamManualMeta = teamManual.getItemMeta();
                teamManualMeta.setDisplayName(ChatColor.GOLD + "Equipes Manuais");
                teamManual.setItemMeta(teamManualMeta);
                event.getInventory().setItem(13, teamManual);

                ItemStack others = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                ItemMeta othersMeta = others.getItemMeta();
                othersMeta.setDisplayName(ChatColor.GOLD + "Equipes Automaticas");
                others.setItemMeta(othersMeta);
                event.getInventory().setItem(12, others);

                othersMeta.setDisplayName(ChatColor.GOLD + "Solo");
                others.setItemMeta(othersMeta);
                event.getInventory().setItem(14, others);
            }
            else if (clickedMeta.getDisplayName().equals(ChatColor.GOLD + "Solo")) {
                gameType = GameType.SOLO;
                ItemStack solo = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                ItemMeta soloMeta = solo.getItemMeta();
                soloMeta.setDisplayName(ChatColor.GOLD + "Solo");
                solo.setItemMeta(soloMeta);
                event.getInventory().setItem(14, solo);

                ItemStack others = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                ItemMeta othersMeta = others.getItemMeta();
                othersMeta.setDisplayName(ChatColor.GOLD + "Equipes Automaticas");
                others.setItemMeta(othersMeta);
                event.getInventory().setItem(12, others);
                othersMeta.setDisplayName(ChatColor.GOLD + "Equipes Manuais");
                others.setItemMeta(othersMeta);
                event.getInventory().setItem(13, others);

            }
            else if (clickedMeta.getDisplayName().equals(ChatColor.GOLD + "Confirmar")) {
                if(!clickedItem.getType().equals(Material.LIME_CONCRETE)) return;
                if(gameType == null){
                    return;
                } else{
                    event.getWhoClicked().closeInventory();
                    gameManager.createCommand((Player) event.getWhoClicked(), gameType, kit, difficulty, ranked, specialQuests, questLeftWhenChange);
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
                    event.getInventory().setItem(31, kitItem);
                }else {
                    kit = true;
                    ItemStack kitItem = new ItemStack(Material.LIME_DYE);
                    ItemMeta kitItemMeta = kitItem.getItemMeta();
                    kitItemMeta.setDisplayName(ChatColor.GOLD + "Kit");
                    kitItem.setItemMeta(kitItemMeta);
                    event.getInventory().setItem(31, kitItem);
                }
            }
            else if(clickedMeta.getDisplayName().equals(ChatColor.GOLD + "Quests Especiais")){
                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.GREEN + "Botao esquerdo para Aumentar a quantidade de quests especiais");
                lore.add(ChatColor.GREEN + "Botao direito para Diminuir a quantidade de quests especiais");
                if(event.getClick().equals(ClickType.LEFT)){
                    if(specialQuests < 25){
                        specialQuests++;
                        UUID headUuid = UUID.fromString((String) ((EntityHead) Quest.QUESTION.getIcon()).UUID);
                        String texture = ((EntityHead) Quest.QUESTION.getIcon()).texture;
                        GameProfile profile = new GameProfile(headUuid, "pizza");
                        profile.getProperties().put("textures", new Property("textures", texture));
                        ItemStack specialItem = new ItemStack(Material.PLAYER_HEAD);
                        SkullMeta meta = (SkullMeta) specialItem.getItemMeta();
                        assert meta != null;
                        try{
                            Field profileField = meta.getClass().getDeclaredField("profile");
                            profileField.setAccessible(true);

                            Object resolvableProfile = Class.forName("net.minecraft.world.item.component.ResolvableProfile")
                                    .getConstructor(GameProfile.class)
                                    .newInstance(profile);
                            profileField.set(meta, resolvableProfile);
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                        meta.setDisplayName(ChatColor.GOLD + "Quests Especiais");
                        specialItem.setItemMeta(meta);

                        meta.setDisplayName(ChatColor.GOLD + "Quests Especiais");
                        List<String> specialLore = new ArrayList<>();
                        specialLore.add(ChatColor.GREEN + "Botao esquerdo para Aumentar a quantidade de quests especiais");
                        specialLore.add(ChatColor.GREEN + "Botao direito para Diminuir a quantidade de quests especiais");
                        meta.setLore(specialLore);
                        specialItem.setItemMeta(meta);
                        specialItem.setAmount(specialQuests);
                        event.getInventory().setItem(29, specialItem);
                    }
                } else if(event.getClick().equals(ClickType.RIGHT)){
                    if(specialQuests > 1){
                        specialQuests--;
                        UUID headUuid = UUID.fromString((String) ((EntityHead) Quest.QUESTION.getIcon()).UUID);
                        String texture = ((EntityHead) Quest.QUESTION.getIcon()).texture;
                        GameProfile profile = new GameProfile(headUuid, "pizza");
                        profile.getProperties().put("textures", new Property("textures", texture));
                        ItemStack specialItem = new ItemStack(Material.PLAYER_HEAD);
                        SkullMeta meta = (SkullMeta) specialItem.getItemMeta();
                        assert meta != null;
                        try{
                            Field profileField = meta.getClass().getDeclaredField("profile");
                            profileField.setAccessible(true);

                            Object resolvableProfile = Class.forName("net.minecraft.world.item.component.ResolvableProfile")
                                    .getConstructor(GameProfile.class)
                                    .newInstance(profile);
                            profileField.set(meta, resolvableProfile);
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                        meta.setDisplayName(ChatColor.GOLD + "Quests Especiais");
                        specialItem.setItemMeta(meta);

                        meta.setDisplayName(ChatColor.GOLD + "Quests Especiais");
                        List<String> specialLore = new ArrayList<>();
                        specialLore.add(ChatColor.GREEN + "Botao esquerdo para Aumentar a quantidade de quests especiais");
                        specialLore.add(ChatColor.GREEN + "Botao direito para Diminuir a quantidade de quests especiais");
                        meta.setLore(specialLore);
                        specialItem.setItemMeta(meta);
                        specialItem.setAmount(specialQuests);
                        event.getInventory().setItem(29, specialItem);
                    }
                    if(specialQuests == 1){
                        specialQuests--;
                        ItemStack specialQuestItem = new ItemStack(Material.LIGHT_GRAY_CONCRETE);
                        ItemMeta specialQuestItemMeta = specialQuestItem.getItemMeta();
                        specialQuestItemMeta.setDisplayName(ChatColor.GOLD + "Quests Especiais");
                        specialQuestItemMeta.setLore(lore);
                        specialQuestItem.setItemMeta(specialQuestItemMeta);
                        event.getInventory().setItem(29, specialQuestItem);
                    }
                }
            } else if(clickedMeta.getDisplayName().equals(ChatColor.GOLD + "Quests Restantes para a Fase 2")){
                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.GREEN + "Botao esquerdo para Aumentar a quantidade de quests restantes");
                lore.add(ChatColor.GREEN + "Botao direito para Diminuir a quantidade de quests restantes");
                lore.add(ChatColor.LIGHT_PURPLE + "" +
                        ChatColor.ITALIC +  "Quantidade de Quests restantes no momento em que muda a fase");
                lore.add(ChatColor.LIGHT_PURPLE + "" + ChatColor.ITALIC + " (quanto maior, mais cedo)");
                if(event.getClick().equals(ClickType.LEFT)){
                    if(questLeftWhenChange < 25){
                        questLeftWhenChange++;
                        ItemStack questLeftWhenChangeItem = new ItemStack(Material.BLAZE_POWDER, questLeftWhenChange);
                        ItemMeta questLeftWhenChangeMeta = questLeftWhenChangeItem.getItemMeta();
                        questLeftWhenChangeMeta.setDisplayName(ChatColor.GOLD + "Quests Restantes para a Fase 2");
                        questLeftWhenChangeMeta.setLore(lore);
                        questLeftWhenChangeItem.setItemMeta(questLeftWhenChangeMeta);
                        questLeftWhenChangeItem.setAmount(questLeftWhenChange);
                        event.getInventory().setItem(33, questLeftWhenChangeItem);
                    }
                }
                else if(event.getClick().equals(ClickType.RIGHT)){
                    if(questLeftWhenChange > 1){
                        questLeftWhenChange--;
                        ItemStack questLeftWhenChangeItem = new ItemStack(Material.BLAZE_POWDER, questLeftWhenChange);
                        ItemMeta questLeftWhenChangeMeta = questLeftWhenChangeItem.getItemMeta();
                        questLeftWhenChangeMeta.setDisplayName(ChatColor.GOLD + "Quests Restantes para a Fase 2");
                        questLeftWhenChangeMeta.setLore(lore);
                        questLeftWhenChangeItem.setItemMeta(questLeftWhenChangeMeta);
                        questLeftWhenChangeItem.setAmount(questLeftWhenChange);
                        event.getInventory().setItem(33, questLeftWhenChangeItem);
                    }
                    if(questLeftWhenChange == 1){
                        questLeftWhenChange--;
                        ItemStack questLeftWhenChangeItem = new ItemStack(Material.LIGHT_GRAY_CONCRETE);
                        ItemMeta questLeftWhenChangeMeta = questLeftWhenChangeItem.getItemMeta();
                        questLeftWhenChangeMeta.setDisplayName(ChatColor.GOLD + "Quests Restantes para a Fase 2");
                        questLeftWhenChangeMeta.setLore(lore);
                        questLeftWhenChangeItem.setItemMeta(questLeftWhenChangeMeta);
                        questLeftWhenChangeItem.setAmount(1);
                        event.getInventory().setItem(33, questLeftWhenChangeItem);
                    }
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
                        event.getInventory().setItem(32, difficultyItem);
                    } else if (difficulty == 10) {
                        difficulty++;
                        ItemStack difficultyItem = new ItemStack(Material.SUSPICIOUS_STEW);
                        ItemMeta difficultyItemMeta = difficultyItem.getItemMeta();
                        difficultyItemMeta.setDisplayName(ChatColor.GOLD + "Dificuldade");
                        lore.add("");
                        lore.add(ChatColor.LIGHT_PURPLE + "Dificuldade Aleatória.");
                        difficultyItemMeta.setLore(lore);
                        difficultyItem.setItemMeta(difficultyItemMeta);
                        event.getInventory().setItem(32, difficultyItem);
                    }

                } else if(event.getClick().equals(ClickType.RIGHT)){
                    if(difficulty > 1){
                        difficulty--;
                        ItemStack difficultyItem = new ItemStack(difficultyMaterials.get(difficulty - 1), difficulty);
                        ItemMeta difficultyItemMeta = difficultyItem.getItemMeta();
                        difficultyItemMeta.setDisplayName(ChatColor.GOLD + "Dificuldade");
                        difficultyItemMeta.setLore(lore);
                        difficultyItem.setItemMeta(difficultyItemMeta);
                        event.getInventory().setItem(32, difficultyItem);
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
                    event.getInventory().setItem(30, rankedItem);
                }else {
                    ranked = true;
                    ItemStack rankedItem = new ItemStack(Material.GLOW_INK_SAC);
                    ItemMeta rankedItemMeta = rankedItem.getItemMeta();
                    rankedItemMeta.setDisplayName(ChatColor.GOLD + "Ranked");
                    rankedItemMeta.setLore(Collections.singletonList(ChatColor.GREEN + "Ativada"));
                    rankedItem.setItemMeta(rankedItemMeta);
                    event.getInventory().setItem(30, rankedItem);
                }

            }

            List<String> errors = getErrorMsgs();

            if(errors.isEmpty()){
                ItemStack confirm = new ItemStack(Material.LIME_CONCRETE);
                ItemMeta confirmMeta = confirm.getItemMeta();
                confirmMeta.setDisplayName(ChatColor.GOLD + "Confirmar");
                confirm.setItemMeta(confirmMeta);
                event.getInventory().setItem(50, confirm);
            } else{
                ItemStack confirm = new ItemStack(Material.GRAY_CONCRETE);
                ItemMeta confirmMeta = confirm.getItemMeta();
                confirmMeta.setDisplayName(ChatColor.GOLD + "Confirmar - Indisponível");
                confirmMeta.setLore(errors);
                confirm.setItemMeta(confirmMeta);
                event.getInventory().setItem(50, confirm);
            }
            return;
        }
        return;
    }

    private List<String> getErrorMsgs(){
        List<String> errors = new ArrayList<>();

        if(gameType == null){
            errors.add(ChatColor.RED + "Selecione um tipo de jogo");
        }

        if(specialQuests > questLeftWhenChange && specialQuests != 25){
            errors.add(ChatColor.RED + "A partida não pode ter mais Quests Especiais do que Quests Restantes para Fase 2");
        }

        if(specialQuests > Arrays.stream(Quest.values()).filter(quest-> quest.getDifficulty() == 4).count()){
            errors.add(ChatColor.RED + "Ainda não existem tantas Quests Especiais");
        }

        return  errors;
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
