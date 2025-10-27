package pl.mlsk.algorithm.impl.lab3.impl.iterator;

import lombok.Getter;
import pl.mlsk.algorithm.impl.lab3.LocalSearchIterator;
import pl.mlsk.common.DistanceMatrix;
import pl.mlsk.common.Node;
import pl.mlsk.common.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class EdgeSwapIterator extends LocalSearchIterator {

    private int firstNodeIndex;
    private int secondNodeIndex;
    @Getter
    private double bestDelta = Double.MAX_VALUE;

    public EdgeSwapIterator(Solution solution, DistanceMatrix distanceMatrix, List<Node> nodesOutOfSolution) {
        super(solution, distanceMatrix, nodesOutOfSolution);
        firstNodeIndex = 0;
        secondNodeIndex = 2;
    }

    @Override
    public boolean hasNext() {
        return firstNodeIndex < solution.orderedNodes().size() - 2;
    }

    @Override
    public Solution next() {
        if (!hasNext()) throw new NoSuchElementException();

        double delta = getDelta();
        if (delta >= 0 || delta >= bestDelta) {
            updateIndexes();
            return null;
        }
        bestDelta = delta;

        List<Node> result = new ArrayList<>(solution.orderedNodes());
        List<Node> nodesToReverse = new ArrayList<>(result.subList(firstNodeIndex + 1, secondNodeIndex + 1)).reversed();
        for (int i = firstNodeIndex + 1; i < secondNodeIndex + 1; i++) {
            result.set(i, nodesToReverse.get(i - (firstNodeIndex + 1)));
        }
        updateIndexes();
        return new Solution(result);
    }

    private void updateIndexes() {
        if (firstNodeIndex == 0 && secondNodeIndex == solution.orderedNodes().size() - 2
                || secondNodeIndex == solution.orderedNodes().size() - 1
        ) {
            firstNodeIndex++;
            secondNodeIndex = firstNodeIndex + 2;
        } else {
            secondNodeIndex++;
        }
    }

    @Override
    protected double getDelta() {
        int nodeAfterFirstIndex = (firstNodeIndex + 1) % solution.orderedNodes().size();
        int nodeAfterSecondIndex = (secondNodeIndex + 1) % solution.orderedNodes().size();

        return distanceMatrix.getDistance(
                solution.orderedNodes().get(firstNodeIndex),
                solution.orderedNodes().get(secondNodeIndex)
        ) + distanceMatrix.getDistance(
                solution.orderedNodes().get(nodeAfterFirstIndex),
                solution.orderedNodes().get(nodeAfterSecondIndex)
        ) - distanceMatrix.getDistance(
                solution.orderedNodes().get(firstNodeIndex),
                solution.orderedNodes().get(nodeAfterFirstIndex)
        ) - distanceMatrix.getDistance(
                solution.orderedNodes().get(secondNodeIndex),
                solution.orderedNodes().get(nodeAfterSecondIndex)
        );
    }
}
