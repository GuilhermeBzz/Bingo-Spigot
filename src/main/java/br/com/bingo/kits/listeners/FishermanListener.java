package br.com.bingo.kits.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class FishermanListener implements Listener{


    Map<UUID, Long> cooldowns = new HashMap<>();


    @EventHandler
    public void onLeadEvent(PlayerLeashEntityEvent event){
        if(event.getPlayer().getInventory().getItemInMainHand() == null){return;}
        if(!event.getPlayer().getInventory().getItemInMainHand().hasItemMeta()) return;
        String name = Objects.requireNonNull(event.getPlayer().getInventory().getItemInMainHand().getItemMeta()).getDisplayName();
        if(name.equals(ChatColor.GOLD + "Fishing Net")){
            event.setCancelled(true);
            return;
        }
    }
    @EventHandler
    public void onLeadToo(PlayerInteractAtEntityEvent event){
        if(event.getPlayer().getInventory().getItemInMainHand() == null){return;}
        if(!event.getPlayer().getInventory().getItemInMainHand().hasItemMeta()) return;
        String name = Objects.requireNonNull(event.getPlayer().getInventory().getItemInMainHand().getItemMeta()).getDisplayName();
        if(name.equals(ChatColor.GOLD + "Fishing Net")){
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onNet(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(event.getItem() == null){return;}
        if(!player.getInventory().getItemInMainHand().hasItemMeta()) return;

        if(!Objects.requireNonNull(player.getInventory().getItemInMainHand().getItemMeta()).getDisplayName().equals(ChatColor.GOLD + "Fishing Net"))return;
        if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && !event.getAction().equals(Action.RIGHT_CLICK_AIR))return;
        if(!player.isInWater()){
            player.sendMessage(ChatColor.RED + "Voce so pode usar a rede na agua");
            return;
        }
        event.setCancelled(true);

        if(!cooldowns.containsKey(player.getUniqueId())){
            cooldowns.put(player.getUniqueId(), 0L);
        }
        long currentTime = System.currentTimeMillis();
        long lastUsedTime = cooldowns.getOrDefault(player.getUniqueId(), 0L);
        long cooldown = 7*60*1000;
        if(currentTime - lastUsedTime < cooldown){
            player.sendMessage(ChatColor.RED + "Aguarde " + ((cooldown/1000)-(((currentTime - lastUsedTime)) / 1000)) + " segundos para usar novamente");
            return;
        }
        cooldowns.put(player.getUniqueId(), currentTime);

        player.sendMessage("Voce usou a rede!");
        Random random = new Random();
        int item = random.nextInt(20);
        switch (item){
            case 0:
                player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.DIAMOND));
                break;
            case 1:
                player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.EMERALD));
                break;
            case 2:
                player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.GOLD_INGOT));
                break;
            case 3:
                player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.NETHERITE_SCRAP));
                break;
            case 4:
                player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.NETHERITE_LEGGINGS));
                break;
            case 5:
                player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.TRIDENT));
                break;
            case 6:
                player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.SLIME_BALL));
                break;
            case 7:
                player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.ENCHANTED_GOLDEN_APPLE));
                break;
            case 8:
                player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.ELYTRA));
                break;
            case 9:
                player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.SHULKER_BOX));
                break;
            case 10:
                player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.BLAZE_POWDER));
                break;
            case 11:
                player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.WARD_ARMOR_TRIM_SMITHING_TEMPLATE));
                break;
            case 12:
                player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.TOTEM_OF_UNDYING));
                break;
            case 13:
                player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.BEDROCK));
                break;
            case 14:
                player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.DIAMOND_PICKAXE));
                break;
            case 15:
                player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.GLASS_BOTTLE));
                player.sendMessage(ChatColor.RED + "Que azar! Voce pegou uma garrafa vazia. Nao jogue lixo no mar!");
                break;
            case 16:
                player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.ENDER_PEARL));
                break;
            case 17:
                player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.GHAST_TEAR));
                break;
            default:
                player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.IRON_INGOT));
                break;
        }
        return;
    }
}
