package bongus.bingo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class BingoBoard {

    private Player p;
    public Inventory personaBoard = Bukkit.createInventory(null, 45, ChatColor.BOLD + "         Bingo Board");
    public static Inventory bingoInventory = Bukkit.createInventory(null, 45, ChatColor.BOLD + "         Bingo Board");
    public static Material[][] materialBoard = new Material[5][5];
    private boolean[][] boolBoard;

    public static ItemStack blankItem = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
    private ItemStack completeItem = new ItemStack(Material.LIME_STAINED_GLASS_PANE);


    public BingoBoard(Player p){
        this.p = p;
        boolBoard = new boolean[5][5];

    }

    public static boolean generateBoard(ArrayList<Material> materials){

        Material[][] newBoard = new Material[5][5];
        Material material;
        int current = -1;
        for(int x = 0; x < 5; x++){
            for(int y = 0; y < 5; y++){
                if(materials.isEmpty()) { newBoard[x][y] = Material.AIR; continue; }
                current = (int) (Math.random() * materials.size());
                material = materials.get(current);
                materials.remove(current);
                newBoard[x][y] = material;
            }
        }
        materialBoard = newBoard;

        System.out.println(materialBoard[4][4].toString());
        ItemMeta meta = blankItem.getItemMeta();
        meta.setDisplayName(" ");
        blankItem.setItemMeta(meta);


        // fill inventory with board materials
        int slot = 0;
        for(int y = 0; y < 5; y++){
            for(int x = 0; x < 9; x++){
                if(!(x > 1 && x < 7)){
                    bingoInventory.setItem(slot, blankItem);
                } else {
                    bingoInventory.setItem(slot, new ItemStack(materialBoard[x - 2][y], 1));
                }
                slot++;
            }

        }

        return true;
    }

    // sets material to true
    public boolean checkOff(Material material){
        Boolean done = false;
        for(int x = 0; x < 5 && !done; x++){
            for(int y = 0; y < 5 && !done; y++){
                if(materialBoard[x][y] == material && !boolBoard[x][y]){
                    boolBoard[x][y] = true;
                    done = true;
                    //Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getName() + ChatColor.WHITE + " got an item!");
                }
            }
        }
        return done;
    }

    public static void openBingoBoard(Player p){
        p.openInventory(bingoInventory);
    }


    // replaces found items with green glass of name
    public void updatePersonalBoard(){
        personaBoard.setStorageContents(bingoInventory.getStorageContents());;
        ItemMeta meta = blankItem.getItemMeta();

        int slot = 0;
        for(int y = 0; y < 5; y++){
            for(int x = 0; x < 9; x++){
                if(x > 1 && x < 7 && boolBoard[x-2][y]){
                    meta.setDisplayName(ChatColor.GREEN + "" + capitalizeFirsts(bingoInventory.getContents()[slot].getType().toString().toLowerCase().replace('_',' ')));
                    completeItem.setItemMeta(meta);
                    personaBoard.setItem(slot, completeItem);
                }
                slot++;
            }

        }

    }

    public boolean[][] getBoolBoard(){
        return boolBoard;
    }

    public void openPersonalBoard(){
        updatePersonalBoard();
        p.openInventory(personaBoard);
    }

    public Inventory getPersonalBoard(){
        return personaBoard;
    }


    // calculates score of bingo board
    public int score(){
        int inRow = 0;
        int score = 0;

        // vertical
        for(int x = 0; x < 5; x++){
            for(int y = 0; y < 5; y++) {
                if(boolBoard[x][y]) {
                    inRow++;
                    score++;
                }else {
                    continue;
                }
            }
            if(inRow == 5){
                score += 5;
            }
            inRow = 0;
        }

        // horizontal
        for(int y = 0; y < 5; y++){
            for(int x = 0; x < 5; x++) {
                if(boolBoard[x][y]) {
                    inRow++;
                }else {
                    break;
                }
            }
            if(inRow == 5){
                score += 5;
            }
            inRow = 0;
        }

        // diagonal \
        for(int p = 0; p < 5; p++) {
            if (boolBoard[p][p]) {
                inRow++;
            } else {
                break;
            }
        }
        if(inRow == 5){
            score += 5;
        }
        inRow = 0;

        // diagonal /
        for(int p = 0; p < 5; p++) {
            if (boolBoard[p][4 - p]) {
                inRow++;
            } else {
                break;
            }
        }
        if(inRow == 5){
            score += 5;
        }

        return score;
    }


    // capitalizes first letters of each word
    public String capitalizeFirsts(String string){
        String[] array = string.split(" ");
        String out = "";
        for(int i = 0; i < array.length; i++){
            out += array[i].substring(0,1).toUpperCase() + array[i].substring(1);
            if(i < array.length - 1){
                out += " ";
            }
        }
        return out;
    }

}
