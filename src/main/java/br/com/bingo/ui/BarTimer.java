package br.com.bingo.ui;

import br.com.bingo.Bingo;
import br.com.bingo.feast.Feast;
import br.com.bingo.game.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class BarTimer {

    GameManager gameManager;
    int time;
    BossBar bossBar;

    public BarTimer(GameManager gameManager) {
        bossBar = Bukkit.createBossBar("Title", BarColor.PINK, BarStyle.SOLID);
        this.gameManager = gameManager;
    }

    public void startBarTimer() {
        time = 0;
        bossBar.setVisible(true);
        updateBarTimer(time);
    }

    public void updateBarTimer(int currentTime){
        setBarTitle();
        Bukkit.getScheduler().scheduleSyncDelayedTask(Bingo.getInstance(),  new Runnable() {
            @Override
            public void run() {
                time++;
                updateBarTimer(time);

            }
        }, 20L);
    }

    public void stopBarTimer(){
        time = 0;
        bossBar.setVisible(false);
        bossBar.removeAll();
    }

    public void addPlayerToBarTimer(Player player){
        bossBar.addPlayer(player);
    }

    public void setBarTitle(){
        if(time <= (5*60)){
            int remaining = (5*60) - time;
            int min = remaining / 60;
            int sec = remaining % 60;
            bossBar.setTitle(ChatColor.GOLD + String.format("%02d", min) + ":" + String.format("%02d", sec) + ChatColor.GREEN + " para o PvP");
            bossBar.setProgress((double) remaining / (5*60));
            return;
        }
        if(time <= (25*60)){
            int min = time / 60;
            int sec = time % 60;
            bossBar.setTitle(ChatColor.GREEN + "Tempo de Partida: " + ChatColor.GOLD + String.format("%02d", min) + ":" + String.format("%02d", sec));
            bossBar.setProgress((double) 1);
            return;
        }
        if(time <= (35*60)){
            int remaining = (35*60) - time;
            int min = remaining / 60;
            int sec = remaining % 60;
            Location feastLocation = Feast.getFeastLocation();
            int x = feastLocation.getBlockX();
            int y = feastLocation.getBlockY();
            int z = feastLocation.getBlockZ();
            bossBar.setTitle(ChatColor.GREEN + "Tempo para o feast: " + ChatColor.GREEN + String.format("%02d", min) + ":" + String.format("%02d", sec) + ChatColor.GREEN + " em" + ChatColor.RED +" X: " + x + " Y: " + y + " Z: " + z + ChatColor.GREEN +"!");

            bossBar.setProgress((double) remaining / (600));
            return;
        }
        int min = time / 60;
        int sec = time % 60;
        bossBar.setTitle(ChatColor.GOLD + "Tempo de Partida: " + ChatColor.GREEN + String.format("%02d", min) + ":" + String.format("%02d", sec));
        bossBar.setProgress((double) 1);
    }


}
