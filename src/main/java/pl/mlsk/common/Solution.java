package pl.mlsk.common;

import java.util.List;

public record Solution(
        List<Node> orderedNodes
) {
    public double evaluate() {

        if (orderedNodes.isEmpty()) {
            return 0.0;
        }

        double result = orderedNodes.getFirst().cost();

        for (int i = 1; i < orderedNodes.size(); i++) {
            Node node1 = orderedNodes.get(i - 1);
            Node node2 = orderedNodes.get(i);
            result += node1.distanceWithCost(node2);
        }
        result += Node.distance(orderedNodes.getFirst(), orderedNodes.getLast());
        return result;
    }
}