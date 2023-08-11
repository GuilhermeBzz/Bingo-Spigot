package br.com.bingo.kits.listeners;

import br.com.bingo.game.GameManager;
import br.com.bingo.game.GameType;
import br.com.bingo.kits.KitType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class KitListener implements Listener {

    GameManager gameManager;

    public KitListener(GameManager gameManager){
        this.gameManager = gameManager;
    }


    @EventHandler
    public void onKitSelect(InventoryClickEvent event){
        if(!event.getView().getTitle().equals(ChatColor.DARK_PURPLE + "Kits")){return;}
        event.setCancelled(true);

        if(!gameManager.kit){
            event.getWhoClicked().sendMessage(ChatColor.RED + "Não é possível escolher um Kit agora");
            return;
        }


        if(gameManager.isGameStarted()){
            event.getWhoClicked().sendMessage(ChatColor.RED + "A partida ja começou! Você não pode selecionar um kit agora!");
            event.getWhoClicked().closeInventory();
            return;
        }

        ItemStack clickedItem = event.getCurrentItem();
        if(clickedItem == null || clickedItem.getType().equals(Material.AIR)){return;}
        ItemMeta clickedMeta = clickedItem.getItemMeta();
        String clickedName = clickedMeta.getDisplayName();
        clickedName = clickedName.replace(ChatColor.AQUA.toString(), "");


        List<KitType> allKits = new ArrayList<>();
        Collections.addAll(allKits, KitType.values());
        if(gameManager.getGameType().equals(GameType.SOLO) && (clickedName.equals(KitType.PAO.getName()) || clickedName.equals(KitType.SEDEX.getName()))){
            event.getWhoClicked().sendMessage(ChatColor.RED + "Esse Kit não está habilitado para a partida Solo!");
            return;
        }

        gameManager.playerKit.remove(event.getWhoClicked().getUniqueId());
        for(KitType kitType : allKits){
            if(kitType.getName().equals(clickedName)){
                gameManager.playerKit.put(event.getWhoClicked().getUniqueId(), kitType);
                event.getWhoClicked().sendMessage(ChatColor.GREEN + "Você selecionou o kit " + ChatColor.AQUA + kitType.getName());
                event.getWhoClicked().closeInventory();
                return;
            }
        }


    }
}
