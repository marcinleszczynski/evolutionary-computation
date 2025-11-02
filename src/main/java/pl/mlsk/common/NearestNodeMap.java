package pl.mlsk.common;

import java.util.*;
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
        PriorityQueue<PriorityNode> pq = new PriorityQueue<>(Comparator.comparingDouble(PriorityNode::priority));
        ToDoubleFunction<Node> distanceFn = nd -> Node.distance(node, nd) + nd.cost();
        for (Node neighbor : nodes) {
            if (neighbor != node) {
                if (pq.size() < n) {
                    pq.add(new PriorityNode(neighbor, distanceFn.applyAsDouble(neighbor)));
                } else if (distanceFn.applyAsDouble(neighbor) < pq.peek().priority()) {
                    pq.poll();
                    pq.add(new PriorityNode(neighbor, distanceFn.applyAsDouble(neighbor)));
                }
            }
        }
        return pq.stream().map(PriorityNode::node).toList();
    }

    private record PriorityNode(
            Node node,
            double priority
    ) {
    }
}
