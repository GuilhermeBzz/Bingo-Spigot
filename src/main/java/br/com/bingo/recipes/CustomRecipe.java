package br.com.bingo.recipes;

import br.com.bingo.Bingo;
import br.com.bingo.utils.Quadruple;
import org.apache.commons.lang3.tuple.Triple;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;




public class CustomRecipe {

    public static void registerRecipes(Bingo plugin){
        new CustomRecipe().blacksmithRecipes(plugin);
    }

    private void blacksmithRecipes(Bingo plugin){
        Map<Material, Material> smeltingMap = Map.of(
                Material.IRON_ORE, Material.IRON_INGOT,
                Material.GOLD_ORE, Material.GOLD_INGOT,
                Material.COPPER_ORE, Material.COPPER_INGOT,
                Material.DEEPSLATE_IRON_ORE, Material.IRON_INGOT,
                Material.DEEPSLATE_GOLD_ORE, Material.GOLD_INGOT,
                Material.DEEPSLATE_COPPER_ORE, Material.COPPER_INGOT,
                Material.RAW_IRON, Material.IRON_INGOT,
                Material.RAW_COPPER, Material.COPPER_INGOT,
                Material.RAW_GOLD, Material.GOLD_INGOT
        );

        for(Map.Entry<Material, Material> entry : smeltingMap.entrySet()){
            ItemStack ore = new ItemStack(entry.getKey());
            ItemStack ingot = new ItemStack(entry.getValue());

            ShapelessRecipe recipe = new ShapelessRecipe(new NamespacedKey(plugin, entry.getKey().name() + "_to_" + entry.getValue().name()), ingot);
            recipe.addIngredient(ore.getType());
            plugin.getServer().addRecipe(recipe);
        }
    }

    private void AlchemistRecipes(Bingo plugin){
        ArrayList<Quadruple<Integer, Material, Integer, Material>> recipes = new ArrayList<>();
        recipes.add(new Quadruple<>(3, Material.IRON_INGOT, 1 , Material.GOLD_INGOT));
        recipes.add(new Quadruple<>(3, Material.GOLD_INGOT, 1 , Material.DIAMOND));
        recipes.add(new Quadruple<>(1, Material.ANDESITE, 1 , Material.GRANITE));
        recipes.add(new Quadruple<>(1, Material.GRANITE, 1 , Material.DIORITE));
        recipes.add(new Quadruple<>(1, Material.DIORITE, 1 , Material.ANDESITE));
        recipes.add(new Quadruple<>(5, Material.DIAMOND, 1 , Material.NETHERITE_SCRAP));
        recipes.add(new Quadruple<>(3, Material.ROTTEN_FLESH, 1 , Material.LEATHER));
        recipes.add(new Quadruple<>(8, Material.STRING, 1 , Material.LEAD));
        recipes.add(new Quadruple<>(1, Material.POTATO, 1 , Material.POISONOUS_POTATO));
        recipes.add(new Quadruple<>(1, Material.GRAVEL, 1 , Material.FLINT));
        recipes.add(new Quadruple<>(1, Material.SLIME_BALL, 1 , Material.MAGMA_CREAM));
        recipes.add(new Quadruple<>(1, Material.OAK_SAPLING, 1 , Material.BIRCH_SAPLING));
        recipes.add(new Quadruple<>(1, Material.BIRCH_SAPLING, 1 , Material.SPRUCE_SAPLING));
        recipes.add(new Quadruple<>(1, Material.SPRUCE_SAPLING, 1 , Material.JUNGLE_SAPLING));
        recipes.add(new Quadruple<>(1, Material.JUNGLE_SAPLING, 1 , Material.ACACIA_SAPLING));
        recipes.add(new Quadruple<>(1, Material.ACACIA_SAPLING, 1 , Material.DARK_OAK_SAPLING));
        recipes.add(new Quadruple<>(3, Material.LAPIS_LAZULI, 1 , Material.EMERALD));
        recipes.add(new Quadruple<>(3, Material.REDSTONE, 1 , Material.LAPIS_LAZULI));
        recipes.add(new Quadruple<>(3, Material.QUARTZ, 1 , Material.AMETHYST_SHARD));



    }
}
