package pl.mlsk.common;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.ToDoubleFunction;

public class NearestNodeMap {

    private final Map<Node, List<Node>> map;

    public NearestNodeMap(List<Node> nodes, int n) {
        map = new HashMap<>();
        for (Node node : nodes) {
            map.put(node, getNClosestNeighbors(node, nodes, n));
        }
    }

    public int neighborhoodSize() {
        return map.values()
                .stream()
                .findAny()
                .map(List::size)
                .orElseThrow(RuntimeException::new);
    }

    public Node getNeighbor(Node node, int index) {
        return map.get(node).get(index);
    }

    private List<Node> getNClosestNeighbors(Node node, List<Node> nodes, int n) {
        ToDoubleFunction<Node> distanceFn = nd -> Node.distance(node, nd) + nd.cost();
        return nodes
                .stream()
                .filter(nod -> nod != node)
                .sorted(Comparator.comparingDouble(distanceFn))
                .limit(n)
                .toList();
    }

    private record PriorityNode(
            Node node,
            double priority
    ) {
    }
}
