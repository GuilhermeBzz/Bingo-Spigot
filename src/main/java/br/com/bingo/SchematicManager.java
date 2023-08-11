package br.com.bingo;

import de.tr7zw.nbtapi.NBTFile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.data.BlockData;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class SchematicManager {

    public static void buildSchematic(String fileName, Location location) throws IOException {

        String diretorioDoServidor = Bukkit.getServer().getWorldContainer().getAbsolutePath();
        File file = new File(diretorioDoServidor + File.separator + "schematics", fileName);


        NBTFile nbt = new NBTFile(file);
        var height = nbt.getShort("Height");
        var width = nbt.getShort("Width");
        var length = nbt.getShort("Length");
        System.out.println(height + " " + width + " " + length);
        var metadata = nbt.getCompound("Metadata");
        System.out.println(metadata);
        var offsetX = metadata.getInteger("WEOffsetX");
        var offsetY = metadata.getInteger("WEOffsetY");
        var offsetZ = metadata.getInteger("WEOffsetZ");
        var paletteMax = nbt.getInteger("PaletteMax");
        var palette = nbt.getCompound("Palette");
        var blocks = nbt.getByteArray("BlockData");
        BlockData[] datas = new BlockData[paletteMax];
        for (String data : palette.getKeys()) {
            datas[palette.getInteger(data)] = Bukkit.getServer().createBlockData(data);
        }
        int baseX = location.getBlockX() + offsetX;
        int baseY = location.getBlockY() + offsetY;
        int baseZ = location.getBlockZ() + offsetZ;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < length; z++) {
                    new Location(location.getWorld(), baseX + x, baseY + y, baseZ + z)
                            .getBlock().setBlockData(datas[blocks[(y * length + z) * width + x]]);
                }
            }
        }

    }
}
