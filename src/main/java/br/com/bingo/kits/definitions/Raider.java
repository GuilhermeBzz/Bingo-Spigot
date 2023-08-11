package br.com.bingo.kits.definitions;

import br.com.bingo.kits.Kit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class Raider extends Kit {

    public Raider(){
        name = "Raider";
    }

    @Override
    public void giveKit(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.BAD_OMEN, 999999, 0));

        ItemStack item = new ItemStack(Material.IRON_GOLEM_SPAWN_EGG, 10);

        ArrayList<ItemStack> items = new ArrayList<>();
        //items.add(compass);
        items.add(item);
        addKitItems(items, player);
    }

    @Override
    public void startKit(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.BAD_OMEN, 999999, 0));
    }

    @Override
    public void completeKit(Player player) {
        giveKit(player);
    }

    @Override
    public void onRespawn(Player player){
        player.addPotionEffect(new PotionEffect(PotionEffectType.BAD_OMEN, 999999, 0));
        player.sendMessage(ChatColor.RED + "Voce ja recebeu seus Iron Golems!");
    }
}
