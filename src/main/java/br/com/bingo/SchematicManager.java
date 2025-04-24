package br.com.bingo;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SchematicManager {

    public static void buildSchematic(String fileName, Location location, boolean isProtected) throws IOException {


        String diretorioDoServidor = Bukkit.getServer().getWorldContainer().getAbsolutePath();
        File file = new File(diretorioDoServidor + File.separator + "schematics", fileName);

        ClipboardFormat format = ClipboardFormats.findByFile(file);
        Clipboard clipboard = null;
        try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
            clipboard = reader.read();
        }

        try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(
                BukkitAdapter.adapt(location.getWorld()), -1)) {
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BlockVector3.at(location.getX(), location.getY(), location.getZ()))
                    .ignoreAirBlocks(false)
                    .build();
            Operations.complete(operation);



            if(isProtected){

                BlockVector3 origin = BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ());
                BlockVector3 clipboardOrigin = clipboard.getOrigin();

                for (BlockVector3 vector : clipboard.getRegion()) {
                    BlockVector3 relative = vector.subtract(clipboardOrigin);
                    BlockVector3 worldPosition = origin.add(relative);
                    Location blocLocation = new Location(location.getWorld(), worldPosition.x(), worldPosition.y(), worldPosition.z());
                    Bingo.getInstance().gameManager.blocosIndestrutiveis.add(blocLocation);
                }
            }
        } catch (WorldEditException e) {
            throw new RuntimeException(e);
        }
    }
}
