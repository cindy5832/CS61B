package core;

public class Edge implements Comparable<Edge> {
    Room room1;
    Room room2;
    double distance;

    public Edge(Room room1, Room room2) {
        this.room1 = room1;
        this.room2 = room2;
        // Euclidean Distance for 最小生成樹 (MST) 演算法
        this.distance = Math.sqrt(Math.pow(room1.getCenterX() - room2.getCenterX(), 2) + Math.pow(room1.getCenterY() - room2.getCenterY(), 2));
    }

    @Override
    public int compareTo(Edge other) {
        return Double.compare(this.distance, other.distance);
    }
}
