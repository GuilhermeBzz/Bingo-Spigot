package br.com.bingo.recipes;

import br.com.bingo.Bingo;
import br.com.bingo.utils.Quadruple;
import org.apache.commons.lang3.tuple.Triple;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;




public class CustomRecipe {

    public static void registerRecipes(Bingo plugin){

        new CustomRecipe().blacksmithRecipes(plugin);
        new CustomRecipe().customRecipesForAll(plugin);
        new CustomRecipe().alchemistRecipes(plugin);
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

    private void customRecipesForAll(Bingo plugin){
        chainRecipes(plugin);


    }

    private void chainRecipes(Bingo plugin){
        ShapedRecipe chainHelmetRecipe = new ShapedRecipe(new NamespacedKey(plugin, "chain_helmet"), new ItemStack(Material.CHAINMAIL_HELMET));
        chainHelmetRecipe.shape("XXX",
                "X X");
        chainHelmetRecipe.setIngredient('X', Material.CHAIN);
        plugin.getServer().addRecipe(chainHelmetRecipe);
        ShapedRecipe chainChestplateRecipe = new ShapedRecipe(new NamespacedKey(plugin, "chain_chestplate"), new ItemStack(Material.CHAINMAIL_CHESTPLATE));
        chainChestplateRecipe.shape("X X",
                "XXX",
                "XXX");
        chainChestplateRecipe.setIngredient('X', Material.CHAIN);
        plugin.getServer().addRecipe(chainChestplateRecipe);
        ShapedRecipe chainLeggingsRecipe = new ShapedRecipe(new NamespacedKey(plugin, "chain_leggings"), new ItemStack(Material.CHAINMAIL_LEGGINGS));
        chainLeggingsRecipe.shape("XXX",
                "X X",
                "X X");
        chainLeggingsRecipe.setIngredient('X', Material.CHAIN);
        plugin.getServer().addRecipe(chainLeggingsRecipe);
        ShapedRecipe chainBootsRecipe = new ShapedRecipe(new NamespacedKey(plugin, "chain_boots"), new ItemStack(Material.CHAINMAIL_BOOTS));
        chainBootsRecipe.shape("X X",
                "X X");
        chainBootsRecipe.setIngredient('X', Material.CHAIN);
        plugin.getServer().addRecipe(chainBootsRecipe);
    }

    private void alchemistRecipes(Bingo plugin){
        ArrayList<Quadruple<Integer, Material, Integer, Material>> recipes = new ArrayList<>();
        recipes.add(new Quadruple<>(3, Material.IRON_INGOT, 1 , Material.GOLD_INGOT));
        recipes.add(new Quadruple<>(3, Material.GOLD_INGOT, 1 , Material.DIAMOND));
        recipes.add(new Quadruple<>(1, Material.ANDESITE, 1 , Material.GRANITE));
        recipes.add(new Quadruple<>(1, Material.GRANITE, 1 , Material.DIORITE));
        recipes.add(new Quadruple<>(1, Material.DIORITE, 1 , Material.ANDESITE));
        recipes.add(new Quadruple<>(5, Material.DIAMOND, 1 , Material.NETHERITE_SCRAP));
        recipes.add(new Quadruple<>(3, Material.ROTTEN_FLESH, 1 , Material.LEATHER));
        recipes.add(new Quadruple<>(8, Material.STRING, 1 , Material.LEAD));
        recipes.add(new Quadruple<>(1, Material.WHEAT, 1 , Material.POTATO));
        recipes.add(new Quadruple<>(1, Material.POTATO, 1 , Material.CARROT));
        recipes.add(new Quadruple<>(1, Material.GRAVEL, 1 , Material.FLINT));
        recipes.add(new Quadruple<>(1, Material.SLIME_BALL, 1 , Material.MAGMA_CREAM));
        recipes.add(new Quadruple<>(1, Material.MAGMA_CREAM, 1 , Material.SLIME_BALL));
        recipes.add(new Quadruple<>(1, Material.OAK_SAPLING, 1 , Material.BIRCH_SAPLING));
        recipes.add(new Quadruple<>(1, Material.BIRCH_SAPLING, 1 , Material.SPRUCE_SAPLING));
        recipes.add(new Quadruple<>(1, Material.SPRUCE_SAPLING, 1 , Material.JUNGLE_SAPLING));
        recipes.add(new Quadruple<>(1, Material.JUNGLE_SAPLING, 1 , Material.ACACIA_SAPLING));
        recipes.add(new Quadruple<>(1, Material.ACACIA_SAPLING, 1 , Material.DARK_OAK_SAPLING));
        recipes.add(new Quadruple<>(1, Material.DARK_OAK_SAPLING, 1 , Material.OAK_SAPLING));
        recipes.add(new Quadruple<>(3, Material.LAPIS_LAZULI, 1 , Material.EMERALD));
        recipes.add(new Quadruple<>(3, Material.REDSTONE, 1 , Material.LAPIS_LAZULI));
        recipes.add(new Quadruple<>(3, Material.QUARTZ, 1 , Material.AMETHYST_SHARD));
        recipes.add(new Quadruple<>(9, Material.COBBLESTONE, 9 , Material.STONE));
        recipes.add(new Quadruple<>(9, Material.STONE, 9 , Material.SMOOTH_STONE));
        recipes.add(new Quadruple<>(1, Material.BROWN_MUSHROOM, 1 , Material.RED_MUSHROOM));
        recipes.add(new Quadruple<>(1, Material.RED_MUSHROOM, 1 , Material.BROWN_MUSHROOM));


        for(Quadruple<Integer, Material, Integer, Material> recipeGuide : recipes){
            createShapedRecipeAlchemist(recipeGuide, plugin);
        }
    }

    private void createShapedRecipeAlchemist(Quadruple<Integer,Material,Integer,Material> recipeGuide, Bingo plugin){


        if(recipeGuide.getFirst() == 1){
            ShapelessRecipe recipe = new       ShapelessRecipe(new NamespacedKey(plugin,
                    recipeGuide.getFirst() + "_" + recipeGuide.getSecond() +
                            "_to_"+
                    recipeGuide.getThird()+ "_" + recipeGuide.getFourth()),
                    new ItemStack(recipeGuide.getFourth()));

            recipe.addIngredient(recipeGuide.getSecond());
            plugin.getServer().addRecipe(recipe);
            return;
        }
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin,
                recipeGuide.getFirst() + "_" + recipeGuide.getSecond() +
                                        "_to_"+
                        recipeGuide.getThird()+ "_" + recipeGuide.getFourth()),
                new ItemStack(recipeGuide.getFourth(), recipeGuide.getThird()));

        if(recipeGuide.getFirst() == 3){
            recipe.shape(" X ",
                         " X ",
                         " X ");
        } else if(recipeGuide.getFirst() == 5){
            recipe.shape(" X ",
                         "XXX",
                         " X ");
        } else if(recipeGuide.getFirst() == 8){
            recipe.shape("XXX",
                         "X X",
                         "XXX");
        } else if(recipeGuide.getFirst() == 9){
            recipe.shape("XXX",
                         "XXX",
                         "XXX");
        }
        recipe.setIngredient('X', recipeGuide.getSecond());
        plugin.getServer().addRecipe(recipe);
    }
}
