package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.*;

public class World {

    // world basic information
    private int width;
    private int height;
    private TETile[][] tile;
    private final int MAX = 100;
    private final int MIN = 50;
    private List<Room> rooms;
    private List<Integer> roomLocation;

    // player location
    private int avatarX;
    private int avatarY;
    // exit location
    private int exitX;
    private int exitY;

    private final int FLOWER = 10;
    private final int WATER = 5;

    private Random random;

    public World(int width, int height, long seed) {
        this.width = width;
        this.height = height;
        this.rooms = new ArrayList<>();
        this.random = new Random(seed);
        this.roomLocation = new ArrayList<>();
        this.tile = new TETile[width][height];
        initialWorld();
        createRooms();
        makeHallWays();
        creatWall();
        placeAvatarAndExit();
        placeItems();
    }

    public TETile[][] getTile() {
        return tile;
    }

    public int getExitY() {
        return exitY;
    }

    public int getExitX() {
        return exitX;
    }

    public int getAvatarY() {
        return avatarY;
    }

    public int getAvatarX() {
        return avatarX;
    }

    // 初始化world
    private void initialWorld() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tile[x][y] = Tileset.NOTHING;
            }
        }
    }

    private void createRooms() {
        int rooms = random.nextInt(MAX - MIN + 1) + MIN;
        for (int i = 0; i < rooms; i++) {
            if (makeRoom()) {
                i++;
            }
        }
    }

    private boolean makeRoom() {
        int width = random.nextInt(2, 8);
        int height = random.nextInt(2, 8);
        int x = random.nextInt(1, this.width - width - 1);
        int y = random.nextInt(1, this.height - height - 1);
        int location = xyToNumber(x, y);
        if (roomLocation.isEmpty() || !roomLocation.contains(location)) {
            roomLocation.add(location);
            Room room = new Room(x, y, width, height);
            rooms.add(room);
            // make room
            for (int i = x; i < x + width; i++) {
                for (int j = y; j < y + height; j++) {
                    tile[i][j] = Tileset.FLOOR;
                }
            }
            return true;
        }
        return false;
    }

    private void makeHallWays() {
        if (rooms.isEmpty()) {
            return;
        }
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < rooms.size(); i++) {
            for (int j = i + 1; j < rooms.size(); j++) {
                edges.add(new Edge(rooms.get(i), rooms.get(j)));
            }
        }
        Collections.sort(edges);
        Map<Room, Integer> roomToIndex = new HashMap<>();
        for (int i = 0; i < rooms.size(); i++) {
            roomToIndex.put(rooms.get(i), i);
        }
        DSU dsu = new DSU(rooms.size());
        for (Edge edge : edges) {
            int index1 = roomToIndex.get(edge.room1);
            int index2 = roomToIndex.get(edge.room2);

            if (!dsu.isConnected(index1, index2)) {
                dsu.union(index1, index2);
                makeCorridor(edge.room1, edge.room2);
                if (dsu.getNumSets() == 1) {
                    break;
                }
            }
        }

    }

    private void makeCorridor(Room room1, Room room2) {
        int x1 = room1.getCenterX();
        int y1 = room1.getCenterY();
        int x2 = room2.getCenterX();
        int y2 = room2.getCenterY();

        boolean horizontalFirst = random.nextBoolean();

        int currentX = x1;
        int currentY = y1;
        if (horizontalFirst) {
            while (currentX != x2) {
                drawCorridor(currentX, currentY);
                currentX += (currentX < x2) ? 1 : -1;
            }
            while (currentY != y2) {
                drawCorridor(currentX, currentY);
                currentY += (currentY < y2) ? 1 : -1;
            }
        } else {
            while (currentY != y2) {
                drawCorridor(currentX, currentY);
                currentY += (currentY < y2) ? 1 : -1;
            }
            while (currentX != x2) {
                drawCorridor(currentX, currentY);
                currentX += (currentX < x2) ? 1 : -1;
            }
        }
        drawCorridor(currentX, currentY);

    }

    private void drawCorridor(int currentX, int currentY) {
        if (currentX >= 0 && currentX < width && currentY >= 0 && currentY < height) {
            if (tile[currentX][currentY] == Tileset.NOTHING) {
                tile[currentX][currentY] = Tileset.FLOOR;
            }
        }
    }

    private void creatWall() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (tile[i][j] == Tileset.NOTHING) {
                    boolean isNearFloor = false;
                    for (int dx = -1; dx <= 1; dx++) {
                        for (int dy = -1; dy <= 1; dy++) {
                            if (dx == 0 && dy == 0) {
                                continue;
                            }
                            int nx = i + dx;
                            int ny = j + dy;
                            if (nx >= 0 && nx < width && ny >= 0 && ny < height && tile[nx][ny] == Tileset.FLOOR) {
                                isNearFloor = true;
                                break;
                            }
                        }
                        if (isNearFloor) {
                            break;
                        }
                    }
                    if (isNearFloor) {
                        tile[i][j] = Tileset.WALL;
                    }
                }
            }
        }
    }

    private void placeAvatarAndExit() {
        if (rooms.isEmpty()) {
            System.err.println("No rooms found");
            return;
        }
        // place Player
        Room avatarRoom = rooms.get(random.nextInt(rooms.size()));
        avatarX = random.nextInt(avatarRoom.getX(), avatarRoom.getX() + avatarRoom.getWidth());
        avatarY = random.nextInt(avatarRoom.getY(), avatarRoom.getY() + avatarRoom.getHeight());
        tile[avatarX][avatarY] = Tileset.AVATAR;

        // place Exit: player and exit not in the same room
        Room exitRoom = rooms.get(random.nextInt(rooms.size()));
        while (exitRoom == avatarRoom) {
            exitRoom = rooms.get(random.nextInt(rooms.size()));
        }
        exitX = random.nextInt(exitRoom.getX() - 1, exitRoom.getX() + exitRoom.getWidth() + 1);
        exitY = random.nextInt(exitRoom.getY() - 1, exitRoom.getY() + exitRoom.getHeight() + 1);
        tile[exitX][exitY] = Tileset.LOCKED_DOOR;
    }

    private void placeItems() {
        if (rooms.isEmpty()) {
            System.err.println("No rooms found");
        }
        int flowers = 0;
        while (flowers <= FLOWER) {
            Room room = rooms.get(random.nextInt(rooms.size()));
            int x = random.nextInt(room.getX(), room.getX() + room.getWidth());
            int y = random.nextInt(room.getY(), room.getY() + room.getHeight());
            if (tile[x][y] == Tileset.FLOOR) {
                tile[x][y] = Tileset.FLOWER;
                flowers++;
            }
        }

        int water = 0;
        while (water <= WATER) {
            Room room = rooms.get(random.nextInt(rooms.size()));
            int x = random.nextInt(room.getX(), room.getX() + room.getWidth());
            int y = random.nextInt(room.getY(), room.getY() + room.getHeight());
            if (tile[x][y] == Tileset.FLOOR) {
                tile[x][y] = Tileset.WATER;
                water++;
            }
        }
    }

    private int xyToNumber(int x, int y) {
        return y * width + x;
    }
}
