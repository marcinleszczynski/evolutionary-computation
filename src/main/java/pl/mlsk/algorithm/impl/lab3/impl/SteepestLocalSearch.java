package pl.mlsk.algorithm.impl.lab3.impl;

import lombok.SneakyThrows;
import org.apache.commons.lang3.function.TriFunction;
import pl.mlsk.algorithm.GreedyAlgorithm;
import pl.mlsk.algorithm.LocalSearchAlgorithm;
import pl.mlsk.common.DistanceMatrix;
import pl.mlsk.common.Node;
import pl.mlsk.common.Solution;

import java.util.Iterator;
import java.util.List;

import static java.util.Objects.nonNull;

public class SteepestLocalSearch extends LocalSearchAlgorithm {

    public SteepestLocalSearch(GreedyAlgorithm greedyAlgorithm, TriFunction<Solution, DistanceMatrix, List<Node>, Iterator<Solution>> iteratorFunction) {
        super(greedyAlgorithm, iteratorFunction);
    }

    @Override
    @SneakyThrows
    protected Solution nextBestSolution(Solution solution, DistanceMatrix distanceMatrix, List<Node> allNodes) {
        Iterator<Solution> iterator = iteratorFunction.apply(solution, distanceMatrix, nodesNotInSolution(solution, allNodes));
        Solution result = solution;
        while (iterator.hasNext()) {
            Solution nextSolution = iterator.next();
            if (nonNull(nextSolution))
                result = nextSolution;
        }
        return result == solution ? null : result;
    }

    private List<Node> nodesNotInSolution(Solution solution, List<Node> allNodes) {
        return allNodes.stream().filter(node -> !solution.orderedNodes().contains(node)).toList();
    }
}
