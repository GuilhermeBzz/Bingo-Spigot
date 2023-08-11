package br.com.bingo.kits.definitions;

import br.com.bingo.kits.Kit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class Aquaman extends Kit {

    public Aquaman(){
        this.name = "Aquaman";
    }
    @Override
    public void giveKit(Player player) {

        //ArrayList<ItemStack> items = new ArrayList<>();

        ItemStack boot = new ItemStack(Material.IRON_BOOTS);
        ItemMeta bootMeta = boot.getItemMeta();
        bootMeta.setUnbreakable(true);
        bootMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        bootMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        boot.setItemMeta(bootMeta);
        bootMeta.setDisplayName(ChatColor.GOLD + "Aquaman's Boots");
        boot.setItemMeta(bootMeta);
        boot.addEnchantment(Enchantment.DEPTH_STRIDER,1);
        boot.addEnchantment(Enchantment.BINDING_CURSE,1);

       // items.add(boot);

        ItemStack helmet = new ItemStack(Material.TURTLE_HELMET);
        ItemMeta helmetMeta = helmet.getItemMeta();
        helmetMeta.setUnbreakable(true);
        helmet.setItemMeta(helmetMeta);
        helmetMeta.setDisplayName(ChatColor.GOLD + "Aquaman's Helmet");
        helmetMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        helmetMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        helmet.setItemMeta(helmetMeta);
        helmet.addEnchantment(Enchantment.WATER_WORKER,1);
        helmet.addUnsafeEnchantment(Enchantment.OXYGEN,5);
        helmet.addEnchantment(Enchantment.BINDING_CURSE,1);

        //items.add(helmet);
        player.getInventory().setHelmet(helmet);
        player.getInventory().setBoots(boot);


        player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 999999, 1, false, false));

    }

    @Override
    public void startKit(Player player) {
        ItemStack boot = new ItemStack(Material.IRON_BOOTS);
        ItemMeta bootMeta = boot.getItemMeta();
        bootMeta.setUnbreakable(true);
        bootMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        bootMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        boot.setItemMeta(bootMeta);
        bootMeta.setDisplayName(ChatColor.GOLD + "Aquaman's Boots");
        boot.setItemMeta(bootMeta);
        boot.addEnchantment(Enchantment.DEPTH_STRIDER,1);
        boot.addEnchantment(Enchantment.BINDING_CURSE,1);

        // items.add(boot);

        ItemStack helmet = new ItemStack(Material.TURTLE_HELMET);
        ItemMeta helmetMeta = helmet.getItemMeta();
        helmetMeta.setUnbreakable(true);
        helmet.setItemMeta(helmetMeta);
        helmetMeta.setDisplayName(ChatColor.GOLD + "Aquaman's Helmet");
        helmetMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        helmetMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        helmet.setItemMeta(helmetMeta);
        helmet.addEnchantment(Enchantment.WATER_WORKER,1);
        helmet.addUnsafeEnchantment(Enchantment.OXYGEN,5);
        helmet.addEnchantment(Enchantment.BINDING_CURSE,1);

        //items.add(helmet);
        player.getInventory().setHelmet(helmet);
        player.getInventory().setBoots(boot);
    }

    @Override
    public void completeKit(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 999999, 0, false, false));
    }
}
