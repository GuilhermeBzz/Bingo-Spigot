package br.com.bingo.commands;

import br.com.bingo.quests.boss.entities.EntityBoss;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class SpawnBossCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!command.getName().equalsIgnoreCase("spawnboss")) return false;
        if(!(commandSender instanceof Player player)) return false;
        if(!player.isOp()){
            player.sendMessage("§cVocê não tem permissão para usar este comando.");
            return false;
        }
        Location location = player.getLocation();

        EntityBoss boss = new EntityBoss(location);
        boss.spawnBoss(location);

        return true;
    }

}
