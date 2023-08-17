package br.com.bingo.feast;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public enum MiniFeastLootTable {

    BEEF(Material.COOKED_BEEF, 3, 6, null, 0.5),
    TNT(Material.TNT, 1, 3, null, 0.25),
    BREAD(Material.BREAD, 4, 8, null, 0.5),
    OAKLOG(Material.OAK_LOG, 4, 8, null, 0.7),
    OBSIDIAN(Material.OBSIDIAN, 2, 4, null, 0.7),
    PEARL(Material.ENDER_PEARL, 1, 2, null, 0.3),
    GAPPLE(Material.GOLDEN_APPLE, 1, 3, null, 0.2),
    DIAMOND(Material.DIAMOND, 1, 3, null, 0.2),
    IRON_PICK_E(Material.IRON_PICKAXE, 1, 1, new EnchantmentIntegerPair[]{new EnchantmentIntegerPair(Enchantment.DIG_SPEED, 3)}, 0.2),
    IRON_PICK(Material.IRON_PICKAXE, 1, 1, null, 0.5),
    IRON_AXE(Material.IRON_AXE, 1, 1, null, 0.5),
    IRON_SHOVEL(Material.IRON_SHOVEL, 1, 1, null, 0.5),
    IRON_HELMET(Material.IRON_HELMET, 1, 1, null, 0.5),
    IRON_CHESTPLATE(Material.IRON_CHESTPLATE, 1, 1, null, 0.5),
    IRON_LEGGINGS(Material.IRON_LEGGINGS, 1, 1, null, 0.5),
    IRON_BOOTS(Material.IRON_BOOTS, 1, 1, null, 0.5),
    IRON_SWORD(Material.IRON_SWORD, 1, 1, null, 0.5),
    SHIELD(Material.SHIELD, 1, 1, null, 0.5),
    GOLD( Material.GOLD_INGOT, 1, 7, null, 0.4),


    ;


    private final Material material;
    private final int minAmount;
    private final int maxAmount;
    private final MiniFeastLootTable.EnchantmentIntegerPair[] enchants;
    private final double chance;


    MiniFeastLootTable(Material material, int minAmount, int maxAmount, MiniFeastLootTable.EnchantmentIntegerPair[] enchants, double chance) {
        this.material = material;
        this.maxAmount = maxAmount;
        this.minAmount = minAmount;
        this.enchants = enchants;
        this.chance = chance;
    }

    public Material getMaterial() {
        return material;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public MiniFeastLootTable.EnchantmentIntegerPair[] getEnchants() {
        return enchants;
    }

    public double getChance() {
        return chance;
    }

    public static class EnchantmentIntegerPair{
        private Enchantment enchantment;
        private int level;

        public EnchantmentIntegerPair(Enchantment enchantment, int level){
            this.enchantment = enchantment;
            this.level = level;
        }

        public Enchantment getEnchantment() {
            return enchantment;
        }

        public int getLevel() {
            return level;
        }
    }

    public static ItemStack generateItem(MiniFeastLootTable item){
        Random random = new Random();
        if(random.nextDouble() > item.getChance()) return new ItemStack(Material.AIR);
        int amount = 0;
        if(item.getMaxAmount() != item.getMinAmount()){
            amount = random.nextInt(item.getMaxAmount() - item.getMinAmount() + 1) + item.getMinAmount();
        } else{
            amount = item.getMaxAmount();
        }
        ItemStack itemStack = new ItemStack(item.getMaterial(), amount);
        if(item.getEnchants() != null){
            for(MiniFeastLootTable.EnchantmentIntegerPair entry : item.getEnchants()){
                itemStack.addEnchantment(entry.getEnchantment(), entry.getLevel());
            }
        }
        if(item.getMaterial() == Material.SPLASH_POTION){
            PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
            ArrayList<PotionEffectType> potionTypes = new ArrayList<>(Arrays.asList(PotionEffectType.values()));
            potionMeta.addCustomEffect(new PotionEffect(potionTypes.get(random.nextInt(potionTypes.size())), 20 * 60 * 3, 1), true);
            itemStack.setItemMeta(potionMeta);
        }

        return itemStack;
    }
}
