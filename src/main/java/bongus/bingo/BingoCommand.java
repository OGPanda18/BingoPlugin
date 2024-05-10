package bongus.bingo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BingoCommand implements CommandExecutor {

    Bingo plugin;

    public BingoCommand(Bingo plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player p = (Player) sender;
        UUID uuid = p.getUniqueId();

        if(args.length == 1) {

            if (args[0].equalsIgnoreCase("new")) {
                BingoBoard.generateBoard(plugin.getItemList());
                return true;
            }

//            if (args[0].equalsIgnoreCase("open")) {
//                Bingo.playerBoards.get(p.getUniqueId()).openPersonalBoard();
//                return true;
//            }

            if (args[0].equalsIgnoreCase("start")) {
                plugin.getBingoUtil().startGame(p.getWorld());
                return true;
            }

//            if(args[0].equalsIgnoreCase("score")) {
//                Server server = Bukkit.getServer();
//                for(Player player : server.getOnlinePlayers()){
//                    server.broadcastMessage(player.getDisplayName() + ": " + ChatColor.GOLD + Bingo.playerBoards.get(player.getUniqueId()).score());
//                }
//            }

            if(args[0].equalsIgnoreCase("nether")){
                Bingo.nether = !Bingo.nether;
                p.sendMessage(ChatColor.RED + "Nether: " + Bingo.nether);
                return true;
            }

            if(args[0].equalsIgnoreCase("end")){
                plugin.getBingoUtil().endGame();
                return true;
            }

            try{
                Bingo.timeLimit = Integer.parseInt(args[0]);
                plugin.getBingoUtil().startGame(p.getWorld());
                return true;
            }catch(Exception e){

            }

        }




        return true;
    }
}
