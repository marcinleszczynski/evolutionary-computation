package pl.mlsk.algorithm.impl.lab3.impl;

import lombok.SneakyThrows;
import org.apache.commons.lang3.function.TriFunction;
import pl.mlsk.algorithm.GreedyAlgorithm;
import pl.mlsk.algorithm.LocalSearchAlgorithm;
import pl.mlsk.algorithm.impl.lab3.LocalSearchIterator;
import pl.mlsk.common.DistanceMatrix;
import pl.mlsk.common.Node;
import pl.mlsk.common.Solution;

import java.util.List;

public class SteepestLocalSearch extends LocalSearchAlgorithm {

    public SteepestLocalSearch(GreedyAlgorithm greedyAlgorithm, TriFunction<Solution, DistanceMatrix, List<Node>, LocalSearchIterator> iteratorFunction) {
        super(greedyAlgorithm, iteratorFunction);
    }

    @Override
    @SneakyThrows
    protected Solution nextBestSolution(Solution solution, DistanceMatrix distanceMatrix, List<Node> allNodes) {
        LocalSearchIterator iterator = iteratorFunction.apply(solution, distanceMatrix, nodesNotInSolution(solution, allNodes));
        Solution result = solution;
        double bestDelta = Double.MAX_VALUE;
        while (iterator.hasNext()) {
            Solution nextSolution = iterator.next();
            if (iterator.getBestDelta() < bestDelta) {
                bestDelta = iterator.getBestDelta();
                result = nextSolution;
            }
        }
        return result;
    }

    private List<Node> nodesNotInSolution(Solution solution, List<Node> allNodes) {
        return allNodes.stream().filter(node -> !solution.orderedNodes().contains(node)).toList();
    }
}
