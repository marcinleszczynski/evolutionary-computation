package pl.mlsk.algorithm.impl;

import org.springframework.stereotype.Service;
import pl.mlsk.algorithm.Algorithm;
import pl.mlsk.algorithm.impl.records.NodeWithIndex;
import pl.mlsk.common.Node;
import pl.mlsk.common.Solution;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class NearestNeighborAnyNode implements Algorithm {

    @Override
    public Solution solve(List<Node> nodes, int startNode) {
        long nodesToTake = nodesToTake(nodes);
        List<Node> solutions = new ArrayList<>();
        List<Node> available = new ArrayList<>(nodes);
        solutions.add(available.get(startNode));
        available.remove(startNode);

        for (long i = 1; i < nodesToTake; i++) {
            NodeWithIndex newNearestNode = newNearestNode(available, solutions);
            solutions.add(newNearestNode.index(), newNearestNode.node());
            available.remove(newNearestNode.node());
        }

        return new Solution(solutions);
    }

    private NodeWithIndex newNearestNode(List<Node> availableNodes, List<Node> solutionNodes) {
        Node nearestNode = availableNodes
                .stream()
                .min(Comparator.comparingDouble(solutionNodes.getFirst()::distanceWithCost))
                .orElseThrow(RuntimeException::new);
        if (solutionNodes.size() <= 1) {
            return new NodeWithIndex(nearestNode, 0);
        }
        double smallestDistance = solutionNodes.getFirst().distanceWithCost(nearestNode);
        int bestIndex = 0;

        for (int i = 1; i < solutionNodes.size(); i++) {
            Node node1 = solutionNodes.get(i-1);
            Node node2 = solutionNodes.get(i);

            Node newNearestNode = availableNodes
                    .stream()
                    .min(Comparator.comparingDouble(node -> node1.distanceWithCost(node) + node2.distanceWithCost(node) - node.cost()))
                    .orElseThrow(RuntimeException::new);
            double newSmallestDistance = node1.distanceWithCost(newNearestNode) + node2.distanceWithCost(newNearestNode) - Node.distance(node1, node2);
            if (newSmallestDistance < smallestDistance) {
                bestIndex = i;
                smallestDistance = newSmallestDistance;
                nearestNode = newNearestNode;
            }
        }

        Node lastNearestNode = availableNodes
                .stream()
                .min(Comparator.comparingDouble(solutionNodes.getLast()::distanceWithCost))
                .orElseThrow(RuntimeException::new);

        if (solutionNodes.getLast().distanceWithCost(lastNearestNode) < smallestDistance) {
            bestIndex = solutionNodes.size();
            nearestNode = lastNearestNode;
        }
        return new NodeWithIndex(nearestNode, bestIndex);
    }
}