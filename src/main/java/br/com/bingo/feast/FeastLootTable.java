package br.com.bingo.feast;

import org.bukkit.Material;
import org.bukkit.PortalType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.*;

public enum FeastLootTable {


    BEEF(Material.COOKED_BEEF, 5, 10, null, 0.5),
    DSWORD(Material.DIAMOND_SWORD, 1, 1,new EnchantmentIntegerPair[]{new EnchantmentIntegerPair(Enchantment.DAMAGE_ALL, 5)} , 0.1),
    TNT(Material.TNT, 1, 5, null, 0.25),
    BREAD(Material.BREAD, 8, 16, null, 0.5),
    OAKLOG(Material.OAK_LOG, 8, 16, null, 0.7),
    OBSIDIAN(Material.OBSIDIAN, 5, 13, null, 0.7),
    PEARL(Material.ENDER_PEARL, 2, 8, null, 0.5),
    EYE(Material.ENDER_EYE, 1, 11, null, 0.3),
    GAPPLE(Material.GOLDEN_APPLE, 1, 8, null, 0.4),
    E_GAPPLE(Material.ENCHANTED_GOLDEN_APPLE, 1, 3, null, 0.2),
    BOW(Material.BOW, 1, 1, new EnchantmentIntegerPair[]{new EnchantmentIntegerPair(Enchantment.ARROW_DAMAGE, 5), new EnchantmentIntegerPair(Enchantment.ARROW_INFINITE, 1)}, 0.2),
    D_BOOTS(Material.DIAMOND_BOOTS, 1, 1, new EnchantmentIntegerPair[]{new EnchantmentIntegerPair(Enchantment.PROTECTION_ENVIRONMENTAL, 4), new EnchantmentIntegerPair(Enchantment.PROTECTION_FALL, 4)}, 0.1),
    D_LEGGINGS(Material.DIAMOND_LEGGINGS, 1, 1, new EnchantmentIntegerPair[]{new EnchantmentIntegerPair(Enchantment.PROTECTION_ENVIRONMENTAL, 4)}, 0.1),
    D_CHESTPLATE(Material.DIAMOND_CHESTPLATE, 1, 1, new EnchantmentIntegerPair[]{new EnchantmentIntegerPair(Enchantment.PROTECTION_ENVIRONMENTAL, 4)}, 0.1),
    D_HELMET(Material.DIAMOND_HELMET, 1, 1, new EnchantmentIntegerPair[]{new EnchantmentIntegerPair(Enchantment.PROTECTION_ENVIRONMENTAL, 4)}, 0.1),
    D_PICKAXE(Material.DIAMOND_PICKAXE, 1, 1, new EnchantmentIntegerPair[]{new EnchantmentIntegerPair(Enchantment.DIG_SPEED, 5)}, 0.1),
    D_AXE(Material.DIAMOND_AXE, 1, 1, new EnchantmentIntegerPair[]{new EnchantmentIntegerPair(Enchantment.DIG_SPEED, 5)}, 0.1),
    D_SHOVEL(Material.DIAMOND_SHOVEL, 1, 1, new EnchantmentIntegerPair[]{new EnchantmentIntegerPair(Enchantment.DIG_SPEED, 5)}, 0.1),
    PANDA(Material.PANDA_SPAWN_EGG, 1, 3, null, 0.15),
    PIG(Material.PIG_SPAWN_EGG, 1, 3, null, 0.15),
    COW(Material.COW_SPAWN_EGG, 1, 3, null, 0.15),
    CHICKEN(Material.CHICKEN_SPAWN_EGG, 1, 3, null, 0.15),
    SHEEP(Material.SHEEP_SPAWN_EGG, 1, 3, null, 0.15),
    HORSE(Material.HORSE_SPAWN_EGG, 1, 3, null, 0.15),
    WOLF(Material.WOLF_SPAWN_EGG, 1, 3, null, 0.15),
    MUSHROOM(Material.MOOSHROOM_SPAWN_EGG, 1, 3, null, 0.15),
    WITCH(Material.WITCH_SPAWN_EGG, 1, 3, null, 0.15),
    VILLAGER(Material.VILLAGER_SPAWN_EGG, 1, 3, null, 0.15),
    WARDEN(Material.WARDEN_SPAWN_EGG, 1, 1, null, 0.001),
    POTION(Material.SPLASH_POTION, 1, 1, null, 0.7),
    ANVIL(Material.ANVIL, 1, 1, null, 0.5),
    FISHING(Material.FISHING_ROD, 1, 1, new EnchantmentIntegerPair[]{new EnchantmentIntegerPair(Enchantment.LUCK, 3), new EnchantmentIntegerPair(Enchantment.LURE, 3)}, 0.4),
    WATER(Material.WATER_BUCKET, 1, 1, null, 0.6),
    LAVA(Material.LAVA_BUCKET, 1, 1, null, 0.6),
    BAMBOO(Material.BAMBOO, 1, 5, null, 0.3),
    WHEAT(Material.WHEAT, 1, 5, null, 0.3),
    CARROT(Material.CARROT, 1, 5, null, 0.3),
    TRIDENT(Material.TRIDENT, 1, 1, new EnchantmentIntegerPair[]{new EnchantmentIntegerPair(Enchantment.LOYALTY, 3), new EnchantmentIntegerPair(Enchantment.CHANNELING, 1)}, 0.1),
    TOTEM(Material.TOTEM_OF_UNDYING, 1, 1, null, 0.08),
    BLAZE_ROD(Material.BLAZE_ROD, 1, 6, null, 0.3),
    GOLDEN_CARROT(Material.GOLDEN_CARROT, 1, 5, null, 0.3),
    ELYTRA(Material.ELYTRA, 1, 1, null, 0.08),
    COBWEB(Material.COBWEB, 1, 5, null, 0.7),
    IRON(Material.IRON_INGOT, 1, 16, null, 0.7),
    GOLD(Material.GOLD_INGOT, 1, 16, null, 0.7),
    SHULKER_BOX(Material.SHULKER_BOX, 1, 1, null, 0.08),
    LAPIS(Material.LAPIS_LAZULI, 3, 16, null, 0.8),
    LEAD(Material.LEAD, 1, 1, null, 0.5),
    SPRUCE(Material.SPRUCE_LOG, 8, 16, null, 0.7),
    XP(Material.EXPERIENCE_BOTTLE, 1, 16, null, 0.4),
    SLIME(Material.SLIME_BALL, 1, 5, null, 0.3),

    ;

    private final Material material;
    private final int minAmount;
    private final int maxAmount;
    private final EnchantmentIntegerPair[] enchants;
    private final double chance;

    FeastLootTable(Material material, int minAmount, int maxAmount, EnchantmentIntegerPair[] enchants, double chance) {
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

    public EnchantmentIntegerPair[] getEnchants() {
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

    public static ItemStack generateItem(FeastLootTable item){
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
            for(EnchantmentIntegerPair entry : item.getEnchants()){
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
