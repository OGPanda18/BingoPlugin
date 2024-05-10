# BingoPlugin
A simple bingo plugin for Spigot minecraft servers you can play with your friends! It's for version `1.20.6`!

### Gameplay
When a game starts, players will be teleported to a random area in the world and given a "Bingo Card" that they can right click to view\
After a few seconds, the game will start and the players will be released\
Any time someone gets an item on the bingo board, it will check it off for them and increase their score by 1\
If someone gets the fifth item in a row, an additional 5 points will be added to their score (for a total of 10 in the row)\
Once the time limit of the game is reached, a scoreboard will be printed out in chat to see who one!

If for some reason you get an item and it doesn't check it off the board, just right click with it in hand

### Usage
The only command is `/bingo [argument]` (alias `/bi`)\
Possible arguments are:
- `start`: start the game with most recently used settings
  - starting a new game while one is active will end the current game!
- `nether`: toggle the ability for nether items to appear on the bingo board (default off)
- `<any positive integer>`: start a new game of given length (in seconds)
- `new`: generates a new bingo board (starting a game will also generate a new board!)

### How to install
- [Create a spigot server](https://minecraft.fandom.com/wiki/Tutorials/Setting_up_a_Spigot_server)
- Download the [latest version](https://github.com/OGPanda18/BingoPlugin/releases/latest) `.jar` file and put it in the plugins folder
- Restart the server (`/reload` may cause issues!)


[@ari-goldman](https://github.com/ari-goldman)/[@OGPanda18](https://github.com/OGPanda18)
