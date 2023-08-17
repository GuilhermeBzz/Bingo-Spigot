package br.com.bingo.game;


import br.com.bingo.*;
import br.com.bingo.feast.Feast;
import br.com.bingo.feast.MiniFeast;
import br.com.bingo.kits.KitManager;
import br.com.bingo.kits.KitType;
import br.com.bingo.quests.Quest;
import br.com.bingo.quests.QuestManager;
import br.com.bingo.rank.LeaderBoard;
import br.com.bingo.rank.Ranks;
import br.com.bingo.rank.profile.PlayerProfile;
import br.com.bingo.team.TeamType;
import br.com.bingo.ui.BarTimer;
import br.com.bingo.ui.BingoMenu;
import br.com.bingo.ui.ScoreboardBingo;
import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


import java.io.File;
import java.util.*;

public class GameManager {
    Bingo plugin;
    UUID gameMaster;
    GameType gameType;
    GameStatus gameStatus;
    UUID teamLeader;

    public int gameDifficulty;

    KitManager kitManager;

    public Boolean kit;
    ScoreboardBingo scoreboardBingo;
    public Map<UUID, TeamType>  playerTeam = new HashMap<>();
    public Map<Quest, UUID>   playerQuests = new HashMap<>();
    public Map<Quest, TeamType>   teamQuests = new HashMap<>();
    public Map<Quest, Integer> questOrder = new HashMap<>();
    public Map<UUID, Integer> playerPoints = new HashMap<>();
    public Map<TeamType, Integer> teamPoints = new HashMap<>();
    public Map<UUID, Boolean> playerForfeit = new HashMap<>();
    public TeamType forfeitingTeam;

    public Feast feast;
    public MiniFeast miniFeast;
    public BarTimer barTimer;
    QuestManager questManager = new QuestManager();

    public Map<UUID, KitType> playerKit = new HashMap<>();
    public LastGame lastGame;

    public final int questLeftWhenChange = 13; //13
    public TeamType teamWinner;

    public boolean ranked;




    public GameManager(Bingo plugin) {
        Bukkit.getLogger().info("Iniciando GameManager...");
        this.plugin  = plugin;
        this.scoreboardBingo = new ScoreboardBingo(this);
        this.gameStatus = GameStatus.NONE;
        this.teamLeader = null;
        this.kit = false;
        this.barTimer = new BarTimer(this);
        this.feast = new Feast(this);
        this.miniFeast = new MiniFeast(this);
        this.lastGame = null;
        this.gameDifficulty = 5;
        this.teamWinner = null;
        this.ranked = false;
    }

    public void createCommand(Player sender, GameType gameType, boolean kitNew, int gameDifficulty, boolean rankedNew){
        if(existGame() || isGameStarted()){
            sender.sendMessage(ChatColor.RED + "Ja existe uma partida criada ou em andamento.");
            return;
        }

        this.kit = kitNew;
        this.gameDifficulty = gameDifficulty;
        this.ranked = rankedNew;

        if(gameType.equals(GameType.SOLO)){
            for(Player target : Bukkit.getServer().getOnlinePlayers()){
                addPlayerToTeam(target, TeamType.SOLO);
            }
        }
        else if(gameType.equals(GameType.TEAM_MANUAL)){
            sender.sendMessage(ChatColor.GREEN + "Escolha o outro Team Leader usando: " + ChatColor.YELLOW + "/bingo leader <player>");
            addPlayerToTeam(sender, TeamType.TEAM_RED);

        }
        else if(gameType.equals(GameType.TEAM_AUTO)){
            int iterator = 0;
            List<Player> players = new ArrayList<>(Bukkit.getServer().getOnlinePlayers());
            Collections.shuffle(players);
            for(Player target : players){
                if(iterator == 0){
                    addPlayerToTeam(target, TeamType.TEAM_RED);
                    iterator = 1;
                } else{
                    addPlayerToTeam(target, TeamType.TEAM_BLUE);
                    iterator = 0;
                }
            }
        }

        createGame(sender, gameType);

    }

    public void createGame(Player player, GameType gameType){

        Bukkit.broadcastMessage(ChatColor.GREEN + "Gerando mapa...");

            this.gameMaster = player.getUniqueId();
            this.gameStatus = GameStatus.CREATED;
            this.gameType = gameType;
            this.forfeitingTeam = null;
            this.teamWinner = null;


        generateWorlds();
        player.sendMessage("Partida Criada!");
        Bukkit.broadcastMessage(ChatColor.GREEN + "Dificuldade: " + ChatColor.YELLOW + gameDifficulty);
        String kitOnOff = "Desativados";
        if(this.kit) kitOnOff = "Ativados";
        Bukkit.broadcastMessage(ChatColor.GREEN + "Kits: " + ChatColor.YELLOW + kitOnOff);
        if(!gameType.equals(GameType.SOLO)){
            if(this.ranked) Bukkit.broadcastMessage(ChatColor.GREEN + "Partida Rankeada");
            else Bukkit.broadcastMessage(ChatColor.GREEN + "Partida Nao Rankeada");
        }


    }

    public void forfeitCommand(Player sender){
        if(forfeitingTeam == null){
            forfeitingTeam = playerTeam.get(sender.getUniqueId());
            if(forfeitingTeam.equals(TeamType.SOLO)){
                sender.sendMessage(ChatColor.RED + "Ainda nao implementado para partida solo.");
                forfeitingTeam = null;
                return;
            } else{
                for(UUID uuid : playerTeam.keySet()){
                    if(playerTeam.get(uuid).equals(forfeitingTeam)){
                       playerForfeit.put(uuid, false);
                    }
                }
                playerForfeit.put(sender.getUniqueId(), true);
                sender.sendMessage(ChatColor.RED + "Voce esta desistindo. Aguarde o resto do time");
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin,  new Runnable() {
                    @Override
                    public void run() {
                        for(UUID uuid: playerForfeit.keySet()){
                            Player player = Bukkit.getPlayer(uuid);
                            if(player != null && player.isOnline()){
                                player.sendMessage(ChatColor.RED + "Seu time nao desistiu. A partida continua");
                            }
                        }
                        forfeitingTeam = null;
                        playerForfeit.clear();
                    }
                }, 1200L);
                for(UUID uuid:playerForfeit.keySet()){
                    if(playerForfeit.get(uuid).equals(false)){
                        Player receiver = Bukkit.getPlayer(uuid);
                        if(receiver!= null && receiver.isOnline()){
                            receiver.sendMessage(ChatColor.RED + "Seu time esta desistindo. Use " + ChatColor.YELLOW + "/ff" + ChatColor.RED + " para concordar.");
                        }
                    }
                }
            }
        } else{
            if(playerTeam.get(sender.getUniqueId()).equals(forfeitingTeam)){
                playerForfeit.put(sender.getUniqueId(), true);
                sender.sendMessage(ChatColor.RED + "Voce aceitou a desistencia. Aguarde o resto do time");
            } else{
                sender.sendMessage("Voce nao pode desistir agora. Tente novamente em 1 minuto");
                return;
            }
        }
        boolean forfeit = false;
        for(UUID uuid : playerForfeit.keySet()){
            if(playerForfeit.get(uuid)){
                forfeit = true;
            } else{
                forfeit = false;
                break;
            }
        }
        if(forfeit) forfeitGame(forfeitingTeam);
        return;
    }
    public void forfeitGame(TeamType forfeitingTeam){
        for(UUID uuid : playerTeam.keySet()){
            Player player = Bukkit.getPlayer(uuid);
            if(player != null && player.isOnline()){
                player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1.0f, 1.0f);
                if(getPlayerTeam(player).equals(forfeitingTeam)){
                    player.sendTitle(ChatColor.RED + "Voce perdeu!", ChatColor.RED + "Seu time desistiu", 10, 70, 20);
                } else{
                    player.sendTitle(ChatColor.GREEN + "Voce ganhou!", ChatColor.GREEN + "O outro time desistiu", 10, 70, 20);
                }
                player.sendMessage("");
                player.sendMessage(ChatColor.GOLD + "-=-=-=-=-=-=- PLACAR FINAL -=-=-=-=-=-=-");
                if(forfeitingTeam.equals(TeamType.TEAM_RED)){
                    player.sendMessage(ChatColor.GOLD + "O " + ChatColor.RED +"Time Vermelho" + ChatColor.GOLD +  " desistiu");
                } else{
                    player.sendMessage(ChatColor.GOLD + "O " + ChatColor.BLUE +"Time Azul" + ChatColor.GOLD +  " desistiu");
                }
                player.sendMessage(ChatColor.GOLD + "Time Azul fez " + ChatColor.YELLOW + teamPoints.get(TeamType.TEAM_BLUE) + " Pontos");
                player.sendMessage(ChatColor.GOLD + "Time Vermelho fez " + ChatColor.YELLOW + teamPoints.get(TeamType.TEAM_RED) + " Pontos");
                player.sendMessage(ChatColor.GOLD + "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
                player.sendMessage("");
            }
        }
        if(forfeitingTeam.equals(TeamType.TEAM_RED)){
            teamWinner = TeamType.TEAM_BLUE;
        } else{
            teamWinner = TeamType.TEAM_RED;
        }
        forfeitingTeam = null;
        playerForfeit.clear();
        finishGame();
    }

    public void finishCommand(Player sender){
        if(!(sender.getUniqueId().equals(getGameMaster()))){
            sender.sendMessage(ChatColor.RED + "Voce nao tem permissao pra isso.");
            return;
        }
        if(isGameStarted()){
            endGame();
        }
    }
    public void finishGame(){
        this.gameStatus = GameStatus.FINISHED;
        Bukkit.getScheduler().cancelTasks(plugin);
        Bukkit.broadcastMessage("Use /tpp <player> para teletransportar para um jogador");
        Bukkit.broadcastMessage("Use /gm <gameMode> para mudar seu gamemode");
        for(UUID uuid : this.playerTeam.keySet()){
            Player player = Bukkit.getPlayer(uuid);
            if(player != null && player.isOnline()) {
                player.setGameMode(GameMode.CREATIVE);
            }
            HashMap<UUID, PermissionAttachment> perms = new HashMap<UUID, PermissionAttachment>();
            PermissionAttachment attachment = player.addAttachment(Bingo.getInstance());
            perms.put(player.getUniqueId(), attachment);
            PermissionAttachment pperms = perms.get(player.getUniqueId());
            pperms.setPermission("bingo.endgame", true);
            player.recalculatePermissions();
        }
        lastGame = new LastGame(this.gameType, this.playerQuests, this.teamQuests, this.questOrder, this.playerTeam, this.playerPoints, this.teamPoints, this.playerKit, this.teamWinner, this.ranked);
    }

    public void cancelCommand(Player sender){
        if(!sender.getUniqueId().equals(getGameMaster())){return;}
        Bukkit.broadcastMessage(ChatColor.RED + "Partida de bingo Cancelada por " + sender.getName());
        cancelGame();
    }
    public  void cancelCommand(){
        Bukkit.broadcastMessage(ChatColor.RED + "Partida de bingo Cancelada pelo Console ");
        cancelGame();
    }
    public void cancelGame(){
        Bukkit.getLogger().info("Cancelando Bingo...");

        for(UUID uuid : this.playerTeam.keySet()){
            Player player = Bukkit.getPlayer(uuid);
            if(player != null) {
                player.sendMessage(ChatColor.RED + "Partida Encerrada!");
                player.setBedSpawnLocation(Bukkit.getWorlds().get(0).getSpawnLocation(), true);
                player.setSaturation(20);
                player.setHealth(20);
                player.setFoodLevel(20);
                player.setLevel(0);
                for (PotionEffect effect : player.getActivePotionEffects()) {
                    player.removePotionEffect(effect.getType());
                }
                HashMap<UUID, PermissionAttachment> perms = new HashMap<UUID, PermissionAttachment>();
                PermissionAttachment attachment = player.addAttachment(Bingo.getInstance());
                perms.put(player.getUniqueId(), attachment);
                PermissionAttachment pperms = perms.get(player.getUniqueId());
                pperms.setPermission("bingo.endgame", false);
                player.recalculatePermissions();

            }
        }

        teleportPlayersToLobby();
        deleteWorlds();

        this.gameStatus = GameStatus.NONE;
        this.teamLeader = null;
        this.gameMaster = null;
        this.gameType = null;
        this.playerTeam = new HashMap<>();
        this.playerPoints = new HashMap<>();
        this.teamPoints = new HashMap<>();
        this.playerQuests = new HashMap<>();
        this.teamQuests = new HashMap<>();
        this.playerKit = new HashMap<>();
        this.kit = false;
        this.gameDifficulty = 5;
        this.ranked = false;
        Bukkit.getScheduler().cancelTasks(plugin);
        barTimer.stopBarTimer();
        feast.eraseFeast();
        miniFeast.eraseMiniFeast();
        LeaderBoard.createLeaderBoard();

        for(Player player : Bukkit.getOnlinePlayers()){
            player.setPlayerListName(player.getName());
            Ranks.setPrefixAndDisplayName(player, playerTeam);
        }

        if(scoreboardBingo != null) this.scoreboardBingo = new ScoreboardBingo(this);
    }

    public void startCommand(Player sender){
        if(!(sender.getUniqueId().equals(getGameMaster()))){
            sender.sendMessage(ChatColor.RED + "Voce nao é o Dono da partida.");
            return;
        }

        if(isGameStarted()){
            sender.sendMessage(ChatColor.RED + "Partida já iniciada.");
            return;
        }
        if(!existGame()){
            sender.sendMessage(ChatColor.RED + "Partida ainda não foi criada");
            return;
        }

        sender.sendMessage(ChatColor.GREEN + "Iniciando partida!");
        try {
            startGame();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Bukkit.getLogger().info("Iniciando partida...");
    }
    public void startGame() throws InterruptedException {

        if(this.kit && playerKit.size()!= playerTeam.size()){
            for(UUID uuid : playerTeam.keySet()){
                if(!playerKit.containsKey(uuid)){
                    Player player = Bukkit.getPlayer(uuid);
                    if(player != null) Bukkit.broadcastMessage(ChatColor.RED + player.getName() +" nao escolheu um kit!");
                }
            }
            return;
        }

        this.gameStatus = GameStatus.STARTED;
        questManager.initializeQuests(gameDifficulty);
        teleportPlayersToGame();
        World world = Bukkit.getWorld("gameWorld");
        world.setDifficulty(Difficulty.NORMAL);
        world.setPVP(false);
        world.setTime(0L);
        world.setGameRule(GameRule.DO_INSOMNIA, false);

        if(this.gameType == GameType.SOLO){
            for(Quest quest : questManager.availableQuests) playerQuests.put(quest, null);
            for(UUID uuid : playerTeam.keySet()) playerPoints.put(uuid, 0);

        } else if (this.gameType == GameType.TEAM_MANUAL || this.gameType == GameType.TEAM_AUTO) {
            for(Quest quest : questManager.availableQuests) teamQuests.put(quest, null);
            for(Quest quest : questManager.availableQuests) playerQuests.put(quest, null);
            teamPoints.put(TeamType.TEAM_RED, 0);
            teamPoints.put(TeamType.TEAM_BLUE, 0);


        }

        for(Quest quest : questManager.availableQuests) questOrder.put(quest, null);

        ItemStack cartela = new ItemStack(Material.PAPER);
        ItemMeta meta = cartela.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Cartela do Bingo");
        cartela.setItemMeta(meta);

        for(UUID uuid :playerTeam.keySet()){
            Player player = Bukkit.getPlayer(uuid);
            player.setGameMode(GameMode.ADVENTURE);
            for (PotionEffect effect : player.getActivePotionEffects()) {
                player.removePotionEffect(effect.getType());
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 99999, 10, false, false, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999, 100, false, false, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 99999, 200, false, false, false));
            player.setBedSpawnLocation(Bukkit.getWorld("gameWorld").getSpawnLocation(), true);
            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
            player.setLevel(0);
            player.getInventory().setItem(8, cartela);
        }
        scoreboardBingo.startScoreboard();
        countDownAndStart(10);

    }

    public void countDownAndStart(int seconds){
        World world = Bukkit.getWorld("gameWorld");

        if(seconds == 0){

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "recipe give @a *");
            barTimer.startBarTimer();
            for(UUID uuid : playerTeam.keySet()){
                Player player = Bukkit.getPlayer(uuid);
                barTimer.addPlayerToBarTimer(player);
                player.setGameMode(GameMode.SURVIVAL);

                player.playSound(player, Sound.ENTITY_WITHER_DEATH, 1.0f, 1.0f);
                player.removePotionEffect(PotionEffectType.BLINDNESS);
                player.removePotionEffect(PotionEffectType.SLOW);
                player.removePotionEffect(PotionEffectType.JUMP);
                player.sendTitle(ChatColor.GREEN + "Partida Iniciada!", ChatColor.AQUA + "Conclua as Quests da cartela para fazer pontos.",  10, 60, 10);


                player.getInventory().addItem(new ItemStack(Material.STONE_SWORD));
                player.getInventory().addItem(new ItemStack(Material.STONE_PICKAXE));
                player.getInventory().addItem(new ItemStack(Material.STONE_AXE));
                player.getInventory().addItem(new ItemStack(Material.STONE_SHOVEL));
                player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 16));
                player.getInventory().addItem(new ItemStack(Material.OAK_LOG, 8));
                player.setHealth(20);
                player.setFoodLevel(20);
                player.setSaturation(20);
                Bukkit.getLogger().info(ChatColor.GOLD + "Jogador: " + player.getName() + " - Time: " + playerTeam.get(player.getUniqueId()).toString());

                if(kit){
                    for(UUID uuid1 : playerKit.keySet()){
                        if(playerKit.get(uuid1).equals(KitType.SURPRISE)){
                            List<KitType> allKits = new ArrayList<>();
                            Collections.addAll(allKits, KitType.values());
                            allKits.remove(KitType.SURPRISE);
                            KitType kitType = allKits.get(new Random().nextInt(allKits.size()));
                            playerKit.put(uuid1, kitType);
                        }
                    }

                    player.sendMessage(ChatColor.WHITE + "Seu Kit é: " +ChatColor.YELLOW + playerKit.get(player.getUniqueId()).getKit().getName() + "!");
                    playerKit.get(player.getUniqueId()).getKit().startKit(player);
                }

                //player.getInventory().setItem(8, item);

                Iterator<Advancement> advancements = Bukkit.getServer().advancementIterator();
                while (advancements.hasNext()) {
                    AdvancementProgress progress = player.getAdvancementProgress(advancements.next());
                    for (String s : progress.getAwardedCriteria())
                        progress.revokeCriteria(s);
                }
            }
            Bukkit.broadcastMessage(ChatColor.GREEN + "O PvP sera liberado em 5 minutos!");

            Bukkit.getScheduler().scheduleSyncDelayedTask(Bingo.getInstance(),  new Runnable() {
                @Override
                public void run() {
                    feast.startFeast(world);

                }
            }, 30000L); // 30000L

            Bukkit.getScheduler().scheduleSyncDelayedTask(Bingo.getInstance(),  new Runnable() {
                @Override
                public void run() {
                    miniFeast.generateMiniFeast(world);

                }
            }, 54000L); // 54000L

            Bukkit.getScheduler().scheduleSyncDelayedTask(Bingo.getInstance(),  new Runnable() {
                @Override
                public void run() {
                    miniFeast.generateMiniFeast(world);

                }
            }, 18000L); // 18000L

            Bukkit.getScheduler().scheduleSyncDelayedTask(Bingo.getInstance(),  new Runnable() {
                @Override
                public void run() {
                    miniFeast.generateMiniFeast(world);

                }
            }, 24000L); // 24000L


            enablePvP(5);

        }else{
            for(UUID uuid :playerTeam.keySet()){
                Player player = Bukkit.getPlayer(uuid);
                player.sendTitle(ChatColor.GREEN + String.valueOf(seconds),"",  0, 20, 0);
                player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
            }
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin,  new Runnable() {
                @Override
                public void run(){
                    countDownAndStart(seconds -1);
                }
            }, 20L);
        }
    }

    public void enablePvP(int minutes){
        World world = Bukkit.getWorld("gameWorld");
        if(minutes == 0){
            world.setPVP(true);
            Bukkit.broadcastMessage(ChatColor.GREEN + "O PvP foi ativado!");
            for(UUID uuid : playerTeam.keySet()){
                Player player = Bukkit.getPlayer(uuid);
                if(player != null) player.playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1, 1);
            }
        } else{
            Bukkit.broadcastMessage(ChatColor.GREEN + String.valueOf(minutes) + " minutos para o PvP ser ativado!");
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin,  new Runnable() {
                @Override
                public void run() {
                    enablePvP(minutes - 1);
                }
            }, 1200L);
        }

    }

    public void setTeamLeader(Player target, Player player){
        if(!existTeamLeader() && !(target.getUniqueId() == getGameMaster())){
            this.teamLeader = target.getUniqueId();
            return;
        }
        player.sendMessage(ChatColor.RED + "Nao foi possivel adicionar " + target.getName() + " como Team Leader.");
    }

    public void addPlayerToTeam(Player player, TeamType teamType){
        if(!checkPlayerTeam(player)){
            this.playerTeam.put(player.getUniqueId(), teamType);
            String teamName = "";
            if(teamType == TeamType.TEAM_RED){
                teamName = ChatColor.RED + "Vermelho";
                player.setPlayerListName(ChatColor.RED + player.getName());
            } else if (teamType == TeamType.TEAM_BLUE) {
                teamName = ChatColor.BLUE + "Azul";
                player.setPlayerListName(ChatColor.BLUE + player.getName());
            } else if (teamType == TeamType.SOLO) {
                teamName = ChatColor.LIGHT_PURPLE + "Solo";
                player.setPlayerListName(ChatColor.LIGHT_PURPLE + player.getName());
            }
            player.sendMessage(ChatColor.GREEN + "Você foi adicionado ao Time " + teamName);

            if(this.kit) player.sendMessage(ChatColor.AQUA + "Escolha seu Kit no Menu");
        }
        Ranks.setPrefixAndDisplayName(player, playerTeam);

    }
    public void paintTAB(Player player){
        if(playerTeam.containsKey(player.getUniqueId())){
            TeamType teamType = playerTeam.get(player.getUniqueId());
            if(teamType == TeamType.TEAM_RED){
                player.setPlayerListName(ChatColor.RED + player.getName());
            } else if (teamType == TeamType.TEAM_BLUE) {
                player.setPlayerListName(ChatColor.BLUE + player.getName());
            } else if (teamType == TeamType.SOLO) {
                player.setPlayerListName(ChatColor.LIGHT_PURPLE + player.getName());
            }
        }
        Ranks.setPrefixAndDisplayName(player, playerTeam);
    }

    public boolean checkPlayerTeam(Player player){
        return this.playerTeam.containsKey(player.getUniqueId());
    }

    public TeamType getPlayerTeam(Player player){
        if(checkPlayerTeam(player)){
            return this.playerTeam.get(player.getUniqueId());
        }
        return null;
    }

    public boolean existGame(){
        return this.gameStatus != GameStatus.NONE;
    }

    public boolean isGameStarted(){
        return this.gameStatus == GameStatus.STARTED;
    }

    public boolean existTeamLeader(){
        return this.teamLeader != null;
    }

    public boolean leaderOrMaster(Player player){
        return player.getUniqueId() == this.teamLeader || player.getUniqueId() == this.gameMaster;
    }

    public UUID getGameMaster(){
        return this.gameMaster;
    }
    public GameType getGameType(){
        return this.gameType;
    }
    public GameStatus getGameStatus(){
        return this.gameStatus;
    }

    public void completeQuest(UUID uuid, Quest quest){

        questManager.availableQuests.remove(quest);
        if(getAvailableQuests().size() == questLeftWhenChange){
            if(getAvailableQuests().contains(Quest.QUESTION)){
                questManager.replaceQuest(Quest.QUESTION, Quest.KILL_PLAYER);
                questOrder.remove(Quest.QUESTION);
                questOrder.put(Quest.KILL_PLAYER, null);

                if(getGameType() == GameType.SOLO){
                    playerQuests.remove(Quest.QUESTION);
                    playerQuests.put(Quest.KILL_PLAYER, null);
                }
                else{
                    teamQuests.remove(Quest.QUESTION);
                    teamQuests.put(Quest.KILL_PLAYER, null);
                    playerQuests.remove(Quest.QUESTION);
                    playerQuests.put(Quest.KILL_PLAYER, null);
                }
                Bukkit.broadcastMessage(ChatColor.DARK_RED + "Nova Quest! " + ChatColor.YELLOW + Quest.KILL_PLAYER.getName());
            }

            if(kit){
                for(UUID uuidIterator : playerKit.keySet()){
                    playerKit.get(uuidIterator).getKit().completeKit(Bukkit.getPlayer(uuidIterator));
                }
            }

        }
        questOrder.replace(quest, 25 - getAvailableQuests().size());

        if(getGameType().equals(GameType.SOLO)){
            playerQuests.replace(quest, uuid);
            playerPoints.replace(uuid, playerPoints.get(uuid) + 1);
            for(UUID uuidIterator : playerTeam.keySet()){
                Player player = Bukkit.getPlayer(uuidIterator);
                if(player == null) continue;
                if(!player.isOnline()) continue;
                if(uuid.equals(uuidIterator)){
                    player.sendMessage(ChatColor.GREEN + "Voce concluiu a Quest " + ChatColor.YELLOW + quest.getName());
                    player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                } else{
                    player.sendMessage(ChatColor.RED + Bukkit.getPlayer(uuid).getName()  + " Concluiu a Quest " + ChatColor.YELLOW + quest.getName());
                    player.playSound(player, Sound.ENTITY_ENDER_DRAGON_AMBIENT, 1.0f, 1.0f);
                }
            }
            scoreboardBingo.updateScoreboard();
           if(questManager.availableQuests.isEmpty()){
               List<Map.Entry<UUID, Integer>> entries = new ArrayList<>(playerPoints.entrySet());
               entries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
               for(UUID uuidIterator : playerTeam.keySet()){
                   Player player = Bukkit.getPlayer(uuidIterator);
                   if(player == null) continue;

                   int count = 0;
                   for(Map.Entry<UUID, Integer> entry : entries){
                       if(count>= 3) break;
                       if(count == 0){
                           if(uuidIterator == entry.getKey()){
                               player.sendTitle(ChatColor.GREEN + "Você Ganhou!", ChatColor.AQUA + "Seu time fez " + playerPoints.get(uuidIterator) + ChatColor.AQUA + " Pontos.",  10, 60, 10);
                           }else{
                               player.sendTitle(ChatColor.RED + "Você Perdeu!", ChatColor.AQUA + "Seu time fez " + playerPoints.get(uuidIterator) + ChatColor.AQUA + " Pontos.",  10, 60, 10);
                               player.sendMessage(ChatColor.RED + Bukkit.getPlayer(entry.getKey()).getName() + " Ganhou com " + playerPoints.get(entry.getKey()) + " Pontos");
                           }
                           player.sendMessage("");
                           player.sendMessage(ChatColor.GOLD + "-=-=-=-=-=-=- PLACAR FINAL -=-=-=-=-=-=-");
                       }
                       player.sendMessage(ChatColor.GOLD + String.valueOf(count +1) + "o - " + Bukkit.getPlayer(entry.getKey()).getName() + " com "+ playerPoints.get(entry.getKey()) + " Pontos");
                       count++;
                   }
                   player.sendMessage(ChatColor.GOLD + "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
                   player.sendMessage("");
               }

               finishGame();
           }

        }
        else if (getGameType().equals(GameType.TEAM_MANUAL) || getGameType().equals(GameType.TEAM_AUTO)) {
            Bukkit.getLogger().info("GameType Team...");
            teamQuests.replace(quest, playerTeam.get(uuid));
            playerQuests.replace(quest, uuid);
            teamPoints.replace(playerTeam.get(uuid), teamPoints.get(playerTeam.get(uuid)) + 1);
            for(UUID uuidIterator : playerTeam.keySet()){
                Player player = Bukkit.getPlayer(uuidIterator);
                if(player == null || !player.isOnline()) continue;
                Player completer = Bukkit.getPlayer(uuid);
                if(getPlayerTeam(completer).equals(getPlayerTeam(player))){
                    player.sendMessage(ChatColor.GREEN + completer.getName() +" concluiu a Quest " + ChatColor.YELLOW + quest.getName());
                    player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                } else{
                    player.sendMessage(ChatColor.RED + completer.getName() +" concluiu a Quest " + ChatColor.YELLOW + quest.getName());
                    player.playSound(player, Sound.ENTITY_ENDER_DRAGON_AMBIENT, 1.0f, 1.0f);
                }
            }
            scoreboardBingo.updateScoreboard();

            for(TeamType teamType : teamPoints.keySet()){
                if(teamPoints.get(teamType) >= 13){
                    Bukkit.getLogger().info("Algum time ganhou...");
                    teamWinner = teamType;
                    for(UUID uuidIterator : playerTeam.keySet()){
                        Player player = Bukkit.getPlayer(uuidIterator);
                        if(player == null) continue;
                        if(!player.isOnline()) continue;
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1.0f, 1.0f);
                        if(playerTeam.get(uuidIterator) == teamType){
                            player.sendTitle(ChatColor.GREEN + "Voce ganhou!", ChatColor.AQUA + "Seu time fez " + teamPoints.get(playerTeam.get(player.getUniqueId())) + " pontos!",  10, 60, 10);
                        }
                        else{
                            player.sendTitle(ChatColor.DARK_RED + "Voce Perdeu!", ChatColor.AQUA + "Seu time fez " + teamPoints.get(playerTeam.get(player.getUniqueId())) + " pontos!",  10, 60, 10);
                        }
                        player.sendMessage("");
                        player.sendMessage(ChatColor.GOLD + "-=-=-=-=-=-=- PLACAR FINAL -=-=-=-=-=-=-");
                        player.sendMessage(ChatColor.GOLD + "Time Azul fez " + ChatColor.YELLOW + teamPoints.get(TeamType.TEAM_BLUE) + " Pontos");
                        player.sendMessage(ChatColor.GOLD + "Time Vermelho fez " + ChatColor.YELLOW + teamPoints.get(TeamType.TEAM_RED) + " Pontos");
                        player.sendMessage(ChatColor.GOLD + "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
                        player.sendMessage("");
                    }
                    finishGame();
                }

            }
        }
    }

    //usado no comando finish
    public void endGame(){
        if(gameType.equals(GameType.TEAM_MANUAL) || gameType.equals(GameType.TEAM_AUTO)){
            for(UUID uuid : playerTeam.keySet()){
                Player player = Bukkit.getPlayer(uuid);
                player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1.0f, 1.0f);
                player.sendTitle(ChatColor.GOLD + "Fim de Jogo!", ChatColor.AQUA + "Seu time fez " + teamPoints.get(playerTeam.get(player.getUniqueId())) + " pontos!",  10, 60, 10);
                player.sendMessage("");
                player.sendMessage(ChatColor.GOLD + "-=-=-=-=-=-=- PLACAR FINAL -=-=-=-=-=-=-");
                player.sendMessage(ChatColor.BLUE + "Time Azul fez " + ChatColor.YELLOW + teamPoints.get(TeamType.TEAM_BLUE) + ChatColor.BLUE +" pontos!");
                player.sendMessage(ChatColor.RED + "Time Vermelho fez " + ChatColor.YELLOW + teamPoints.get(TeamType.TEAM_RED) + ChatColor.RED +" pontos!");
                player.sendMessage(ChatColor.GOLD + "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
                player.sendMessage("");
            }
        } else if (gameType.equals(GameType.SOLO)) {
            for(UUID uuid : playerTeam.keySet()){
                Player player = Bukkit.getPlayer(uuid);
                if(player == null) continue;
                if(!player.isOnline()) continue;
                player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1.0f, 1.0f);
                player.sendTitle(ChatColor.GOLD + "Fim de Jogo!", ChatColor.AQUA + "Você fez " + playerPoints.get(player.getUniqueId()) + " pontos!",  10, 60, 10);
                player.sendMessage("");
                player.sendMessage(ChatColor.GOLD + "-=-=-=-=-=-=- PLACAR FINAL -=-=-=-=-=-=-");
                //show every player points
                for(UUID uuid2 : playerTeam.keySet()){
                    Player player2 = Bukkit.getPlayer(uuid2);
                    if(player2 == null) continue;
                    if(!player2.isOnline()) continue;
                    player.sendMessage(ChatColor.GOLD + player2.getName() + " fez " + ChatColor.YELLOW + playerPoints.get(player2.getUniqueId()) + ChatColor.GOLD +" pontos!");
                }
                player.sendMessage(ChatColor.GOLD + "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
                player.sendMessage("");
            }
        }

        finishGame();
    }

    public List<Quest> getAvailableQuests(){
        return  questManager.availableQuests;
    }


    public void updateScoreboard(){scoreboardBingo.updateScoreboard();}


    public void generateWorlds() {
        Bukkit.broadcastMessage(ChatColor.AQUA + "Gerando Overworld...");
        WorldCreator overworldCreator = new WorldCreator("gameWorld");
        overworldCreator.environment(World.Environment.NORMAL);
        overworldCreator.type(WorldType.NORMAL);
        World overworld = overworldCreator.createWorld();

        Bukkit.broadcastMessage(ChatColor.AQUA + "Gerando chunks do Spawn...");
        for (int chunkX = -2; chunkX <= 2; chunkX++) {
            for (int chunkZ = -2; chunkZ <= 2; chunkZ++) {
                overworld.loadChunk(chunkX, chunkZ, true);
            }
        }

        Bukkit.broadcastMessage(ChatColor.AQUA + "Gerando Nether...");
        WorldCreator netherCreator = new WorldCreator("gameWorld_nether");
        netherCreator.environment(World.Environment.NETHER);
        World nether = netherCreator.createWorld();

        Bukkit.broadcastMessage(ChatColor.AQUA + "Gerando End...");
        WorldCreator endCreator = new WorldCreator("gameWorld_the_end");
        endCreator.environment(World.Environment.THE_END);
        World end = endCreator.createWorld();

        Bukkit.broadcastMessage(ChatColor.AQUA + "Mundo gerado com sucesso!");

    }

    public void teleportPlayersToGame() {
        Random rand = new Random();
        for (UUID uuid : playerTeam.keySet()) {
            Player player = Bukkit.getPlayer(uuid);

            Location destiny = Bukkit.getWorld("gameWorld").getSpawnLocation();
            int x = destiny.getBlockX() + rand.nextInt(10);
            int z = destiny.getBlockZ() + rand.nextInt(10);
            int y = Bukkit.getWorld("gameWorld").getHighestBlockYAt(x, z);
            Location finalDestiny = new Location(Bukkit.getWorld("gameWorld"), x, y, z);
            if(finalDestiny.getBlock().getType().equals(Material.WATER) || finalDestiny.getBlock().getType().equals(Material.LAVA)){
                finalDestiny.getBlock().setType(Material.GRASS);
            }

            if(player != null) player.teleport(finalDestiny);
        }
    }

    public void teleportPlayersToLobby() {
        for (UUID uuid : playerTeam.keySet()) {
            Player player = Bukkit.getPlayer(uuid);
            if(player != null) {
                player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
                player.setGameMode(GameMode.ADVENTURE);
                player.getInventory().clear();
                BingoMenu.giveMenuOpener(player);
                PlayerProfile.givePorfileOpener(player);
            }
        }
    }

    public void deleteWorlds() {
        Bukkit.unloadWorld("gameWorld", false);
        Bukkit.unloadWorld("gameWorld_nether", false);
        Bukkit.unloadWorld("gameWorld_the_end", false);

        File worldFolder = new File("gameWorld");
        File worldNetherFolder = new File("gameWorld_nether");
        File worldEndFolder = new File("gameWorld_the_end");

        deleteFolder(worldFolder);
        deleteFolder(worldNetherFolder);
        deleteFolder(worldEndFolder);

        Bukkit.getLogger().info("Os mundos foram deletados com sucesso!");
    }

    private void deleteFolder(File worldFolder) {
        File[] files = worldFolder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFolder(file);
                } else {
                    file.delete();
                }
            }
        }
        worldFolder.delete();
    }

    public boolean testIfWorldExist(String world){
        File worldFolder = new File(world);
        return worldFolder.exists();
    }

    public boolean hasLastGame(){
        return(lastGame != null);
    }

    public void teleportToGameWorld(Player player){
        if(isGameStarted()) {
            if(player.getWorld().getName().equals("gameWorld_nether") || player.getWorld().getName().equals("gameWorld_the_end") || player.getWorld().getName().equals("gameWorld"))
                player.sendMessage(ChatColor.RED + "Você já está no mundo do jogo!");
            else player.teleport(Bukkit.getWorld("gameWorld").getSpawnLocation());
        }
        else player.sendMessage(ChatColor.RED + "O jogo ainda não começou!");
    }
    public void teleportToDefaultWorld(Player player){
        if(isGameStarted() && !(getGameMaster().equals(player.getUniqueId()))){
            player.sendMessage(ChatColor.RED + "O jogo já começou!");
            return;
        }
        player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
    }

}


