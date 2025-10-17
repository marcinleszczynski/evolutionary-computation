package pl.mlsk.algorithm.impl.lab2.evaluator.impl;

import org.springframework.stereotype.Service;
import pl.mlsk.algorithm.impl.lab2.evaluator.EvaluationResult;
import pl.mlsk.algorithm.impl.lab2.evaluator.NodeEvaluator;
import pl.mlsk.common.DistanceMatrix;
import pl.mlsk.common.Node;

import java.util.List;

@Service
public class GreedyCycleNodeEvaluator implements NodeEvaluator {

    @Override
    public EvaluationResult evaluate(Node node, int index, List<Node> resultNodes, DistanceMatrix distanceMatrix) {

        if (resultNodes.size() <= 1) {
            double distance = distanceMatrix.getDistance(resultNodes.getFirst(), node) + node.cost();
            return new EvaluationResult(node, 0, distance);
        }

        if (index == 0 || index == resultNodes.size()) {
            double distance = calculateDistanceIncrease(distanceMatrix, node, resultNodes.getFirst(), resultNodes.getLast());
            return new EvaluationResult(node, index, distance);
        }

        Node node1 = resultNodes.get(index-1);
        Node node2 = resultNodes.get(index);

        double distance = calculateDistanceIncrease(distanceMatrix, node, node1, node2);
        return new EvaluationResult(node, index, distance);
    }

    private static double calculateDistanceIncrease(DistanceMatrix distanceMatrix, Node newNode, Node node1, Node node2) {
        return distanceMatrix.getDistance(node1, newNode) + distanceMatrix.getDistance(node2, newNode) - distanceMatrix.getDistance(node1, node2) + newNode.cost();
    }
}
