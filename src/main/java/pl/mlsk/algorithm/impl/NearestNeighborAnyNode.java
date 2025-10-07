package pl.mlsk.algorithm.impl;

import org.springframework.stereotype.Service;
import pl.mlsk.algorithm.Algorithm;
import pl.mlsk.algorithm.impl.records.NodeWithIndex;
import pl.mlsk.common.AlgorithmInput;
import pl.mlsk.common.DistanceMatrix;
import pl.mlsk.common.Node;
import pl.mlsk.common.Solution;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class NearestNeighborAnyNode implements Algorithm {

    @Override
    public Solution solve(AlgorithmInput input, int startNode) {
        List<Node> nodes = input.nodes();
        DistanceMatrix distanceMatrix = input.distanceMatrix();
        long nodesToTake = nodesToTake(nodes);
        List<Node> solutions = new ArrayList<>();
        List<Node> available = new ArrayList<>(nodes);
        solutions.add(available.get(startNode));
        available.remove(startNode);

        for (long i = 1; i < nodesToTake; i++) {
            NodeWithIndex newNearestNode = newNearestNode(available, solutions, distanceMatrix);
            solutions.add(newNearestNode.index(), newNearestNode.node());
            available.remove(newNearestNode.node());
        }

        return new Solution(solutions);
    }

    private NodeWithIndex newNearestNode(List<Node> availableNodes, List<Node> solutionNodes, DistanceMatrix distanceMatrix) {
        Node nearestNode = availableNodes
                .stream()
                .min(Comparator.comparingDouble(node -> distanceMatrix.getDistance(solutionNodes.getFirst(), node)))
                .orElseThrow(RuntimeException::new);
        if (solutionNodes.size() <= 1) {
            return new NodeWithIndex(nearestNode, 0);
        }
        double smallestDistance = distanceMatrix.getDistance(solutionNodes.getFirst(), nearestNode);
        int bestIndex = 0;

        for (int i = 1; i < solutionNodes.size(); i++) {
            Node node1 = solutionNodes.get(i-1);
            Node node2 = solutionNodes.get(i);

            Node newNearestNode = availableNodes
                    .stream()
                    .min(Comparator.comparingDouble(node -> getDistanceDifferenceAfterInsertionBetween(distanceMatrix, node, node1, node2)))
                    .orElseThrow(RuntimeException::new);
            double newSmallestDistance = getDistanceDifferenceAfterInsertionBetween(distanceMatrix, newNearestNode, node1, node2);
            if (newSmallestDistance < smallestDistance) {
                bestIndex = i;
                smallestDistance = newSmallestDistance;
                nearestNode = newNearestNode;
            }
        }

        Node lastNearestNode = availableNodes
                .stream()
                .min(Comparator.comparingDouble(node -> distanceMatrix.getDistance(solutionNodes.getLast(), node)))
                .orElseThrow(RuntimeException::new);

        if (distanceMatrix.getDistance(solutionNodes.getLast(), lastNearestNode) < smallestDistance) {
            bestIndex = solutionNodes.size();
            nearestNode = lastNearestNode;
        }
        return new NodeWithIndex(nearestNode, bestIndex);
    }

    private double getDistanceDifferenceAfterInsertionBetween(DistanceMatrix distanceMatrix, Node newNode, Node node1, Node node2) {
        return distanceMatrix.getDistance(node1, newNode) + distanceMatrix.getDistance(node2, newNode) - newNode.cost() - distanceMatrix.getDistance(node1, node2) + node2.cost();
    }
}