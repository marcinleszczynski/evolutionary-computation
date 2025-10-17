package pl.mlsk.algorithm;

import pl.mlsk.algorithm.impl.lab2.evaluator.EvaluationResult;
import pl.mlsk.algorithm.impl.lab2.evaluator.NodeEvaluator;
import pl.mlsk.algorithm.impl.records.NodeWithIndex;
import pl.mlsk.common.DistanceMatrix;
import pl.mlsk.common.Node;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public abstract class GreedyRegretAlgorithm extends GreedyAlgorithm {

    @Override
    protected NodeWithIndex bestNextNode(DistanceMatrix distanceMatrix, List<Node> available, List<Node> result) {
        EvaluationResult bestNode = available
                .stream()
                .map(node -> createEvaluationResults(distanceMatrix, node, result))
                .max(Comparator.comparingDouble(this::calculateRegret))
                .map(List::getFirst)
                .orElseThrow(RuntimeException::new);
        return new NodeWithIndex(bestNode.node(), bestNode.index());
    }

    protected List<EvaluationResult> createEvaluationResults(DistanceMatrix distanceMatrix, Node node, List<Node> solutionNodes) {
        return IntStream.range(0, solutionNodes.size())
                .mapToObj(i -> getNodeEvaluator().evaluate(node, i, solutionNodes, distanceMatrix))
                .distinct()
                .sorted(Comparator.comparingDouble(EvaluationResult::distance))
                .limit(2)
                .toList();
    }

    protected abstract double calculateRegret(List<EvaluationResult> evaluationResultsForNode);

    protected abstract NodeEvaluator getNodeEvaluator();

    @Override
    public String algorithmName() {
        return super.algorithmName() + " - " + getNodeEvaluator().getClass().getSimpleName();
    }
}
