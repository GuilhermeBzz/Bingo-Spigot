package br.com.bingo.listener.quest;

import br.com.bingo.game.GameManager;
import br.com.bingo.quests.Quest;
import br.com.bingo.quests.QuestType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.inventory.ItemStack;


public class FullSetListener implements Listener {

    GameManager gameManager;

    public FullSetListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void playerChangeArmorStandEvent(PlayerArmorStandManipulateEvent event){
        if(!gameManager.isGameStarted()) return;
        Player player = event.getPlayer();
        if(!gameManager.checkPlayerTeam(player)) return;
        ArmorStand armorStand = event.getRightClicked();

        for(Quest quest : gameManager.getAvailableQuests()){
            if((quest.getType() != QuestType.FULL_SET)){continue;}
            if(!(quest.getTarget() instanceof Material)){continue;}
            Material material = (Material) quest.getTarget();
            if(isFullSet(armorStand, material, event.getPlayerItem())){
                gameManager.completeQuest(player.getUniqueId(), quest);
                return;
            }
        }
    }

    public boolean isFullSet(ArmorStand armorStand, Material material, ItemStack inHand){
        String type = "";
        if(material.equals(Material.IRON_INGOT)) type = "IRON";
        if(material.equals(Material.GOLD_INGOT)) type = "GOLDEN";
        if(material.equals(Material.DIAMOND)) type = "DIAMOND";
        if(material.equals(Material.CHAIN)) type = "CHAINMAIL";
        if(material.equals(Material.LEATHER)) type = "LEATHER";

        ItemStack helmet = armorStand.getEquipment().getHelmet();
        ItemStack chestplate = armorStand.getEquipment().getChestplate();
        ItemStack leggings = armorStand.getEquipment().getLeggings();
        ItemStack boots = armorStand.getEquipment().getBoots();

        if(helmet == null || chestplate == null || leggings == null || boots == null){return false;}

        if(helmet.getType().toString().contains(type)) Bukkit.getLogger().info("helmet");
        if(chestplate.getType().toString().contains(type)) Bukkit.getLogger().info("chestplate");
        if(leggings.getType().toString().contains(type)) Bukkit.getLogger().info("leggings");
        if(boots.getType().toString().contains(type)) Bukkit.getLogger().info("boots");

        boolean inHandMatches = inHand != null && inHand.getType().toString().contains(type);
        boolean helmetOk = false;
        boolean chestplateOk = false;
        boolean leggingsOk = false;
        boolean bootsOk = false;

        if(helmet.getType().toString().contains(type)) helmetOk = true;
        if(chestplate.getType().toString().contains(type)) chestplateOk = true;
        if(leggings.getType().toString().contains(type)) leggingsOk = true;
        if(boots.getType().toString().contains(type)) bootsOk = true;

        if(helmetOk && chestplateOk && leggingsOk && bootsOk) return true;

        if(helmetOk && chestplateOk && leggingsOk){
            if(inHandMatches && inHand.getType().toString().contains("BOOTS")) return true;
        }
        if(helmetOk && chestplateOk && bootsOk){
            if(inHandMatches && inHand.getType().toString().contains("LEGGINGS")) return true;
        }
        if(helmetOk && leggingsOk && bootsOk){
            if(inHandMatches && inHand.getType().toString().contains("CHESTPLATE")) return true;
        }
        if(chestplateOk && leggingsOk && bootsOk){
            if(inHandMatches && inHand.getType().toString().contains("HELMET")) return true;
        }

        return false;

    }
}
