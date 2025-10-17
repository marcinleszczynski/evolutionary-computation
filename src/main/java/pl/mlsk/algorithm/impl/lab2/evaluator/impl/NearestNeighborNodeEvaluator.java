package pl.mlsk.algorithm.impl.lab2.evaluator.impl;

import org.springframework.stereotype.Service;
import pl.mlsk.algorithm.impl.lab2.evaluator.EvaluationResult;
import pl.mlsk.algorithm.impl.lab2.evaluator.NodeEvaluator;
import pl.mlsk.common.DistanceMatrix;
import pl.mlsk.common.Node;

import java.util.List;

@Service
public class NearestNeighborNodeEvaluator implements NodeEvaluator {
    
    @Override
    public EvaluationResult evaluate(Node node, int index, List<Node> resultNodes, DistanceMatrix distanceMatrix) {

        if (index == 0) {
            double distance = distanceMatrix.getDistance(resultNodes.getFirst(), node) + node.cost();
            return new EvaluationResult(node, index, distance);
        }

        if (index == resultNodes.size()) {
            double distance = distanceMatrix.getDistance(resultNodes.getLast(), node) + node.cost();
            return new EvaluationResult(node, index, distance);
        }
        Node node1 = resultNodes.get(index-1);
        Node node2 = resultNodes.get(index);

        double distance = distanceMatrix.getDistance(node1, node) + distanceMatrix.getDistance(node2, node) - distanceMatrix.getDistance(node1, node2) + node.cost();
        return new EvaluationResult(node, index, distance);
    }
}
