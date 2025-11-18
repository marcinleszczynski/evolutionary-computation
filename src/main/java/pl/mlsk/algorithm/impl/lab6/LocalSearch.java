package pl.mlsk.algorithm.impl.lab6;

import org.springframework.stereotype.Service;
import pl.mlsk.algorithm.impl.lab3.impl.iterator.grouped.LocalEdgeIterator;
import pl.mlsk.common.DistanceMatrix;
import pl.mlsk.common.Node;
import pl.mlsk.common.Solution;

import java.util.HashSet;

import static java.util.Objects.nonNull;

@Service
public class LocalSearch {

    public Solution localSearch(Solution solution, DistanceMatrix distanceMatrix) {
        while (true) {
            Solution newSolution = steepest(solution, distanceMatrix);
            if (newSolution == null)
                return solution;
            solution = newSolution;
        }
    }

    private Solution steepest(Solution solution, DistanceMatrix distanceMatrix) {
        HashSet<Node> solutionNodeSet = new HashSet<>(solution.orderedNodes());
        LocalEdgeIterator iterator = new LocalEdgeIterator(solution, distanceMatrix, distanceMatrix.streamNodes().filter(node -> !solutionNodeSet.contains(node)).toList());
        Solution result = solution;
        while (iterator.hasNext()) {
            Solution nextSolution = iterator.next();
            if (nonNull(nextSolution))
                result = nextSolution;
        }
        return result == solution ? null : result;
    }
}
