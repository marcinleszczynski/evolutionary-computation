package pl.mlsk.common;

public record Node(
        double x,
        double y,
        double cost
) {
    public static double distance(Node a, Node b) {
        return Math.hypot(a.x - b.x, a.y - b.y);
    }
}
