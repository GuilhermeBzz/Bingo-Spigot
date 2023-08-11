package br.com.bingo;

import br.com.bingo.commands.*;
import br.com.bingo.game.GameManager;
import br.com.bingo.kits.listeners.*;
import br.com.bingo.listener.*;
import br.com.bingo.listener.quest.*;
import br.com.bingo.rank.LeaderBoard;
import br.com.bingo.rank.utils.match.MatchesStorageUtil;
import br.com.bingo.rank.utils.players.PlayersStorageUtil;
import br.com.bingo.rank.utils.quests.QuestsStorageUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class Bingo extends JavaPlugin {
    private static Bingo instance;
    GameManager gameManager;

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("Iniciando Plugin....");
        instance = this;

        gameManager = new GameManager(this);

        if(gameManager.testIfWorldExist("gameWorld")) gameManager.deleteWorlds();

        try {
            MatchesStorageUtil.loadMatches();
            QuestsStorageUtil.loadQuests();
            PlayersStorageUtil.loadPlayers();
        } catch (IOException e) {
            e.printStackTrace();
        }


        getCommand("bingo").setExecutor(new BingoCommand(gameManager));
        getCommand("menu").setExecutor(new MenuCommand(gameManager));
        getCommand("teamchat").setExecutor(new TeamChatCommand(gameManager));
        getCommand("tc").setExecutor(new TeamChatCommand(gameManager));
        getCommand("ff").setExecutor(new ForfeitCommand(gameManager));
        getCommand("adm").setExecutor(new ConsoleCommand(gameManager));
        getCommand("invsee").setExecutor(new InvseeCommand());
        getCommand("gm").setExecutor(new GamemodeCommand());
        getCommand("tpp").setExecutor(new TpCommand());
        getCommand("profile").setExecutor(new ProfileCommand());
        getCommand("profiles").setExecutor(new ProfileListCommand());
        getCommand("updateleaderboard").setExecutor(new UpdataLeaderBoardCommand());

        getServer().getPluginManager().registerEvents(new InventoryListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new LastGameListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new MaterialListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new JoinListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new EntityListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new DieListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new LevelUpListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new AdvancementListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new EffectListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new EnchantListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new ShearListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new BedrockListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new PortalListener(), this);
        getServer().getPluginManager().registerEvents(new HunterListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new KillPlayerListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new SpawnListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new MenuListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new SpecialItemDropListener(), this);
        getServer().getPluginManager().registerEvents(new SpecialItemContainersListener(), this);
        getServer().getPluginManager().registerEvents(new KitListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new MinerListener(), this);
        getServer().getPluginManager().registerEvents(new SurvivorListener(), this);
        getServer().getPluginManager().registerEvents(new NetherExplorerListener(), this);
        getServer().getPluginManager().registerEvents(new KillCommandListener(), this);
        getServer().getPluginManager().registerEvents(new GamblerListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new NecromancerListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new PaoListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new JokerListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new SedexListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new FishermanListener(), this);
        getServer().getPluginManager().registerEvents(new ScoutListener(), this);
        getServer().getPluginManager().registerEvents(new CheckpointListener(), this);
        getServer().getPluginManager().registerEvents(new ArchitectListener(), this);
        getServer().getPluginManager().registerEvents(new EndermageListener(), this);
        getServer().getPluginManager().registerEvents(new CultivatorListener(), this);
        getServer().getPluginManager().registerEvents(new ProfileListener(), this);

        LeaderBoard.createLeaderBoard();
    }
    @Override
    public void onDisable() {
        LeaderBoard.clearEntities();

    }



    public static Bingo getInstance(){
        return instance;
    }

}

