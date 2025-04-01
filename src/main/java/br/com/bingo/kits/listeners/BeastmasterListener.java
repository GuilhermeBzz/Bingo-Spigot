package br.com.bingo.kits.listeners;

import br.com.bingo.game.GameManager;
import br.com.bingo.kits.Kit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class BeastmasterListener implements Listener {

    GameManager gameManager;
    Map<UUID, Long> cooldowns = new HashMap<>();

    public BeastmasterListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getItem() == null) return;
        if (!event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Egg Generator")) return;

        if(!cooldowns.containsKey(player.getUniqueId())){
            cooldowns.put(player.getUniqueId(), 0L);
        }
        long currentTime = System.currentTimeMillis();
        long lastUsedTime = cooldowns.getOrDefault(player.getUniqueId(), 0L);
        int amount = 1;
        long cooldown = 5*60*1000;
        if(gameManager.getAvailableQuests().size() <= gameManager.questLeftWhenChange){
            amount = 2;
            cooldown = 5*30*1000;
        }

        if(currentTime - lastUsedTime < cooldown){
            player.sendMessage(ChatColor.RED + "Aguarde " + ((cooldown/1000)-(((currentTime - lastUsedTime)) / 1000)) + " segundos para usar novamente");
            return;
        }
        cooldowns.put(player.getUniqueId(), currentTime);

        giveEgg(player, amount);

        player.sendMessage(ChatColor.DARK_RED + "Você gerou um novo Spawner!");
    }

    public void giveEgg(Player player, Integer amount){
        ArrayList<ItemStack> eggs = new ArrayList<>();
        eggs.add(new ItemStack(Material.CHICKEN_SPAWN_EGG, amount));
        eggs.add(new ItemStack(Material.COW_SPAWN_EGG, amount));
        eggs.add(new ItemStack(Material.PIG_SPAWN_EGG, amount));
        eggs.add(new ItemStack(Material.SHEEP_SPAWN_EGG, amount));
        eggs.add(new ItemStack(Material.WOLF_SPAWN_EGG, amount));
        eggs.add(new ItemStack(Material.PANDA_SPAWN_EGG, amount));
        eggs.add(new ItemStack(Material.OCELOT_SPAWN_EGG, amount));
        eggs.add(new ItemStack(Material.RABBIT_SPAWN_EGG, amount));
        eggs.add(new ItemStack(Material.HORSE_SPAWN_EGG, amount));
        eggs.add(new ItemStack(Material.LLAMA_SPAWN_EGG, amount));
        eggs.add(new ItemStack(Material.PARROT_SPAWN_EGG, amount));
        eggs.add(new ItemStack(Material.CAT_SPAWN_EGG, amount));
        eggs.add(new ItemStack(Material.TURTLE_SPAWN_EGG, amount));
        eggs.add(new ItemStack(Material.FOX_SPAWN_EGG, amount));
        eggs.add(new ItemStack(Material.BEE_SPAWN_EGG, amount));
        eggs.add(new ItemStack(Material.ARMADILLO_SPAWN_EGG, amount));

        Collections.shuffle(eggs);
        ArrayList<ItemStack> items = new ArrayList<>();
        items.addAll(eggs.subList(0, 1));

        Kit.addKitItems(items, player);

    }

}
