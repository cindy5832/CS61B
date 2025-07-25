package core;

public class Room {
    private int x;
    private int y;
    private int width;
    private int height;
    private int centerX;
    private int centerY;

    public Room(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.centerX = x + width / 2;
        this.centerY = y + height / 2;
    }

    // 判斷一個點是否在房間內部 (不包含牆壁)
    public boolean contains(int px, int py) {
        return px >= x && px < x + width && py >= y && py < y + height;
    }

    // 判斷一個點是否在房間佔用範圍內 (含牆壁)
    public boolean containsWithWall(int px, int py) {
        return px >= x - 1 && px <= x + width && py >= y - 1 && py <= y + height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }
}
