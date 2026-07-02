package br.com.bingo.kits.definitions;

import br.com.bingo.Bingo;
import br.com.bingo.kits.Kit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Cartographer extends Kit {

    public static final String COMPASS_NAME = ChatColor.GOLD + "Cartographer's Compass";

    private ItemStack buildCompass() {
        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta meta = compass.getItemMeta();
        meta.setDisplayName(COMPASS_NAME);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        compass.setItemMeta(meta);
        return compass;
    }

    @Override
    public void giveKit(Player player) {
        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(buildCompass());
        addKitItems(items, player);
    }

    @Override
    public void startKit(Player player) {
        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(buildCompass());
        addKitItems(items, player);
    }

    @Override
    public void completeKit(Player player) {
        Bingo.getInstance().gameManager.getCartographerManager().onPhase2(player.getUniqueId());
        player.sendMessage(ChatColor.GOLD + "Cartografo evoluido: draft mais rapido (1 min) e com mais opcoes (5)!");
    }
}
