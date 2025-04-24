package br.com.bingo;

import br.com.bingo.quests.boss.listener.BossQuestListener;
import br.com.bingo.commands.*;
import br.com.bingo.game.GameManager;
import br.com.bingo.kits.listeners.*;
import br.com.bingo.listener.*;
import br.com.bingo.listener.quest.*;
import br.com.bingo.quests.capture.listener.CaptureQuestListener;
import br.com.bingo.quests.domination.listener.DominationQuestListener;
import br.com.bingo.rank.LeaderBoard;
import br.com.bingo.rank.utils.match.MatchesStorageUtil;
import br.com.bingo.rank.utils.players.PlayersStorageUtil;
import br.com.bingo.rank.utils.quests.QuestsStorageUtil;
import br.com.bingo.recipes.CustomRecipe;
import br.com.bingo.web.WebService;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class Bingo extends JavaPlugin {
    private static Bingo instance;
    public GameManager gameManager;
    Map<UUID, String> playerOriginalName;

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("Iniciando Plugin....");
        instance = this;
        ServerConfig.getInstance().load();

        playerOriginalName = new HashMap<>();
        gameManager = new GameManager(this);
        if(gameManager.testIfWorldExist("gameWorld")) gameManager.deleteWorlds();

        try {
            MatchesStorageUtil.loadMatches();
            QuestsStorageUtil.loadQuests();
            PlayersStorageUtil.loadPlayers();
        } catch (IOException e) {
            e.printStackTrace();
        }

        CustomRecipe.registerRecipes(this);

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
        getCommand("bingoDebug").setExecutor(new BingoDebugCommand(gameManager));
        getCommand("spawnboss").setExecutor(new SpawnBossCommand());
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
        getServer().getPluginManager().registerEvents(new BlacksmithListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new ExplorerListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new SoulboundListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new PyroListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new BeastmasterListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new FullSetListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new BuildListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new AlchemistListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new ProtectedBLockListener(), this);
        getServer().getPluginManager().registerEvents(new BossQuestListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new DominationQuestListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new CaptureQuestListener(gameManager), this);

        LeaderBoard.createLeaderBoard();
    }
    @Override
    public void onDisable() {
        WebService.endGame(gameManager.gameWebId, false, null);
        LeaderBoard.clearEntities();

    }

    public Map<UUID, String> getPlayerOriginalNameMap(){
        return playerOriginalName;
    }

    public void setPlayerOriginalName(UUID uuid, String name){
        playerOriginalName.put(uuid, name);
    }

    public boolean isPlayerInOriginalName(UUID uuid){
        return playerOriginalName.containsKey(uuid);
    }

    public String getPlayerOriginalName(UUID uuid){
        return playerOriginalName.getOrDefault(uuid, "Error");
    }


    public static Bingo getInstance(){
        return instance;
    }

}

