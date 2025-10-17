package pl.mlsk.algorithm.impl.lab1;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mlsk.algorithm.GreedyAlgorithm;
import pl.mlsk.algorithm.impl.lab2.evaluator.EvaluationResult;
import pl.mlsk.algorithm.impl.lab2.evaluator.impl.NearestNeighborNodeEvaluator;
import pl.mlsk.algorithm.impl.records.NodeWithIndex;
import pl.mlsk.common.DistanceMatrix;
import pl.mlsk.common.Node;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class NearestNeighborAnyNode extends GreedyAlgorithm {

    private final NearestNeighborNodeEvaluator evaluator;

    @Override
    protected NodeWithIndex bestNextNode(DistanceMatrix distanceMatrix, List<Node> available, List<Node> result) {
        EvaluationResult bestNode = available
                .stream()
                .map(node -> createEvaluationResults(distanceMatrix, node, result))
                .flatMap(List::stream)
                .min(Comparator.comparingDouble(EvaluationResult::distance))
                .orElseThrow(RuntimeException::new);
        return new NodeWithIndex(bestNode.node(), bestNode.index());
    }

    private List<EvaluationResult> createEvaluationResults(DistanceMatrix distanceMatrix, Node node, List<Node> solutionNodes) {
        return IntStream.range(0, solutionNodes.size())
                .mapToObj(i -> evaluator.evaluate(node, i, solutionNodes, distanceMatrix))
                .toList();
    }
}