package pl.mlsk.algorithm;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.function.TriFunction;
import pl.mlsk.algorithm.impl.lab3.LocalSearchIterator;
import pl.mlsk.common.AlgorithmInput;
import pl.mlsk.common.DistanceMatrix;
import pl.mlsk.common.Node;
import pl.mlsk.common.Solution;

import java.util.List;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
public abstract class LocalSearchAlgorithm implements Algorithm {

    private final GreedyAlgorithm greedyAlgorithm;
    protected final TriFunction<Solution, DistanceMatrix, List<Node>, LocalSearchIterator> iteratorFunction;
    private static final Solution empty = new Solution(List.of(new Node(0, 0, 0))); // used only for getting name of result iterator class

    @Override
    public Solution solve(AlgorithmInput input, int startNode) {
        Solution solution = greedyAlgorithm.solve(input, startNode);
        return localSearch(solution, input.distanceMatrix(), input.nodes());
    }

    private Solution localSearch(Solution solution, DistanceMatrix distanceMatrix, List<Node> allNodes) {
        while (true) {
            Solution nextSolution = nextBestSolution(solution, distanceMatrix, allNodes);
            if (isNull(nextSolution))
                return solution;
            solution = nextSolution;
        }
    }

    protected abstract Solution nextBestSolution(Solution solution, DistanceMatrix distanceMatrix, List<Node> allNodes);

    @Override
    public String algorithmName() {
        return getClass().getSimpleName() + " - " + iteratorFunction.apply(empty, null, null).getClass().getSimpleName() + " - " + greedyAlgorithm.getClass().getSimpleName();
    }
}
