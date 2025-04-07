package br.com.bingo.web;

import br.com.bingo.Bingo;
import br.com.bingo.quests.Quest;
import br.com.bingo.rank.models.players.PlayersData;
import br.com.bingo.rank.utils.players.PlayersStorageUtil;
import br.com.bingo.team.TeamType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class WebService {

    private static List<QuestDto> allQuests = new ArrayList<>();
    public static String urlBase = "https://minecraft-bingo-webapp.vercel.app";



    public static String startMatch(List<Quest> questList){
        CreateGameDto createGameDto = generateQuestDto(questList);
        allQuests = createGameDto.getQuests();
        String url = urlBase + "/api/games/start";
        String json = CreateGameDto.toJson(createGameDto);
        Bukkit.getLogger().info(json);
        String response = sendPostRequest(url, json);

        if(response == null) return null;

        String id =  extractJsonField(response, "id");
        Bukkit.getLogger().info("Jogo iniciado com id: " + id);
        return id;
    }

    public static String startMatchSolo(List<Quest> questList, List<Player> playerList){
        CreateGameSoloDto createGameSoloDto = generateQuestSoloDto(questList, playerList);
        allQuests = createGameSoloDto.getQuests();
        String url = urlBase + "/api/games/start";
        String json = CreateGameSoloDto.toJson(createGameSoloDto);
        Bukkit.getLogger().info(json);
        String response = sendPostRequest(url, json);

        if(response == null) return null;
        String id= extractJsonField(response, "id");
        Bukkit.getLogger().info("Jogo iniciado com id: " + id);
        return id;
    }

    public static void completeQuest(Quest quest, Player player, TeamType team, String gameId){
        if(gameId == null) return;

        String questName = quest.getName();

        QuestDto questDto = new QuestDto(questName);

        String url = urlBase + "/api/games/" + gameId + "/complete";
        Bukkit.getLogger().info(url);
        CompleteQuestDto completeQuestDto = new CompleteQuestDto();
        completeQuestDto.setGameId(gameId);
        completeQuestDto.setPlayerName(player.getName());
        completeQuestDto.setQuestName(questDto.getName());

        if(team == TeamType.TEAM_BLUE){
            completeQuestDto.setTeamName("Blue");
        } else if(team == TeamType.TEAM_RED){
            completeQuestDto.setTeamName("Red");
        } else{
            completeQuestDto.setTeamName("SOLO");
        }
        String json = CompleteQuestDto.toJson(completeQuestDto);
        Bukkit.getLogger().info(json);
        sendAsyncPostRequest(url, json);
        Bukkit.getLogger().info("Questa completada: " + quest.getName());
    }

    public static void endGame(String gameId, Boolean isRanked,  Map<UUID, TeamType> playerTeam){
        if(gameId == null) return;
        allQuests = null;
        EndGameDto endGameDto = new EndGameDto();
        endGameDto.setGameId(gameId);
        endGameDto.setRanked(isRanked);
        boolean isSoloOrNull = false;

        if(!isRanked || playerTeam == null ){
            endGameDto.setPlayerUpdates(new ArrayList<>());
            isSoloOrNull = true;
        }

        if(!isSoloOrNull && isRanked){
            for(UUID uuid : playerTeam.keySet()){
                if(!isSoloOrNull && playerTeam.get(uuid) == TeamType.SOLO){
                    isSoloOrNull = true;
                    endGameDto.setPlayerUpdates(new ArrayList<>());
                }
            }
        }
        if(!isSoloOrNull && isRanked){
            List<UUID> uuidList = new ArrayList<>(playerTeam.keySet());
            endGameDto.setPlayerUpdates(generatePlayerUpdateDto(uuidList));
        }

        String url = urlBase + ":" + "/api/games/" + gameId + "/end";

        String json = EndGameDto.toJson(endGameDto);
        Bukkit.getLogger().info(json);
        Bukkit.getLogger().info(url);
        String response = sendPostRequest(url, json);
        Bukkit.getLogger().info("Jogo finalizado com id: " + gameId);
    }

    public static void updateQuest(String gameId, Quest quest){
        UpdateQuestDto updateQuestDto = new UpdateQuestDto();
        updateQuestDto.setGameId(gameId);
        updateQuestDto.setNewQuestName(quest.getName());
        updateQuestDto.setQuestName(Quest.QUESTION.getName());
        String json = UpdateQuestDto.toJson(updateQuestDto);

        String url = urlBase + ":" + "/api/games/" + gameId + "/updateQuest";
        Bukkit.getLogger().info(json);
        sendAsyncPostRequest(url, json);
    }

    private static String sendPostRequest(String urlString, String jsonBody) {
        try {
            // Criar URL e abrir conexão
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            Bukkit.getLogger().info("Conectado a " + urlString);
            // Configurar requisição
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true); // Permite envio de corpo

            // Enviar JSON no corpo da requisição
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Obter código de resposta
            int responseCode = conn.getResponseCode();
            Bukkit.getLogger().info("Código de resposta: " + responseCode);

            // Ler resposta (ou erro, se houver)
            InputStream inputStream = (responseCode < 400) ? conn.getInputStream() : conn.getErrorStream();
            String jsonResponse = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

            conn.disconnect();
            return jsonResponse;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void sendAsyncPostRequest(String urlString, String jsonBody){
        Bukkit.getScheduler().runTaskAsynchronously(Bingo.getInstance(), () ->{
            String response = sendPostRequest(urlString, jsonBody);
            //Bukkit.getLogger().info(response);
        });
    }

    private static String extractJsonField(String json, String field) {
        String searchKey = "\"" + field + "\":";
        int startIndex = json.indexOf(searchKey);

        if (startIndex == -1) {
            return null; // Campo não encontrado
        }

        startIndex += searchKey.length(); // Pula o nome do campo
        while (startIndex < json.length() && (json.charAt(startIndex) == ' ' || json.charAt(startIndex) == '"')) {
            startIndex++; // Pula espaços e aspas iniciais
        }

        int endIndex = startIndex;
        while (endIndex < json.length() && json.charAt(endIndex) != ',' && json.charAt(endIndex) != '}' && json.charAt(endIndex) != '"') {
            endIndex++; // Captura o valor do campo
        }

        return json.substring(startIndex, endIndex);
    }

    private static CreateGameDto generateQuestDto(List<Quest> questList){
        List<QuestDto> questDtos = new ArrayList<>();
        for(Quest quest : questList){
            QuestDto questDto = new QuestDto(quest.getName());
            questDtos.add(questDto);
        }
        CreateGameDto createGameDto = new CreateGameDto();
        createGameDto.setQuests(questDtos);
        createGameDto.setTeam1Name("Red");
        createGameDto.setTeam2Name("Blue");
        return createGameDto;
    }

    private static CreateGameSoloDto generateQuestSoloDto(List<Quest> questList, List<Player> playerList){
        List<QuestDto> questDtos = new ArrayList<>();
        CreateGameSoloDto createGameSoloDto = new CreateGameSoloDto();
        for(Quest quest : questList){
            QuestDto questDto = new QuestDto(quest.getName());
            questDtos.add(questDto);
        }
        createGameSoloDto.setQuests(questDtos);

        List<String> playerStringList = new ArrayList<>();
        for(Player player : playerList){
            playerStringList.add(player.getName());
        }
        createGameSoloDto.setPlayers(playerStringList);

        return createGameSoloDto;
    }

    private static List<PlayerUpdateDto> generatePlayerUpdateDto(List<UUID> uuidList){
        List<PlayerUpdateDto> response = new ArrayList<>();
        for(UUID uuid : uuidList){
            Player player = Bukkit.getPlayer(uuid);
            PlayerUpdateDto playerUpdateDto = new PlayerUpdateDto();
            playerUpdateDto.setPlayerName(player.getName());
            playerUpdateDto.setPlayerId(uuid.toString());
            PlayersData playerData = PlayersStorageUtil.getPlayer(uuid.toString());
            playerUpdateDto.setMmr(playerData.getPoints());
            response.add(playerUpdateDto);
        }
        return response;
    }


}
