package core;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.awt.*;

import static edu.princeton.cs.algs4.StdDraw.*;

public class Game {

    private final Font TITLE = new Font("Arial", Font.BOLD, 50);
    private final Font CONTENT = new Font("Arial", Font.BOLD, 30);
    private final Font HUD = new Font("Dialog", Font.BOLD, 20);
    public static final int WIDTH = 100;
    public static final int HEIGHT = 50;
    public static final int SCREEN_HEIGHT = 55;
    private TERenderer teRenderer;
    private TETile[][] currentWorld;

    private int playerX;
    private int playerY;

    private int exitX;
    private int exitY;

    private int flower = 0;
    private int water = 0;
    private boolean isPlaying = true;


    public Game() {
        this.teRenderer = new TERenderer();
        teRenderer.initialize(WIDTH, SCREEN_HEIGHT);
    }

    public void startPage() {
        clear(Color.BLACK);
        // Title
        drawString(TITLE, 50, 40, "The Maze");
        // Options
        drawString(CONTENT, 50, 30, "New Game (N)");
        drawString(CONTENT, 50, 25, "Load Game (L)");
        drawString(CONTENT, 50, 20, "Quit (Q)");
        show();
    }

    public void startGame() {
        while (!hasNextKeyTyped()) {
            pause(10);
        }
        char c = Character.toUpperCase(nextKeyTyped());
        // Options
        switch (c) {
            case 'N':
                startNewGame();
                break;
            case 'L':
                loadGame();
                break;
            case 'Q':
                quitGame();
                break;
            default:
                break;
        }


    }

    void loadGame() {
        clear(Color.BLACK);
        drawString(TITLE, 50, 40, "Game Records");
    }

    void quitGame() {
        isPlaying = false;

        System.exit(0); // terminate the game
    }

    private void startNewGame() {
        clear(Color.BLACK);
        drawString(CONTENT, 50, 30, "Start New Game! Enter a random positive number and end with 'S' to create the game (e.g., 12345S): ");
        show();
        StringBuilder seed = new StringBuilder();
        boolean setSeed = true;
        char number;
        int x = 50;
        while (setSeed) {
            if (hasNextKeyTyped()) {
                number = Character.toUpperCase(nextKeyTyped());
                if (Character.isDigit(number)) {
                    seed.append(number);
                    drawString(CONTENT, x++, 25, Character.toString(number));
                    show();
                } else if (number == 'S') {
                    setSeed = false;
                }
            }
        }
        long seedValue = -1;
        try {
            if (!seed.isEmpty()) {
                seedValue = Long.parseLong(seed.toString());
            } else {
                drawString(CONTENT, 50, 25, "Please enter a random number!");
                show();
                pause(1000);
                startNewGame();
            }
        } catch (NumberFormatException e) {
            drawString(CONTENT, 50, 20, "Please enter the correct number or enter a smaller number!");
            show();
            pause(1000);
            startNewGame();
        }

        World world = new World(WIDTH, HEIGHT, seedValue);
        currentWorld = world.getTile();
        playerX = world.getAvatarX();
        playerY = world.getAvatarY();
        exitX = world.getExitX();
        exitY = world.getExitY();
        playGame();

    }

    private void drawString(Font f, int x, int y, String text) {
        setPenColor(Color.WHITE);
        setFont(f);
        text(x, y, text);
    }

    public void saveGame() {
    }

    public void playGame() {
        teRenderer.renderFrame(currentWorld);
        drawHUD();
        while (isPlaying) {
            if (hasNextKeyTyped()) {
                char c = Character.toUpperCase(nextKeyTyped());
                switch (c) {
                    case 'W':
                        // move up
                        move(0, 1);
                        break;
                    case 'A':
                        // move left
                        move(-1, 0);
                        break;
                    case 'S':
                        // move down
                        move(0, -1);
                        break;
                    case 'D':
                        // move right
                        move(1, 0);
                        break;
                    default:
                        break;
                }
                teRenderer.renderFrame(currentWorld);
            }
            pause(10);
        }
//        if (!isPlaying){
//            saveGame();
//            startNewGame();
//        }
    }

    private void move(int dx, int dy) {
        int x = playerX + dx;
        int y = playerY + dy;
        if (canMove(x, y)) {
            currentWorld[playerX][playerY] = Tileset.FLOOR;
            playerX = x;
            playerY = y;
            pickUpObject();
            getGoal();
            currentWorld[playerX][playerY] = Tileset.AVATAR;
        }
    }

    private boolean canMove(int x, int y) {
        // bound check
        if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) {
            return false;
        }
        return currentWorld[x][y] == Tileset.FLOOR || currentWorld[x][y] == Tileset.FLOWER || currentWorld[x][y] == Tileset.WATER || currentWorld[x][y] == Tileset.LOCKED_DOOR;
    }

    private void pickUpObject() {
        if (currentWorld[playerX][playerY] == Tileset.FLOWER) {
            flower++;
        } else if (currentWorld[playerX][playerY] == Tileset.WATER) {
            water++;
        }
    }

    private void getGoal() {
        if (exitX == playerX && exitY == playerY && currentWorld[playerX][playerY] == Tileset.LOCKED_DOOR) {
            isPlaying = false;
            // TODO: win game message
        }
    }

    private void drawHUD(){
        setPenColor(Color.WHITE);
        setFont(HUD);
        textLeft(5, SCREEN_HEIGHT - 2, "✿ " +  flower + "    \uD83D\uDCA7" + water);
        textRight(WIDTH-5, SCREEN_HEIGHT-2, "Save");

    }
}
