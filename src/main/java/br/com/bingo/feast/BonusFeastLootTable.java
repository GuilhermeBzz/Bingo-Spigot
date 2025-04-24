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

public enum BonusFeastLootTable {
    // Combat Items
    NETHERITE_SWORD(Material.NETHERITE_SWORD, 1, 1, new EnchantmentIntegerPair[]{
            new EnchantmentIntegerPair(Enchantment.SHARPNESS, 5),
            new EnchantmentIntegerPair(Enchantment.UNBREAKING, 3),
            new EnchantmentIntegerPair(Enchantment.SWEEPING_EDGE, 3),
            new EnchantmentIntegerPair(Enchantment.FIRE_ASPECT, 2)
    }, 0.25),
    
    NETHERITE_AXE(Material.NETHERITE_AXE, 1, 1, new EnchantmentIntegerPair[]{
            new EnchantmentIntegerPair(Enchantment.SHARPNESS, 5),
            new EnchantmentIntegerPair(Enchantment.UNBREAKING, 3),
            new EnchantmentIntegerPair(Enchantment.EFFICIENCY, 5)
    }, 0.25),
    
    BOW(Material.BOW, 1, 1, new EnchantmentIntegerPair[]{
            new EnchantmentIntegerPair(Enchantment.POWER, 5),
            new EnchantmentIntegerPair(Enchantment.INFINITY, 1),
            new EnchantmentIntegerPair(Enchantment.PUNCH, 2),
            new EnchantmentIntegerPair(Enchantment.FLAME, 1)
    }, 0.25),
    
    // Armor
    NETHERITE_HELMET(Material.NETHERITE_HELMET, 1, 1, new EnchantmentIntegerPair[]{
            new EnchantmentIntegerPair(Enchantment.PROTECTION, 4),
            new EnchantmentIntegerPair(Enchantment.UNBREAKING, 3),
            new EnchantmentIntegerPair(Enchantment.THORNS, 3),
            new EnchantmentIntegerPair(Enchantment.RESPIRATION, 3)
    }, 0.25),
    
    NETHERITE_CHESTPLATE(Material.NETHERITE_CHESTPLATE, 1, 1, new EnchantmentIntegerPair[]{
            new EnchantmentIntegerPair(Enchantment.PROTECTION, 4),
            new EnchantmentIntegerPair(Enchantment.UNBREAKING, 3),
            new EnchantmentIntegerPair(Enchantment.THORNS, 3)
    }, 0.25),
    
    // Utility Items
    NETHERITE_PICKAXE(Material.NETHERITE_PICKAXE, 1, 1, new EnchantmentIntegerPair[]{
            new EnchantmentIntegerPair(Enchantment.EFFICIENCY, 5),
            new EnchantmentIntegerPair(Enchantment.UNBREAKING, 3),
            new EnchantmentIntegerPair(Enchantment.FORTUNE, 3)
    }, 0.25),
    
    SHIELD(Material.SHIELD, 1, 1, new EnchantmentIntegerPair[]{
            new EnchantmentIntegerPair(Enchantment.UNBREAKING, 3)
    }, 0.3),
    
    // Potions
    SPLASH_POTION(Material.SPLASH_POTION, 2, 4, null, 0.3),
    
    // Quest Helpers
    ENDER_PEARL(Material.ENDER_PEARL, 4, 8, null, 0.35),
    
    GOLDEN_APPLE(Material.GOLDEN_APPLE, 2, 4, null, 0.3),
    
    ENCHANTED_GOLDEN_APPLE(Material.ENCHANTED_GOLDEN_APPLE, 1, 2, null, 0.2),
    
    DIAMOND(Material.DIAMOND, 4, 8, null, 0.3),
    
    OBSIDIAN(Material.OBSIDIAN, 8, 16, null, 0.3),
    
    // Nether Items
    NETHERITE_INGOT(Material.NETHERITE_INGOT, 1, 2, null, 0.2),
    
    ANCIENT_DEBRIS(Material.ANCIENT_DEBRIS, 4, 8, null, 0.25),
    
    // Special Items
    TRIDENT(Material.TRIDENT, 1, 1, new EnchantmentIntegerPair[]{
            new EnchantmentIntegerPair(Enchantment.LOYALTY, 3),
            new EnchantmentIntegerPair(Enchantment.UNBREAKING, 3),
            new EnchantmentIntegerPair(Enchantment.IMPALING, 5),
            new EnchantmentIntegerPair(Enchantment.CHANNELING, 1)
    }, 0.2),
    
    CROSSBOW(Material.CROSSBOW, 1, 1, new EnchantmentIntegerPair[]{
            new EnchantmentIntegerPair(Enchantment.QUICK_CHARGE, 3),
            new EnchantmentIntegerPair(Enchantment.UNBREAKING, 3),
            new EnchantmentIntegerPair(Enchantment.MULTISHOT, 1),
            new EnchantmentIntegerPair(Enchantment.PIERCING, 4)
    }, 0.2),
    
    // Elytra
    ELYTRA(Material.ELYTRA, 1, 1, new EnchantmentIntegerPair[]{
            new EnchantmentIntegerPair(Enchantment.UNBREAKING, 3),
            new EnchantmentIntegerPair(Enchantment.MENDING, 1)
    }, 0.15),
    
    // Totem
    TOTEM_OF_UNDYING(Material.TOTEM_OF_UNDYING, 1, 2, null, 0.15),

    // Novos Itens Diversos
    // Blocos Raros
    BEACON(Material.BEACON, 1, 1, null, 0.15),
    
    CONDUIT(Material.CONDUIT, 1, 1, null, 0.15),

    // Itens de Redstone
    DISPENSER(Material.DISPENSER, 1, 1, null, 0.2),
    
    OBSERVER(Material.OBSERVER, 1, 1, null, 0.2),
    
    // Itens de Farm
    HONEY_BLOCK(Material.HONEY_BLOCK, 4, 8, null, 0.2),
    
    SLIME_BLOCK(Material.SLIME_BLOCK, 4, 8, null, 0.2),
    
    // Itens de Decoração
    SHROOMLIGHT(Material.SHROOMLIGHT, 4, 8, null, 0.2),
    
    SEA_LANTERN(Material.SEA_LANTERN, 4, 8, null, 0.2),
    
    // Itens de Transporte
    SADDLE(Material.SADDLE, 1, 1, null, 0.2),
    
    // Itens de Alquimia
    BLAZE_ROD(Material.BLAZE_ROD, 2, 6, null, 0.2),
    
    GHAST_TEAR(Material.GHAST_TEAR, 2, 7, null, 0.2),
    
    // Itens de Mobs
    WITHER_SKELETON_SKULL(Material.WITHER_SKELETON_SKULL, 1, 3, null, 0.25),
    
    // Itens de Exploração
    COMPASS(Material.COMPASS, 1, 1, null, 0.2),
    
    MAP(Material.MAP, 1, 1, null, 0.2),
    
    // Itens de Construção
    SPONGE(Material.SPONGE, 2, 4, null, 0.2),
    
    // Itens de Farm
    HOPPER(Material.HOPPER, 1, 2, null, 0.2),
    
    DROPPER(Material.DROPPER, 1, 2, null, 0.2),
    
    // Itens de Música
    MUSIC_DISC_OTHERSIDE(Material.MUSIC_DISC_OTHERSIDE, 1, 1, null, 0.15),
    
    // Itens de Iluminação
    SOUL_LANTERN(Material.SOUL_LANTERN, 2, 4, null, 0.2),
    
    // Itens de Armazenamento
    BUNDLE(Material.BUNDLE, 1, 1, null, 0.2),
    
    // Itens de Redstone Avançados
    SCULK_SENSOR(Material.SCULK_SENSOR, 1, 2, null, 0.15),
    
    // Itens de Farm Avançados
    HONEYCOMB_BLOCK(Material.HONEYCOMB_BLOCK, 2, 4, null, 0.2),
    
    // Itens de Decoração Avançados
    AMETHYST_BLOCK(Material.AMETHYST_BLOCK, 2, 4, null, 0.2),
    
    // Itens de Utilidade
    SPYGLASS(Material.SPYGLASS, 1, 1, null, 0.2),
    
    // Itens de Farm Especiais
    BEE_NEST(Material.BEE_NEST, 1, 1, null, 0.15),
    
    // Itens de Utilidade Especiais
    RECOVERY_COMPASS(Material.RECOVERY_COMPASS, 1, 1, null, 0.15);

    private final Material material;
    private final int minAmount;
    private final int maxAmount;
    private final BonusFeastLootTable.EnchantmentIntegerPair[] enchants;
    private final double chance;

    BonusFeastLootTable(Material material, int minAmount, int maxAmount, BonusFeastLootTable.EnchantmentIntegerPair[] enchants, double chance) {
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

    public BonusFeastLootTable.EnchantmentIntegerPair[] getEnchants() {
        return enchants;
    }

    public double getChance() {
        return chance;
    }

    public static class EnchantmentIntegerPair{
        private final Enchantment enchantment;
        private final int level;

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

    public static ItemStack generateItem(BonusFeastLootTable item){
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
            for(BonusFeastLootTable.EnchantmentIntegerPair entry : item.getEnchants()){
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
