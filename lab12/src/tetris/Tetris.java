package tetris;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static edu.princeton.cs.algs4.StdDraw.*;

/**
 * Provides the logic for Tetris.
 *
 * @author Erik Nelson, Omar Yu, Noah Adhikari, Jasmine Lin
 */

public class Tetris {

    private static int WIDTH = 10;
    private static int HEIGHT = 20;

    // Tetrominoes spawn above the area we display, so we'll have our Tetris board have a
    // greater height than what is displayed.
    private static int GAME_HEIGHT = 25;

    // Contains the tiles for the board.
    private TETile[][] board;

    // Helps handle movement of pieces.
    private Movement movement;

    // Checks for if the game is over.
    private boolean isGameOver;

    // The current Tetromino that can be controlled by the player.
    private Tetromino currentTetromino;

    // The current game's score.
    private int score;

    /**
     * Checks for if the game is over based on the isGameOver parameter.
     *
     * @return boolean representing whether the game is over or not
     */
    private boolean isGameOver() {
        return isGameOver;
    }

    /**
     * Renders the game board and score to the screen.
     */
    private void renderBoard() {
        ter.renderFrame(board);
        renderScore();
        if (auxFilled) {
            auxToBoard();
        } else {
            fillBoard(Tileset.NOTHING);
        }
    }

    /**
     * Creates a new Tetromino and updates the instance variable
     * accordingly. Flags the game to end if the top of the board
     * is filled and the new piece cannot be spawned.
     */
    private void spawnPiece() {
        // The game ends if this tile is filled
        if (board[4][19] != Tileset.NOTHING) {
            isGameOver = true;
        }

        // Otherwise, spawn a new piece and set its position to the spawn point
        currentTetromino = Tetromino.values()[bagRandom.getValue()];
        currentTetromino.reset();
    }


    /**
     * Determines if a new frame should be rendered.
     * This estimates a 60 fps cap on the rendered window.
     */
    public boolean shouldRenderNewFrame() {
        if (frameDeltaTime() > 16) {
            resetFrameTimer();
            return true;
        }
        return false;
    }

    /**
     * Updates the board based on the user input. Makes the appropriate moves
     * depending on the user's input.
     */
    private void updateBoard() {
        auxToBoard();
        // Grabs the current piece.
        if (currentTetromino == null) {
            spawnPiece();
            if (isGameOver) {
                return;
            }
            return;
        }

        Tetromino t = currentTetromino;

        while (hasNextKeyTyped()) {
            char c = nextKeyTyped();
            switch (c) {
                case 'a':
                    movement.tryMove(-1, 0);
                    break;
                case 's':
                    movement.dropDown();
                    break;
                case 'd':
                    movement.tryMove(1, 0);
                    break;
                case 'q':
                    movement.rotateLeft();
                    break;
                case 'w':
                    movement.rotateRight();
                    break;
            }

            if (currentTetromino == null){
                clearLines();
                return;
            }
        }

        if (actionDeltaTime() > 1000) {
            Tetromino pieceBeforeDrop = currentTetromino;
            movement.dropDown();
            resetActionTimer();
            if (pieceBeforeDrop != null && currentTetromino == null) {
                clearLines();
                return;
            }
        }
        if (currentTetromino != null) {
            Tetromino.draw(t, board, t.pos.x, t.pos.y);
        }
    }

    /**
     * Increments the score based on the number of lines that are cleared.
     *
     * @param linesCleared
     */
    private void incrementScore(int linesCleared) {
        switch (linesCleared) {
            case 1:
                score += 100;
                break;
            case 2:
                score += 300;
                break;
            case 3:
                score += 500;
                break;
            case 4:
                score += 800;
                break;
        }
    }

    /**
     * Clears lines/rows on the board that are horizontally filled.
     * Repeats this process for cascading effects and updates score accordingly.
     */
    private void clearLines() {
        int linesCleared = 0;
        List<Integer> completedRows = new ArrayList<>();
        for (int i = 0; i < GAME_HEIGHT; i++) {
            boolean completed = true;
            for (int j = 0; j < WIDTH; j++) {
                if (board[j][i] == Tileset.NOTHING) {
                    completed = false;
                    break;
                }
            }
            if (completed) {
                linesCleared++;
                completedRows.add(i);
            }
        }
        if (linesCleared > 0) {
            dropDownRow(completedRows);
        }
        incrementScore(linesCleared);
        fillAux();
        renderBoard();
    }

    private void dropDownRow(List<Integer> completeRows) {
        TETile[][] tempBoard = new TETile[WIDTH][GAME_HEIGHT];
        for (int i = 0; i < WIDTH; i++) {
            Arrays.fill(tempBoard[i], Tileset.NOTHING);
        }
        int currentRow = 0;
        for (int i = 0; i < GAME_HEIGHT; i++){
            if (!completeRows.contains(i)){
                for (int j = 0; j < WIDTH; j++){
                    tempBoard[j][currentRow] = board[j][i];
                }
                currentRow++;
            }
        }
        board = tempBoard;
    }

    /**
     * Where the game logic takes place. The game should continue as long as the game isn't
     * over.
     */
    public void runGame() {
        resetActionTimer();
        resetFrameTimer();

        // First spawn a piece
        spawnPiece();
        while (!isGameOver()) {
            if (shouldRenderNewFrame()) {
                updateBoard();
                renderBoard();
            }
        }
    }

    /**
     * Renders the score using the StdDraw library.
     */
    private void renderScore() {
        setPenColor(255, 255, 255);
        text(7, HEIGHT - 1, "Score: " + score);
        show();
    }

    /**
     * Use this method to run Tetris.
     *
     * @param args
     */
    public static void main(String[] args) {
        long seed = args.length > 0 ? Long.parseLong(args[0]) : (new Random()).nextLong();
        Tetris tetris = new Tetris(seed);
        tetris.runGame();
    }

    /**
     * Everything below here you don't need to touch.
     */

    // This is our tile rendering engine.
    private final TERenderer ter = new TERenderer();

    // Used for randomizing which pieces are spawned.
    private Random random;
    private BagRandomizer bagRandom;

    private long prevActionTimestamp;
    private long prevFrameTimestamp;

    // The auxiliary board. At each time step, as the piece moves down, the board
    // is cleared and redrawn, so we keep an auxiliary board to track what has been
    // placed so far to help render the current game board as it updates.
    private TETile[][] auxiliary;
    private boolean auxFilled;

    public Tetris(long seed) {
        board = new TETile[WIDTH][GAME_HEIGHT];
        auxiliary = new TETile[WIDTH][GAME_HEIGHT];
        random = new Random(seed);
        bagRandom = new BagRandomizer(random, Tetromino.values().length);
        auxFilled = false;
        movement = new Movement(WIDTH, GAME_HEIGHT, this);

        ter.initialize(WIDTH, HEIGHT);
        fillBoard(Tileset.NOTHING);
        fillAux();
    }

    // Setter and getter methods.

    /**
     * Returns the current game board.
     *
     * @return
     */
    public TETile[][] getBoard() {
        return board;
    }

    /**
     * Returns the current auxiliary board.
     *
     * @return
     */
    public TETile[][] getAuxiliary() {
        return auxiliary;
    }


    /**
     * Returns the current Tetromino/piece.
     *
     * @return
     */
    public Tetromino getCurrentTetromino() {
        return currentTetromino;
    }

    /**
     * Sets the current Tetromino to null.
     *
     * @return
     */
    public void setCurrentTetromino() {
        currentTetromino = null;
    }

    /**
     * Sets the boolean auxFilled to true;
     */
    public void setAuxTrue() {
        auxFilled = true;
    }

    /**
     * Fills the entire board with the specific tile that is passed in.
     *
     * @param tile
     */
    private void fillBoard(TETile tile) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = tile;
            }
        }
    }

    /**
     * Copies the contents of the src array into the dest array using
     * System.arraycopy.
     *
     * @param src
     * @param dest
     */
    private static void copyArray(TETile[][] src, TETile[][] dest) {
        for (int i = 0; i < src.length; i++) {
            System.arraycopy(src[i], 0, dest[i], 0, src[0].length);
        }
    }

    /**
     * Copies over the tiles from the game board to the auxiliary board.
     */
    public void fillAux() {
        copyArray(board, auxiliary);
    }

    /**
     * Copies over the tiles from the auxiliary board to the game board.
     */
    private void auxToBoard() {
        copyArray(auxiliary, board);
    }

    /**
     * Calculates the delta time with the previous action.
     *
     * @return the amount of time between the previous Tetromino movement with the present
     */
    private long actionDeltaTime() {
        return System.currentTimeMillis() - prevActionTimestamp;
    }

    /**
     * Calculates the delta time with the previous frame.
     *
     * @return the amount of time between the previous frame with the present
     */
    private long frameDeltaTime() {
        return System.currentTimeMillis() - prevFrameTimestamp;
    }

    /**
     * Resets the action timestamp to the current time in milliseconds.
     */
    private void resetActionTimer() {
        prevActionTimestamp = System.currentTimeMillis();
    }

    /**
     * Resets the frame timestamp to the current time in milliseconds.
     */
    private void resetFrameTimer() {
        prevFrameTimestamp = System.currentTimeMillis();
    }

}
