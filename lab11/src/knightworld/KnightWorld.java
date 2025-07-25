package knightworld;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Draws a world consisting of knight-move holes.
 */
public class KnightWorld {

    private TETile[][] tiles;
    private int height;
    private int width;
    private int holeSize;

    public KnightWorld(int width, int height, int holeSize) {
        this.width = width;
        this.height = height;
        this.holeSize = holeSize;
        tiles = new TETile[width][height];
        initialFloor();
        createHole();
    }

    private void initialFloor() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = Tileset.LOCKED_DOOR;
            }
        }
    }

    private void createHole() {
        Random rand = new Random();
        int x = rand.nextInt(5);
        for (int i = x; i < width / holeSize; i += 5) {
            makeHole(i * holeSize, 0);
        }
        for (int y = holeSize; y <= height - holeSize; y += holeSize){
            int nextX = randX(x);
            for (int i = nextX; i < width / holeSize; i += 5) {
                makeHole(i * holeSize, y);
            }
            x = nextX;
        }

    }

    private int randX(int lastX) {
        Random rand = new Random();
        int x = rand.nextInt(5);
        while (x == lastX) {
            x = rand.nextInt(5);
        }
        return x;
    }

    private void makeHole(int x, int y) {
        for (int dx = 0; dx < holeSize; dx++) {
            for (int dy = 0; dy < holeSize; dy++) {
                tiles[x + dx][y + dy] = Tileset.NOTHING;
            }
        }
    }

    /**
     * Returns the tiles associated with this KnightWorld.
     */
    public TETile[][] getTiles() {
        return tiles;
    }

    public static void main(String[] args) {
        // Change these parameters as necessary
        int width = 50;
        int height = 30;
        int holeSize = 1;

        KnightWorld knightWorld = new KnightWorld(width, height, holeSize);

        TERenderer ter = new TERenderer();
        ter.initialize(width, height);
        ter.renderFrame(knightWorld.getTiles());

    }
}
