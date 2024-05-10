package bongus.bingo;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class Bingo extends JavaPlugin {

    public static boolean nether;
    public static boolean gameActive;
    public static int timeLimit;
    public static int timeLeft;
    public BingoUtil bingoUtil;
    public Location gameLocation;
    public Map<UUID, BingoBoard> playerBoards = new HashMap<>();

    private ArrayList<Material> itemList = new ArrayList<>();
    private Material[] itemArray = {
            Material.GOLDEN_APPLE,
            Material.DIAMOND,
            Material.IRON_BLOCK,
            Material.GOLD_BLOCK,
            Material.COPPER_BLOCK,
            Material.AMETHYST_SHARD,
            Material.SPYGLASS,
            Material.GLOW_LICHEN,
            Material.GLOW_INK_SAC,
            Material.CARROT,
            Material.POTATO,
//            Material.BEETROOT,
            Material.LEATHER_CHESTPLATE,
//            Material.PUMPKIN,
            Material.CROSSBOW,
//            Material.FERMENTED_SPIDER_EYE,
            Material.TNT,
            Material.POWERED_RAIL,
            Material.APPLE,
            Material.GOLDEN_CARROT,
            Material.JACK_O_LANTERN,
            Material.OBSIDIAN,
            Material.REPEATER,
//            Material.HONEY_BOTTLE,
//            Material.HONEYCOMB,
            Material.CARROT_ON_A_STICK,
//            Material.HOPPER_MINECART,
//            Material.TNT_MINECART,
            Material.EMERALD,
            Material.LAPIS_BLOCK,
            Material.ENCHANTING_TABLE,
            Material.MILK_BUCKET,
            Material.PISTON,
//            Material.POWDER_SNOW_BUCKET,
            Material.LANTERN,
            Material.CAMPFIRE,
            Material.MAP,
            Material.CLOCK,
            Material.COMPASS,
//            Material.PURPLE_TERRACOTTA,
            Material.ORANGE_BED,
            Material.LIGHT_GRAY_STAINED_GLASS,
            Material.BLACK_GLAZED_TERRACOTTA,
            Material.RED_CONCRETE,
            Material.WRITABLE_BOOK,
            Material.GOLDEN_AXE,
            Material.FLOWER_POT,
            Material.ARMOR_STAND,
//            Material.CANDLE,
            Material.POINTED_DRIPSTONE,
            Material.GLOW_BERRIES,
            Material.FLOWERING_AZALEA,
//            Material.RABBIT_STEW,
            Material.LAVA_BUCKET,
            Material.SALMON_BUCKET,
            Material.FIREWORK_ROCKET,
            Material.EGG,
            Material.WHITE_BANNER,
//            Material.PUMPKIN_PIE,
//            Material.CAKE,
            Material.BOOKSHELF,
            Material.CHISELED_DEEPSLATE,
            Material.CRACKED_STONE_BRICKS,
            Material.CHISELED_SANDSTONE,
            Material.ANVIL,
            Material.DRIED_KELP,
            Material.OAK_CHEST_BOAT,
//            Material.BREWING_STAND,
//            Material.SPLASH_POTION,
//            Material.POTION,
            Material.NOTE_BLOCK,
            Material.JUKEBOX,
            Material.TARGET,
            Material.DISPENSER,
            Material.BARREL,
            Material.MOSSY_COBBLESTONE,
            Material.ITEM_FRAME,
            Material.SPIDER_EYE,
            Material.MUD,
//            Material.TINTED_GLASS,
            Material.IRON_DOOR,
//            Material.SUSPICIOUS_STEW,
            Material.DIAMOND_HOE,
            Material.ENDER_PEARL,
            Material.BREAD,
            Material.STONE_SWORD,
            Material.COOKED_BEEF,
            Material.COOKED_PORKCHOP,
            Material.BAKED_POTATO,
            Material.FLINT_AND_STEEL,
            Material.DROPPER,
            Material.DIRT,
            Material.SHIELD,
            Material.SALMON,
            Material.RAW_COPPER_BLOCK,
            Material.LIGHTNING_ROD,
            Material.COMPOSTER,
            Material.ANDESITE,
            Material.TUFF,
            Material.GRANITE,
            Material.COAL_BLOCK,
//            Material.BAMBOO,
//            Material.TERRACOTTA,
            Material.TERRACOTTA,
            Material.GOLDEN_HOE,
//            Material.COOKIE,
            Material.POISONOUS_POTATO,
            Material.ARROW
    };

    private Material[] netherArray = {
            Material.BREWING_STAND,
            Material.SOUL_CAMPFIRE,
            Material.FIRE_CHARGE,
            Material.REDSTONE_LAMP,
            Material.SPECTRAL_ARROW,
            Material.CRYING_OBSIDIAN,
            Material.MAGMA_BLOCK,
            Material.NETHERITE_SCRAP,
            Material.CHISELED_QUARTZ_BLOCK,
            Material.NETHER_BRICKS,
            Material.NETHER_WART_BLOCK,
            Material.ENDER_EYE,
            Material.SOUL_LANTERN,
            Material.DAYLIGHT_DETECTOR,
            Material.COMPARATOR,
            Material.OBSERVER,
            Material.WARPED_FUNGUS_ON_A_STICK,
            Material.BLAZE_ROD,
            Material.GHAST_TEAR,
            Material.MAGMA_CREAM,
            Material.WARPED_FUNGUS,
            Material.ENDER_CHEST,
            Material.CRIMSON_FUNGUS
    };




    @Override
    public void onEnable() {
        // Plugin startup logic

        nether = false;
        bingoUtil = new BingoUtil(this );

        getServer().getPluginManager().registerEvents(new BingoEvents(this), this);
        getCommand("bingo").setExecutor(new BingoCommand(this));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public ArrayList<Material> getItemList(){
        itemList.clear();
        for(int i = 0; i < itemArray.length; i++){
            itemList.add(itemArray[i]);
        }
        if(nether){
            for(int i = 0; i < netherArray.length; i++){
                itemList.add(netherArray[i]);
            }
        }
        return itemList;
    }

    public BingoUtil getBingoUtil() {
        return bingoUtil;
    }

    public Location getGameLocation() {
        return gameLocation;
    }

    public void setGameLocation(Location gameLocation) {
        this.gameLocation = gameLocation;
    }

    public Map<UUID, BingoBoard> getPlayerBoards() {
        return playerBoards;
    }
}
