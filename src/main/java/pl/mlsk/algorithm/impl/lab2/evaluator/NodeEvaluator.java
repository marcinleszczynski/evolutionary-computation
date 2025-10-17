package pl.mlsk.algorithm.impl.lab2.evaluator;

import pl.mlsk.common.DistanceMatrix;
import pl.mlsk.common.Node;

import java.util.List;

public interface NodeEvaluator {

    EvaluationResult evaluate(Node node, int index, List<Node> resultNodes, DistanceMatrix distanceMatrix);
}
