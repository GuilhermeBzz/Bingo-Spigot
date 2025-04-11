package br.com.bingo.kits.definitions;

import br.com.bingo.kits.Kit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;

public class Alchemist extends Kit {
    @Override
    public void giveKit(Player player) {
        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(generateRecipesGuide());

        addKitItems(items, player);
    }

    @Override
    public void startKit(Player player) {
        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(generateRecipesGuide());

        addKitItems(items, player);
    }

    @Override
    public void completeKit(Player player) {

    }


    private ItemStack generateRecipesGuide(){
        ItemStack book = new ItemStack(org.bukkit.Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();
        bookMeta.setDisplayName(ChatColor.GOLD + "Receitas e Formatos de Receitas");
        bookMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        bookMeta.setUnbreakable(true);
        bookMeta.setAuthor("BzCraft");
        bookMeta.setTitle("Receitas do Alquimista");

        bookMeta.addPage(ChatColor.GOLD + "3 Gold Ingot =>\n" +
                ChatColor.GOLD + "1 Diamond\n" +
                ChatColor.BLUE + "3 Iron Ingot =>\n" +
                ChatColor.BLUE + "1 Gold Ingot\n" +
                ChatColor.GOLD + "1 Andesite =>\n" +
                ChatColor.GOLD + "1 Granite\n" +
                ChatColor.BLUE + "1 Granite =>\n" +
                ChatColor.BLUE + "1 Diorite\n"+
                ChatColor.GOLD + "1 Diorite =>\n" +
                ChatColor.GOLD + "1 Andesite\n" +
                ChatColor.BLUE +"5 Diamond =>\n" +
                ChatColor.BLUE +"1 Netherite Scrap\n" );

        bookMeta.addPage(ChatColor.GOLD + "3 Rotten Flesh =>\n" +
                ChatColor.GOLD + "1 Leather\n" +
                ChatColor.BLUE +"8 String =>\n" +
                ChatColor.BLUE +"1 Lead\n"+
                ChatColor.GOLD + "1 Wheat =>\n" +
                ChatColor.GOLD + "1 Potato\n" +
                ChatColor.BLUE +"1 Potato =>\n" +
                ChatColor.BLUE +"1 Carrot\n" +
                ChatColor.GOLD + "1 Gravel =>\n" +
                ChatColor.GOLD + "1 Flint\n" +
                ChatColor.BLUE +"1 Slime Ball =>\n" +
                ChatColor.BLUE +"1 Magma Cream");

        bookMeta.addPage(ChatColor.GOLD + "1 Magma Cream =>\n" +
                ChatColor.GOLD + "1 Slime Ball\n" +
                ChatColor.BLUE +"1 Oak Sapling =>\n" +
                ChatColor.BLUE +"1 Birch Sapling\n" +
                ChatColor.GOLD + "1 Birch Sapling =>\n" +
                ChatColor.GOLD + "1 Spruce Sapling\n" +
                ChatColor.BLUE +"1 Spruce Sapling =>\n" +
                ChatColor.BLUE +"1 Jungle Sapling\n"+
                ChatColor.GOLD + "1 Jungle Sapling =>\n" +
                ChatColor.GOLD + "1 Acacia Sapling\n" +
                ChatColor.BLUE +"1 Acacia Sapling =>\n" +
                ChatColor.BLUE +"1 Dark Oak Sapling\n");

        bookMeta.addPage(ChatColor.GOLD + "3 Lapis Lazuli =>\n" +
                ChatColor.GOLD + "1 Emerald\n" +
                ChatColor.BLUE +"3 Redstone =>\n" +
                ChatColor.BLUE + "1 Lapis Lazuli\n"+
                ChatColor.GOLD + "3 Quartz =>\n" +
                ChatColor.GOLD + "1 Amethyst Shard\n" +
                ChatColor.BLUE + "9 Cobblestone =>\n" +
                ChatColor.BLUE + "9 Stone\n" +
                ChatColor.GOLD + "9 Stone =>\n" +
                ChatColor.GOLD + "9 Smooth Stone\n"+
                ChatColor.RED + "Formas =>");

        bookMeta.addPage("3 Items:\n"
                + "|_X_|\n"
                + "|_X_|\n"
                + "|_X_|\n");
        bookMeta.addPage("5 Items:\n"
                + "|_X_|\n"
                + "|XXX|\n"
                + "|_X_|\n");
        bookMeta.addPage("8 Items:\n"
                + "|XXX|\n"
                + "|X_X|\n"
                + "|XXX|\n");
        bookMeta.addPage("9 Items:\n"
                + "|XXX|\n"
                + "|XXX|\n"
                + "|XXX|\n");


        book.setItemMeta(bookMeta);

        return book;
    }
}
