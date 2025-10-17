package pl.mlsk.algorithm.impl.lab2.evaluator;

import pl.mlsk.common.Node;

public record EvaluationResult(
        Node node,
        int index,
        double distance
) { }
