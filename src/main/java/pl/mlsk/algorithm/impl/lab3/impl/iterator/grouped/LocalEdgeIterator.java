package pl.mlsk.algorithm.impl.lab3.impl.iterator.grouped;

import pl.mlsk.algorithm.impl.lab3.LocalSearchGroupIterator;
import pl.mlsk.algorithm.impl.lab3.impl.iterator.EdgeSwapIterator;
import pl.mlsk.algorithm.impl.lab3.impl.iterator.InterRouteIterator;
import pl.mlsk.common.DistanceMatrix;
import pl.mlsk.common.Node;
import pl.mlsk.common.Solution;

import java.util.List;

public class LocalEdgeIterator extends LocalSearchGroupIterator {

    public LocalEdgeIterator(Solution solution, DistanceMatrix distanceMatrix, List<Node> nodesOutOfSolution) {
        super(
                new InterRouteIterator(solution, distanceMatrix, nodesOutOfSolution),
                new EdgeSwapIterator(solution, distanceMatrix, nodesOutOfSolution)
        );
    }
}
