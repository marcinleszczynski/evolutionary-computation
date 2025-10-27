package pl.mlsk.algorithm.impl.lab3.impl.iterator.grouped;

import pl.mlsk.algorithm.impl.lab3.LocalSearchGroupIterator;
import pl.mlsk.algorithm.impl.lab3.impl.iterator.InterRouteIterator;
import pl.mlsk.algorithm.impl.lab3.impl.iterator.NodeSwapIterator;
import pl.mlsk.common.DistanceMatrix;
import pl.mlsk.common.Node;
import pl.mlsk.common.Solution;

import java.util.List;

public class LocalNodeIterator extends LocalSearchGroupIterator {

    public LocalNodeIterator(Solution solution, DistanceMatrix distanceMatrix, List<Node> nodesOutOfSolution) {
        super(
                new InterRouteIterator(solution, distanceMatrix, nodesOutOfSolution),
                new NodeSwapIterator(solution, distanceMatrix, nodesOutOfSolution)
        );
    }
}
