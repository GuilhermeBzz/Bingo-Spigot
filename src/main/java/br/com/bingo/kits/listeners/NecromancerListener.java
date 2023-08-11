package br.com.bingo.kits.listeners;

import br.com.bingo.Bingo;
import br.com.bingo.game.GameManager;
import br.com.bingo.team.TeamType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class NecromancerListener implements Listener {

    GameManager gameManager;
    Map<UUID, Long> cooldowns = new HashMap<>();

    public NecromancerListener(GameManager gameManager){
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onHoeClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(event.getItem() == null){return;}
        if(!player.getInventory().getItemInMainHand().hasItemMeta()) return;

        if(!Objects.requireNonNull(player.getInventory().getItemInMainHand().getItemMeta()).getDisplayName().equals(ChatColor.GOLD + "Necromancer's Hoe"))return;
        if(!event.getAction().equals(Action.RIGHT_CLICK_AIR) && !event.getAction().equals(Action.RIGHT_CLICK_BLOCK))return;
        if(!player.getWorld().getPVP()) {
            player.sendMessage(ChatColor.RED + "Voce ainda nao pode usar seu Kit");
            return;
        }

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



        ArrayList<EntityType> mobs = new ArrayList<>();
        if(player.getInventory().getItemInMainHand().getType().equals(Material.WOODEN_HOE)){
            //STARTER
            Random random = new Random();
            for(int i = 0; i < 5; i++){

                int randomMob = random.nextInt(4);
                switch (randomMob){
                    case 0:
                        mobs.add(EntityType.ZOMBIE);
                        break;
                    case 1:
                        mobs.add(EntityType.SKELETON);
                        break;
                    case 2:
                        mobs.add(EntityType.CREEPER);
                        break;
                    case 3:
                        mobs.add(EntityType.SILVERFISH);
                        break;
                    default:
                        mobs.add(EntityType.ZOMBIE);
                        break;
                }
            }

        } else if (player.getInventory().getItemInMainHand().getType().equals(Material.NETHERITE_HOE)) {
            //ADVANCED
            Random random = new Random();
            for(int i = 0; i < 7; i++){
                int randomMob = random.nextInt(9);
                switch (randomMob){
                    case 0:
                        mobs.add(EntityType.HUSK);
                        break;
                    case 1:
                        mobs.add(EntityType.STRAY);
                        break;
                    case 2:
                        mobs.add(EntityType.CREEPER);
                        break;
                    case 3:
                        mobs.add(EntityType.SILVERFISH);
                        break;
                    case 4:
                        mobs.add(EntityType.BLAZE);
                        break;
                    case 5:
                        mobs.add(EntityType.VINDICATOR);
                        break;
                    case 6:
                        mobs.add(EntityType.WITCH);
                        break;
                    case 7:
                        mobs.add(EntityType.CAVE_SPIDER);
                        break;
                    case 8:
                        mobs.add(EntityType.PHANTOM);
                        break;
                    default:
                        mobs.add(EntityType.ZOMBIE);
                        break;
                }
            }
        }

        TeamType necroType = gameManager.playerTeam.get(player.getUniqueId());
        for(UUID uuid: gameManager.playerTeam.keySet()){
            Player target = Bukkit.getPlayer(uuid);
            if(target == null){continue;}
            if(target.equals(player)){continue;}
            if(!gameManager.playerTeam.get(target.getUniqueId()).equals(necroType)){
                spawnWarning(target, mobs,3);
                target.sendMessage(ChatColor.RED + "Você foi atacado pelo necromante!");
            }else{
                if(necroType.equals(TeamType.SOLO)){
                    spawnWarning(target, mobs,3);
                    target.sendMessage(ChatColor.RED + "Você foi atacado pelo necromante!");
                }
            }
        }


    }

    public void spawnWarning(Player player, ArrayList<EntityType> mobs, int time){

        if(time == 0){
            spawnMobs(player, mobs);
            return;
        }
        player.sendMessage(ChatColor.RED + "Você será atacado pelo necromancer em " + time + " segundos");
        int newTime = time - 1;
        Bukkit.getScheduler().scheduleSyncDelayedTask(Bingo.getInstance(),  new Runnable() {
            @Override
            public void run() {
                spawnWarning(player, mobs, newTime);
            }
        }, 60L);
    }

    public void spawnMobs(Player player, ArrayList<EntityType> mobs){
        Random random = new Random();
        for(EntityType entity : mobs){
            int R = random.nextInt(4) + 6;
            double angle = 2 * Math.PI * random.nextDouble();
            double x = (double) (R * Math.cos(angle));
            double z = (double) (R * Math.sin(angle));


            double y = 2;
            player.getWorld().spawnEntity(player.getLocation().add(x, y, z), entity);

        }
        if(mobs.size() >= 6){
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*10, 3));
        }
    }
}
