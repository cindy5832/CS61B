package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static core.Game.HEIGHT;
import static core.Game.WIDTH;

public class AutograderBuddy {

    /**
     * Simulates a game, but doesn't render anything or call any StdDraw
     * methods. Instead, returns the world that would result if the input string
     * had been typed on the keyboard.
     * <p>
     * Recall that strings ending in ":q" should cause the game to quit and
     * save. To "quit" in this method, save the game to a file, then just return
     * the TETile[][]. Do not call System.exit(0) in this method.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public static TETile[][] getWorldFromInput(String input) {
        Game game = new Game();
        TETile[][] currentWorld = null;
        String inputString = input.toUpperCase();
        Pattern newWorldPattern = Pattern.compile("N(\\d+)S");
        Matcher newWorldMatcher = newWorldPattern.matcher(inputString);
        if (newWorldMatcher.find()) {
            long seed = Long.parseLong(newWorldMatcher.group(1));
            currentWorld = new World(WIDTH, HEIGHT, seed).getTile();
        }else if(inputString.startsWith("L")){
            game.loadGame();
        }
        if (inputString.endsWith(":Q")){
            if (currentWorld != null) {
                game.saveGame();
            }
        }
        return currentWorld;
//        throw new RuntimeException("Please fill out AutograderBuddy!");

    }


    /**
     * Used to tell the autograder which tiles are the floor/ground (including
     * any lights/items resting on the ground). Change this
     * method if you add additional tiles.
     */
    public static boolean isGroundTile(TETile t) {
        return t.character() == Tileset.FLOOR.character()
                || t.character() == Tileset.AVATAR.character()
                || t.character() == Tileset.FLOWER.character();
    }

    /**
     * Used to tell the autograder while tiles are the walls/boundaries. Change
     * this method if you add additional tiles.
     */
    public static boolean isBoundaryTile(TETile t) {
        return t.character() == Tileset.WALL.character()
                || t.character() == Tileset.LOCKED_DOOR.character()
                || t.character() == Tileset.UNLOCKED_DOOR.character();
    }
}
