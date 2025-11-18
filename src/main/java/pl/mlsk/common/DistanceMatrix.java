package pl.mlsk.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class DistanceMatrix {

    private final Map<Node, Integer> indexes = new HashMap<>();
    private final double[][] matrix;

    public DistanceMatrix(List<Node> nodes) {
        matrix = new double[nodes.size()][nodes.size()];
        for (int i = 0; i < nodes.size(); i++) {
            indexes.put(nodes.get(i), i);
            for (int j = 0; j < nodes.size(); j++) {
                matrix[i][j] = Node.distance(nodes.get(i), nodes.get(j));
            }
        }
    }

    public double getDistance(Node node1, Node node2) {
        return matrix[indexes.get(node1)][indexes.get(node2)];
    }

    public Stream<Node> streamNodes() {
        return indexes.keySet().stream();
    }
}
