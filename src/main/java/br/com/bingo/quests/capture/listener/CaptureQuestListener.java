package br.com.bingo.quests.capture.listener;

import br.com.bingo.game.GameManager;
import br.com.bingo.quests.Quest;
import br.com.bingo.quests.QuestType;
import br.com.bingo.team.TeamType;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CaptureQuestListener implements Listener {

    GameManager gameManager;


    public CaptureQuestListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }



    @EventHandler
    public void clickOnEnemyFlagEvent(PlayerInteractEvent event){
        if(!gameManager.isGameStarted()) return;
        if(gameManager.captureEnded) return;
        if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if(event.getClickedBlock() == null) return;
        if (event.getHand() != EquipmentSlot.HAND) return;
        if(!event.getClickedBlock().getType().equals(Material.BLUE_BANNER)
                && !event.getClickedBlock().getType().equals(Material.RED_BANNER)
        ) return;
        Location blockLocation = event.getClickedBlock().getLocation();
        HashMap<TeamType, Location> captureLocations = gameManager.flagBaseLocationsMap;

        Player player = event.getPlayer();
        TeamType playerTeam = gameManager.getPlayerTeam(player);

        if(playerTeam.equals(TeamType.TEAM_RED)){
            if(!captureLocations.get(TeamType.TEAM_BLUE).equals(blockLocation)) return;
        }else{
            if(!captureLocations.get(TeamType.TEAM_RED).equals(blockLocation)) return;
        }

        ItemStack item = event.getItem();
        Bukkit.getLogger().info("" + player.getName() + " clicou na bandeira inimiga segurando:" + item);
        if(item == null || item.getType().equals(Material.AIR)){
            captureTheFlag(player, playerTeam, blockLocation);
        }
        player.sendMessage(ChatColor.RED + "Você não pode capturar a bandeira inimiga com itens na mão!");
        return;

    }

    public void captureTheFlag(Player player, TeamType playerTeam, Location blockLocation){
        blockLocation.getBlock().setType(Material.AIR);
        blockLocation.getWorld().spawnParticle(Particle.EXPLOSION, blockLocation, 1);
        blockLocation.getWorld().playSound(blockLocation, Sound.ENTITY_GENERIC_EXPLODE, 1, 1);

        ItemStack flag = new ItemStack(Material.RED_BANNER);
        ItemMeta flagMeta = flag.getItemMeta();
        flagMeta.setDisplayName(ChatColor.RED + "Bandeira Vermelha");

        if(playerTeam.equals(TeamType.TEAM_RED)){
            flag.setType(Material.BLUE_BANNER);
            flagMeta.setDisplayName(ChatColor.BLUE + "Bandeira Azul");
        }
        flagMeta.setLore(null);
        flag.setItemMeta(flagMeta);
        player.getInventory().setItemInMainHand(flag);

        Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + player.getName() + " capturou a bandeira inimiga!");
    }

    @EventHandler
    public void preventPlacingFlags(BlockPlaceEvent event){
        ItemStack item = event.getItemInHand();
        if (item == null || !item.hasItemMeta()) return;
        ItemMeta meta = item.getItemMeta();
        if (meta.hasDisplayName()) {
            String displayName = meta.getDisplayName();
            if (displayName.equalsIgnoreCase(ChatColor.RED + "Bandeira Vermelha")
                || displayName.equalsIgnoreCase(ChatColor.BLUE + "Bandeira Azul")){
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.RED + "Você não pode colocar esse bloco!");
            }
        }
    }

    @EventHandler
    public void deliverFlagEvent(PlayerInteractEvent event){
        if(!gameManager.isGameStarted()) return;
        if(gameManager.captureEnded) return;
        if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if(event.getClickedBlock() == null) return;
        if(!event.getClickedBlock().getType().equals(Material.CHISELED_TUFF)) return;

        Location blockLocation = event.getClickedBlock().getLocation();
        HashMap<TeamType, Location> captureLocations = gameManager.flagBaseLocationsMap;

        Player player = event.getPlayer();
        TeamType playerTeam = gameManager.getPlayerTeam(player);

        ItemStack item = event.getItem();
        if(!isFlagItem(item)) return;

        if(playerTeam.equals(TeamType.TEAM_RED)){
            if(!captureLocations.get(TeamType.TEAM_RED).clone().add(0,-1,0).equals(blockLocation)) return;
        }else{
            if(!captureLocations.get(TeamType.TEAM_BLUE).clone().add(0,-1,0).equals(blockLocation)) return;
        }

        deliverFlag(player, playerTeam, blockLocation);
    }

    public void deliverFlag(Player player, TeamType playerTeam, Location blockLocation){

        player.getInventory().remove(player.getInventory().getItemInMainHand());
        Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + player.getName() + " entregou a bandeira inimiga!");
        gameManager.captureEnded = true;
        completeQuest(player);

    }

    public void completeQuest(Player player){
        for(Quest quest : gameManager.getAvailableQuests()){
            if(quest.getType() != QuestType.CAPTURE) continue;
            gameManager.completeQuest(player.getUniqueId(), quest);
            break;
        }
    }

    @EventHandler
    public void restoreFlagIfPlayerDie(PlayerDeathEvent event){
        if(!gameManager.isGameStarted()) return;
        if(gameManager.captureEnded) return;
        Player player = event.getEntity();
        TeamType playerTeam = gameManager.getPlayerTeam(player);
        HashMap<TeamType, Location> captureLocations = gameManager.flagBaseLocationsMap;
        List<ItemStack> itensToRemove = new ArrayList<>();
        for(ItemStack item : event.getDrops()){
            if(isFlagItem(item)){
                if(playerTeam.equals(TeamType.TEAM_RED)){
                    captureLocations.get(TeamType.TEAM_BLUE).getBlock().setType(Material.BLUE_BANNER);
                    Bukkit.broadcastMessage(ChatColor.BLUE + "A bandeira azul foi restaurada!");
                } else{
                    captureLocations.get(TeamType.TEAM_RED).getBlock().setType(Material.RED_BANNER);
                    Bukkit.broadcastMessage(ChatColor.RED + "A bandeira vermelha foi restaurada!");
                }
                itensToRemove.add(item);
            }
        }
        for(ItemStack item : itensToRemove){
            event.getDrops().remove(item);
        }
    }

    @EventHandler
    public void onPlayerPickupFlag(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        ItemStack item = event.getItem().getItemStack();

        if (!isFlagItem(item)) return;

        TeamType playerTeam = gameManager.getPlayerTeam(player);

        if (isOwnTeamFlag(playerTeam, item)) {
            event.setCancelled(true); // Impede que o jogador pegue a bandeira
            event.getItem().remove(); // Remove o item do chão
            restoreFlagToBase(playerTeam); // Restaura a bandeira
            player.sendMessage(ChatColor.YELLOW + "Você não pode pegar a bandeira do seu próprio time. Ela foi restaurada à base.");
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        ItemStack currentItem = event.getCurrentItem();

        if (currentItem == null || !isFlagItem(currentItem)) return;

        TeamType playerTeam = gameManager.getPlayerTeam(player);

        if (isOwnTeamFlag(playerTeam, currentItem)) {
            event.setCancelled(true); // Bloqueia a ação
            event.getClickedInventory().remove(currentItem); // Remove do baú
            restoreFlagToBase(playerTeam);
            player.sendMessage(ChatColor.YELLOW + "Você não pode pegar a bandeira do seu próprio time. Ela foi restaurada à base.");
        }
    }




    private boolean isFlagItem(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasDisplayName()) return false;

        String name = meta.getDisplayName();
        return name.equalsIgnoreCase(ChatColor.RED + "Bandeira Vermelha") ||
                name.equalsIgnoreCase(ChatColor.BLUE + "Bandeira Azul");
    }

    private boolean isOwnTeamFlag(TeamType team, ItemStack item) {
        if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) return false;
        String name = item.getItemMeta().getDisplayName();
        return (team == TeamType.TEAM_RED && name.equalsIgnoreCase(ChatColor.RED + "Bandeira Vermelha")) ||
                (team == TeamType.TEAM_BLUE && name.equalsIgnoreCase(ChatColor.BLUE + "Bandeira Azul"));
    }

    private void restoreFlagToBase(TeamType team) {
        Location loc = gameManager.flagBaseLocationsMap.get(team);
        if (team == TeamType.TEAM_RED) {
            loc.getBlock().setType(Material.RED_BANNER);
            Bukkit.broadcastMessage(ChatColor.RED + "A bandeira vermelha foi restaurada!");
        } else {
            loc.getBlock().setType(Material.BLUE_BANNER);
            Bukkit.broadcastMessage(ChatColor.BLUE + "A bandeira azul foi restaurada!");
        }
    }

}
