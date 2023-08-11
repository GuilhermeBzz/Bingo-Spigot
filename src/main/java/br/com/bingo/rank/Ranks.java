package br.com.bingo.rank;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum Ranks {

    Potato(ChatColor.YELLOW, Material.POISONOUS_POTATO, 0, 49, "Batata", 0),
    COAL_1(ChatColor.GRAY,Material.COAL, 50, 99, "Carvão |", 1),

    COAL_2(ChatColor.GRAY,Material.COAL, 100, 149, "Carvão ||", 2),
    COAL_3(ChatColor.GRAY,Material.COAL, 150, 199, "Carvão |||", 3),
    COPPER_1(ChatColor.BLUE,Material.COPPER_INGOT, 200, 249, "Cobre |", 1),
    COPPER_2(ChatColor.BLUE,Material.COPPER_INGOT, 250, 299, "Cobre ||", 2),
    COPPER_3(ChatColor.BLUE,Material.COPPER_INGOT, 300, 349, "Cobre |||", 3),
    IRON_1(ChatColor.WHITE,Material.IRON_INGOT, 350, 399, "Ferro |", 1),
    IRON_2(ChatColor.WHITE,Material.IRON_INGOT, 400, 449, "Ferro ||", 2),
    IRON_3(ChatColor.WHITE,Material.IRON_INGOT, 450, 499, "Ferro |||", 3),
    GOLD_1(ChatColor.GOLD,Material.GOLD_INGOT, 500, 549, "Ouro |", 1),
    GOLD_2(ChatColor.GOLD,Material.GOLD_INGOT, 550, 599, "Ouro ||", 2),
    GOLD_3(ChatColor.GOLD,Material.GOLD_INGOT, 600, 649, "Ouro |||", 3),
    DIAMOND_1(ChatColor.AQUA,Material.DIAMOND, 650, 699, "Diamante |", 1),
    DIAMOND_2(ChatColor.AQUA,Material.DIAMOND, 700, 749, "Diamante ||", 2),
    DIAMOND_3(ChatColor.AQUA,Material.DIAMOND, 750, 799, "Diamante |||", 3),
    EMERALD_1(ChatColor.DARK_GREEN,Material.EMERALD, 800, 849, "Esmeralda |", 1),
    EMERALD_2(ChatColor.DARK_GREEN,Material.EMERALD, 850, 899, "Esmeralda ||", 2),
    EMERALD_3(ChatColor.DARK_GREEN,Material.EMERALD, 900, 949, "Esmeralda |||", 3),
    NETHERITE_1(ChatColor.RED,Material.NETHERITE_INGOT, 950, 999, "Netherite |", 1),
    NETHERITE_2(ChatColor.RED,Material.NETHERITE_INGOT, 1000, 1049, "Netherite ||", 2),
    NETHERITE_3(ChatColor.RED,Material.NETHERITE_INGOT, 1050, 1099, "Netherite |||", 3),
    DRAGON_1(ChatColor.LIGHT_PURPLE,Material.DRAGON_EGG, 1100, 1149, "Dragão |", 1),
    DRAGON_2(ChatColor.LIGHT_PURPLE,Material.DRAGON_EGG, 1150, 1199, "Dragão ||", 2),
    DRAGON_3(ChatColor.LIGHT_PURPLE,Material.DRAGON_EGG, 1200, 1249, "Dragão |||", 3),
    MASTER(ChatColor.DARK_RED,Material.NETHER_STAR, 1250, 999999999, "Mestre", 0);


    private final ChatColor color;
    private final Material icon;
    private final int min;
    private final int max;
    private final String name;
    private final int tier;

    Ranks(ChatColor color, Material icon, int min, int max, String name, int tier) {
        this.color = color;
        this.icon = icon;
        this.min = min;
        this.max = max;
        this.name = name;
        this.tier = tier;
    }

    public Material getIcon() {
        return icon;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public String getName() {
        return name;
    }

    public int getTier() {
        return tier;
    }

    public static Ranks getRank(int xp) {
        for (Ranks rank : Ranks.values()) {
            if (xp >= rank.getMin() && xp <= rank.getMax()) {
                return rank;
            }
        }
        return null;
    }

    public ChatColor getColor() {
        return color;
    }

    public static Ranks getNextRank(Ranks rank) {
        for (Ranks r : Ranks.values()) {
            if (r.getMin() == rank.getMax() + 1) {
                return r;
            }
        }
        return null;
    }

    public static void openRankList(Player player){
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_RED + "Ranks");
        for (int i = 0; i < 27; i++) {
            inv.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
        }
        ItemStack potato = new ItemStack(Material.POISONOUS_POTATO);
        ItemMeta potatoMeta = potato.getItemMeta();
        potatoMeta.setDisplayName(ChatColor.YELLOW + "Batata");
        potatoMeta.setLore(java.util.Arrays.asList(ChatColor.GRAY + "XP: " + ChatColor.YELLOW + "0" + ChatColor.GRAY + "-" + ChatColor.YELLOW + "49"));
        potato.setItemMeta(potatoMeta);
        inv.setItem(0, potato);

        ItemStack coal = new ItemStack(Material.COAL);
        ItemMeta coalMeta = coal.getItemMeta();
        coalMeta.setDisplayName(ChatColor.GRAY + "Carvão");
        coalMeta.setLore(java.util.Arrays.asList(ChatColor.GRAY + "Tier I: " + ChatColor.YELLOW + "50" + ChatColor.GRAY + "-" + ChatColor.YELLOW + "99", ChatColor.GRAY + "Tier II: " + ChatColor.YELLOW + "100" + ChatColor.GRAY + "-" + ChatColor.YELLOW + "149", ChatColor.GRAY + "Tier III: " + ChatColor.YELLOW + "150" + ChatColor.GRAY + "-" + ChatColor.YELLOW + "199"));
        coal.setItemMeta(coalMeta);
        inv.setItem(1, coal);

        ItemStack copper = new ItemStack(Material.COPPER_INGOT);
        ItemMeta copperMeta = copper.getItemMeta();
        copperMeta.setDisplayName(ChatColor.BLUE + "Cobre");
        copperMeta.setLore(java.util.Arrays.asList(ChatColor.GRAY + "Tier I: " + ChatColor.YELLOW + "200" + ChatColor.GRAY + "-" + ChatColor.YELLOW + "249", ChatColor.GRAY + "Tier II: " + ChatColor.YELLOW + "250" + ChatColor.GRAY + "-" + ChatColor.YELLOW + "299", ChatColor.GRAY + "Tier III: " + ChatColor.YELLOW + "300" + ChatColor.GRAY + "-" + ChatColor.YELLOW + "349"));
        copper.setItemMeta(copperMeta);
        inv.setItem(2, copper);

        ItemStack iron = new ItemStack(Material.IRON_INGOT);
        ItemMeta ironMeta = iron.getItemMeta();
        ironMeta.setDisplayName(ChatColor.WHITE + "Ferro");
        ironMeta.setLore(java.util.Arrays.asList(ChatColor.GRAY + "Tier I: " + ChatColor.YELLOW + "350" + ChatColor.GRAY + "-" + ChatColor.YELLOW + "399", ChatColor.GRAY + "Tier II: " + ChatColor.YELLOW + "400" + ChatColor.GRAY + "-" + ChatColor.YELLOW + "449", ChatColor.GRAY + "Tier III: " + ChatColor.YELLOW + "450" + ChatColor.GRAY + "-" + ChatColor.YELLOW + "499"));
        iron.setItemMeta(ironMeta);
        inv.setItem(3, iron);

        ItemStack gold = new ItemStack(Material.GOLD_INGOT);
        ItemMeta goldMeta = gold.getItemMeta();
        goldMeta.setDisplayName(ChatColor.GOLD + "Ouro");
        goldMeta.setLore(java.util.Arrays.asList(ChatColor.GRAY + "Tier I: " + ChatColor.YELLOW + "500" + ChatColor.GRAY + "-" + ChatColor.YELLOW + "549", ChatColor.GRAY + "Tier II: " + ChatColor.YELLOW + "550" + ChatColor.GRAY + "-" + ChatColor.YELLOW + "599", ChatColor.GRAY + "Tier III: " + ChatColor.YELLOW + "600" + ChatColor.GRAY + "-" + ChatColor.YELLOW + "649"));
        gold.setItemMeta(goldMeta);
        inv.setItem(4, gold);

        ItemStack diamond = new ItemStack(Material.DIAMOND);
        ItemMeta diamondMeta = diamond.getItemMeta();
        diamondMeta.setDisplayName(ChatColor.AQUA + "Diamante");
        diamondMeta.setLore(java.util.Arrays.asList(ChatColor.GRAY + "Tier I: " + ChatColor.YELLOW + "650" + ChatColor.GRAY + "-" + ChatColor.YELLOW + "699", ChatColor.GRAY + "Tier II: " + ChatColor.YELLOW + "700" + ChatColor.GRAY + "-" + ChatColor.YELLOW + "749", ChatColor.GRAY + "Tier III: " + ChatColor.YELLOW + "750" + ChatColor.GRAY + "-" + ChatColor.YELLOW + "799"));
        diamond.setItemMeta(diamondMeta);
        inv.setItem(5, diamond);

        ItemStack emerald = new ItemStack(Material.EMERALD);
        ItemMeta emeraldMeta = emerald.getItemMeta();
        emeraldMeta.setDisplayName(ChatColor.GREEN + "Esmeralda");
        emeraldMeta.setLore(java.util.Arrays.asList(ChatColor.GRAY + "Tier I: " + ChatColor.YELLOW + "800" + ChatColor.GRAY + "-" + ChatColor.YELLOW + "849", ChatColor.GRAY + "Tier II: " + ChatColor.YELLOW + "850" + ChatColor.GRAY + "-" + ChatColor.YELLOW + "899", ChatColor.GRAY + "Tier III: " + ChatColor.YELLOW + "900" + ChatColor.GRAY + "-" + ChatColor.YELLOW + "949"));
        emerald.setItemMeta(emeraldMeta);
        inv.setItem(6, emerald);

        ItemStack netherite = new ItemStack(Material.NETHERITE_INGOT);
        ItemMeta netheriteMeta = netherite.getItemMeta();
        netheriteMeta.setDisplayName(ChatColor.RED + "Netherite");
        netheriteMeta.setLore(java.util.Arrays.asList(ChatColor.GRAY + "Tier I: " + ChatColor.YELLOW + "950" + ChatColor.GRAY + "-" + ChatColor.YELLOW + "999", ChatColor.GRAY + "Tier II: " + ChatColor.YELLOW + "1000" + ChatColor.GRAY + "-" + ChatColor.YELLOW + "1049", ChatColor.GRAY + "Tier III: " + ChatColor.YELLOW + "1050" + ChatColor.GRAY + "-" + ChatColor.YELLOW + "1099"));
        netherite.setItemMeta(netheriteMeta);
        inv.setItem(7, netherite);

        ItemStack dragon = new ItemStack(Material.DRAGON_EGG);
        ItemMeta dragonMeta = dragon.getItemMeta();
        dragonMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Dragão");
        dragonMeta.setLore(java.util.Arrays.asList(ChatColor.GRAY + "Tier I: " + ChatColor.YELLOW + "1100" + ChatColor.GRAY + "-" + ChatColor.YELLOW + "1149", ChatColor.GRAY + "Tier II: " + ChatColor.YELLOW + "1150" + ChatColor.GRAY + "-" + ChatColor.YELLOW + "1199", ChatColor.GRAY + "Tier III: " + ChatColor.YELLOW + "1200" + ChatColor.GRAY + "-" + ChatColor.YELLOW + "1249"));
        dragon.setItemMeta(dragonMeta);
        inv.setItem(8, dragon);

        ItemStack wither = new ItemStack(Material.NETHER_STAR);
        ItemMeta witherMeta = wither.getItemMeta();
        witherMeta.setDisplayName(ChatColor.DARK_RED + "Master");
        witherMeta.setLore(java.util.Arrays.asList(ChatColor.GRAY + "XP: " + ChatColor.GOLD + "1250" + ChatColor.GRAY + "+"));
        wither.setItemMeta(witherMeta);
        inv.setItem(13, wither);

        player.openInventory(inv);
    }
}
