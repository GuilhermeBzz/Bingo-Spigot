package br.com.bingo;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class ChangeLog {


    public static void openChangeLog(Player player){
        ItemStack book = new ItemStack(org.bukkit.Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();
        bookMeta.setAuthor("BzCraft");
        bookMeta.setTitle("Patch Notes");

        bookMeta.addPage(ChatColor.BLACK + "-=-=-=-=-=-=-=-=-=-\n" +
                ChatColor.BLUE +"      Patch Notes\n" +
                ChatColor.BLUE +"      03/08/2023\n" +
                ChatColor.BLACK + "-=-=-=-=-=-=-=-=-=-\n" +
                "§6§lBugs Corrigidos§0\n" +
                "- Joker com comportamento antigo, Player nascendo no Lobby\n" +
                "§6§lNether Explorer§0\n" +
                "- Bussola Corrigida! Agora aponta pra Bastion também!\n");

        bookMeta.addPage("§6§lCartela§0\n"+
                "- Agora a cartela informa a ordem em que as quests foram feitas e quem completou!\n\n" +
                "- Agora é possível ver a cartela da última partida no menu!");

        bookMeta.addPage(ChatColor.BLACK + "-=-=-=-=-=-=-=-=-=-\n" +
                ChatColor.BLUE +"      Patch Notes\n" +
                ChatColor.BLUE +"      02/08/2023\n" +
                ChatColor.BLACK + "-=-=-=-=-=-=-=-=-=-\n"+
                "§6§lEndermage§0\n" +
                "- Agora, começa a partida com uma perola infinita\n" +
                "§6§lBeastmaster §2§lBuff§0\n" +
                "- Recebe um ovo de 5 em 5 minutos\n" +
                "§0");

        bookMeta.addPage("§6§lSurvivor§0\n" +
                "- Não perde mais a carne, porém cooldown da maça aumentado§0\n"+
                "§2§lNovos Kits!§0\n" +
                "- Cultivator!\n" +
                "§2§lNova Surpresa!§0\n" +
                "- Jogue para descobrir!\n");

        bookMeta.addPage(ChatColor.BLACK + "-=-=-=-=-=-=-=-=-=-\n" +
                        ChatColor.BLUE +"      Patch Notes\n" +
                        ChatColor.BLUE +"      30/07/2023\n" +
                        ChatColor.BLACK + "-=-=-=-=-=-=-=-=-=-\n"+
                "§6§lJoker §4§lNerf§0\n" +
                "- Não remove mais itens dos inimigos na segunda fase\n" +
                        "§6§lSurvivor §2§lBuff§0\n" +
                        "- Agora, survivor aumenta sua saturação");
        bookMeta.addPage("§6§lGambler §4§lFix§0\n" +
                "- Corrigido Bug onde lista de kits não altera entre partidas\n" +
                "§6§lNecromancer §4§lNerf§0\n" +
                "- Agora, os jogadores são avisados 3 segundos antes de receber o ataque§0\n" +
                "§6§lHunter §4§lFix§0\n" +
                "- Corrigido problema onde a velocidade dada durava pouco§0");



        bookMeta.addPage(ChatColor.BLACK + "-=-=-=-=-=-=-=-=-=-\n" +
                                ChatColor.BLUE +"      Patch Notes\n" +
                                ChatColor.BLUE +"      27/07/2023\n" +
                                ChatColor.BLACK + "-=-=-=-=-=-=-=-=-=-\n"+
                "§2§lNovos Kits!§0\n" +
                "- Gampler, Pao, Scout, Checkpoint, Joker, Architect e Sedex!\n" +
                "\n" +
                "§6§lNovo Lobby!§r\n" +
                "- Nova decoração para o Lobby do jogo!§0");



        bookMeta.addPage(ChatColor.BLACK + "-=-=-=-=-=-=-=-=-=-\n" +
                                 ChatColor.BLUE +"      Patch Notes\n" +
                                 ChatColor.BLUE +"      23/07/2023\n" +
                                 ChatColor.BLACK + "-=-=-=-=-=-=-=-=-=-\n" +
                                 ChatColor.GOLD + "• Necromancer:\n" +
                                 ChatColor.BLACK + "Alvos agora recebem Slow na segunda etapa, porem menos mobs são gerados \n\n");



        bookMeta.addPage(
                ChatColor.BLACK + "-=-=-=-=-=-=-=-=-=-\n" +
                        ChatColor.BLUE +"      Patch Notes\n" +
                        ChatColor.BLUE +"      20/07/2023\n" +
                        ChatColor.BLACK + "-=-=-=-=-=-=-=-=-=-\n" +
                        ChatColor.GOLD + "• Aquaman:\n" +
                        ChatColor.BLACK + "Velocidade da Segunda Etapa Reduzida\n\n" +
                        ChatColor.GOLD + "• Miner:\n" +
                        ChatColor.BLACK + "Velocidade de mineração da Segunda Etapa Reduzida\n\n");
        bookMeta.addPage(
                ChatColor.BLACK + "• Itens Craftados ou pegos de baús agora completam Quests Automaticamente\n\n" +
                        ChatColor.BLACK + "• Corrigido bug onde Quests não eram possíveis de completar\n\n" +
                        ChatColor.BLACK + "• Patch Notes Adicionado =)\n\n" +
                                          "-=-=-=-=-=-=-=-=-=-");


        book.setItemMeta(bookMeta);
        player.openBook(book);

    }
}
