package br.com.bingo.kits.definitions;

import br.com.bingo.kits.Kit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class NetherExplorer extends Kit {

        public NetherExplorer(){
            name = "Nether Explorer";
        }

        @Override
        public void giveKit(Player player) {

            ItemStack compass = new ItemStack(Material.COMPASS, 1);
            ItemMeta compassMeta = compass.getItemMeta();
            compassMeta.setDisplayName(ChatColor.GOLD + "Nether Compass");
            compassMeta.setUnbreakable(true);
            compassMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GREEN + "Botao direito para Fortaleza");
            lore.add(ChatColor.GREEN + "Botao esquerdo para Bastion");
            compassMeta.setLore(lore);
            compass.setItemMeta(compassMeta);

            ItemStack creator = new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE);
            ItemMeta creatorMeta = creator.getItemMeta();
            creatorMeta.setDisplayName(ChatColor.GOLD + "Portal Creator");
            creatorMeta.setUnbreakable(true);
            creatorMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            creator.setItemMeta(creatorMeta);

            ArrayList<ItemStack> items = new ArrayList<>();
            items.add(compass);
            items.add(creator);
            addKitItems(items, player);

            player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 999999, 0, false, false));
        }

    @Override
    public void startKit(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 999999, 0, false, false));
    }

    @Override
    public void completeKit(Player player) {
        ItemStack creator = new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE);
        ItemMeta creatorMeta = creator.getItemMeta();
        creatorMeta.setDisplayName(ChatColor.GOLD + "Portal Creator");
        creatorMeta.setUnbreakable(true);
        creatorMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        creator.setItemMeta(creatorMeta);

        ItemStack compass = new ItemStack(Material.COMPASS, 1);
        ItemMeta compassMeta = compass.getItemMeta();
        compassMeta.setDisplayName(ChatColor.GOLD + "Nether Compass");
        compassMeta.setUnbreakable(true);
        compassMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GREEN + "Botao direito para Fortaleza");
        lore.add(ChatColor.GREEN + "Botao esquerdo para Bastion");
        compassMeta.setLore(lore);
        compass.setItemMeta(compassMeta);

        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(compass);
        items.add(creator);
        addKitItems(items, player);
    }

}
