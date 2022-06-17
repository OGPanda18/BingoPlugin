package bongus.bingo;

import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitTask;

import static bongus.bingo.Bingo.gameActive;

public class BingoUtil {

    private BossBar bossBar;
    private WorldBorder border;
    private Bingo plugin;
    private BukkitTask bossTimer;
    private BukkitTask startTimer;
    private BukkitTask gameTimer;

    public BingoUtil(Bingo plugin) {
        this.plugin = plugin;
    }

    public void startGame(World world){
        if(gameActive) endGame();

        gameActive = true;
        BingoBoard.generateBoard(plugin.getItemList());
        plugin.getPlayerBoards().clear();

        plugin.setGameLocation(randomLocation(world));

        // adds new BingoBoard for each person
        for(Player p : Bukkit.getServer().getOnlinePlayers()){
            plugin.getPlayerBoards().put(p.getUniqueId(), new BingoBoard(p.getUniqueId())); // <--- could this be the LEAK?
            plugin.getPlayerBoards().get(p.getUniqueId()).updatePersonalBoard();

            p.setGameMode(GameMode.ADVENTURE);
            p.getInventory().clear();
            p.setHealth(20);
            p.setFoodLevel(20);
            p.teleport(plugin.getGameLocation());


            ItemStack knowledge = new ItemStack(Material.KNOWLEDGE_BOOK);
            ItemMeta meta = knowledge.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + "Bingo Card");
            knowledge.setItemMeta(meta);

            p.getInventory().setItem(8, knowledge);

            world.setTime(1000);
            world.setStorm(false);
            world.setWeatherDuration(Bingo.timeLimit * 80);

        }
        createBossBar();
        border = world.getWorldBorder();
        border.setCenter(plugin.getGameLocation().getX(), plugin.getGameLocation().getZ());
        border.setSize(4);


        // inital waiting period for chunk loading
        startTimer = Bukkit.getScheduler().runTaskLater(plugin, () -> {
            border.reset();
            for(Player p : Bukkit.getServer().getOnlinePlayers()) {
                p.setGameMode(GameMode.SURVIVAL);
                p.setHealth(20);
                p.setFoodLevel(20);
                p.setLevel(0);
                p.setExp(0);
            }
        }, 400);

        gameTimer = Bukkit.getScheduler().runTaskLater(plugin, () -> {
            endGame();
        }, 400 + (Bingo.timeLimit * 20));
    }

    public void endGame(){
        if(bossBar != null) bossBar.removeAll();
        gameTimer.cancel();
        bossTimer.cancel();
        startTimer.cancel();

        Server server = Bukkit.getServer();
        for(Player player : server.getOnlinePlayers()){
            server.broadcastMessage(player.getDisplayName() + ": " + ChatColor.GOLD + plugin.getPlayerBoards().get(player.getUniqueId()).score());
        }

        gameActive = false;
    }

    public Location getHighestBlock(World world, int x, int z){
        Location locat = new Location(world, x, 255, z);
        for(int y = 255; y > 0; y--){
            locat.setY(y);
            if(!(locat.getBlock().getType() == Material.WATER || locat.getBlock().getType() == Material.LAVA)){
                if(!locat.getBlock().isPassable()) {
                    locat.add(0, 1, 0);
                    return locat;
                }
            }else{
                return randomLocation(world);
            }
        }

        return null;

    }
    public Location randomLocation(World world){
        return getHighestBlock(world,(int) ((Math.random() - 0.5) * 10000000),(int) ((Math.random() - 0.5) * 10000000));
    }

    public void createBossBar(){
        bossBar = Bukkit.createBossBar("Time Left", BarColor.WHITE, BarStyle.SOLID);
        bossBar.setProgress(1);
        Bingo.timeLeft = Bingo.timeLimit;

        for(Player p : Bukkit.getServer().getOnlinePlayers()){
            bossBar.addPlayer(p);
        }


        // update timer until time is up; end game
        bossTimer = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            double increment = 1.0/(Bingo.timeLimit);
            double newBar = bossBar.getProgress() - increment;
            if(newBar >= 0){
                bossBar.setProgress(newBar);
                bossBar.setTitle((Bingo.timeLeft/60) + ":" + String.format("%02d",Bingo.timeLeft % 60));
                Bingo.timeLeft--;
            }
            else{ bossBar.removeAll(); bossTimer.cancel(); }
        },400 + 1, 1);

    }
}
