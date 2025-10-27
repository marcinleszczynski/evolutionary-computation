package pl.mlsk.algorithm.impl.lab3.impl.iterator;

import lombok.Getter;
import pl.mlsk.algorithm.impl.lab3.LocalSearchIterator;
import pl.mlsk.common.DistanceMatrix;
import pl.mlsk.common.Node;
import pl.mlsk.common.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class NodeSwapIterator extends LocalSearchIterator {

    @Getter
    private double bestDelta = Double.MAX_VALUE;

    int firstNodeIndex = 0;
    int secondNodeIndex = 1;

    public NodeSwapIterator(Solution solution, DistanceMatrix distanceMatrix, List<Node> nodesOutOfSolution) {
        super(solution, distanceMatrix, nodesOutOfSolution);
    }

    @Override
    protected double getDelta() {
        return switch (secondNodeIndex - firstNodeIndex) {
            case 1, 2 -> deltaForDistanceOneOrTwo();
            default -> delta();
        };
    }

    private double deltaForDistanceOneOrTwo() {
        int size = solution.orderedNodes().size();
        int beforeFirstNodeIndex = (firstNodeIndex - 1 + size) % size;
        int afterSecondNodeIndex = (secondNodeIndex + 1) % size;

        return distanceMatrix.getDistance(
                solution.orderedNodes().get(secondNodeIndex),
                solution.orderedNodes().get(beforeFirstNodeIndex)
        ) + distanceMatrix.getDistance(
                solution.orderedNodes().get(firstNodeIndex),
                solution.orderedNodes().get(afterSecondNodeIndex)
        ) - distanceMatrix.getDistance(
                solution.orderedNodes().get(firstNodeIndex),
                solution.orderedNodes().get(beforeFirstNodeIndex)
        ) - distanceMatrix.getDistance(
                solution.orderedNodes().get(secondNodeIndex),
                solution.orderedNodes().get(afterSecondNodeIndex)
        );
    }

    private double delta() {
        int size = solution.orderedNodes().size();
        int beforeFirstNodeIndex = (firstNodeIndex - 1 + size) % size;
        int afterFirstNodeIndex = (firstNodeIndex + 1) % size;
        int beforeSecondNodeIndex = (secondNodeIndex - 1 + size) % size;
        int afterSecondNodeIndex = (secondNodeIndex + 1) % size;

        return distanceMatrix.getDistance(
                solution.orderedNodes().get(beforeFirstNodeIndex),
                solution.orderedNodes().get(secondNodeIndex)
        ) + distanceMatrix.getDistance(
                solution.orderedNodes().get(firstNodeIndex),
                solution.orderedNodes().get(afterSecondNodeIndex)
        ) + distanceMatrix.getDistance(
                solution.orderedNodes().get(firstNodeIndex),
                solution.orderedNodes().get(beforeSecondNodeIndex)
        ) + distanceMatrix.getDistance(
                solution.orderedNodes().get(afterFirstNodeIndex),
                solution.orderedNodes().get(secondNodeIndex)
        ) - distanceMatrix.getDistance(
                solution.orderedNodes().get(firstNodeIndex),
                solution.orderedNodes().get(beforeFirstNodeIndex)
        ) - distanceMatrix.getDistance(
                solution.orderedNodes().get(firstNodeIndex),
                solution.orderedNodes().get(afterFirstNodeIndex)
        ) - distanceMatrix.getDistance(
                solution.orderedNodes().get(secondNodeIndex),
                solution.orderedNodes().get(beforeSecondNodeIndex)
        ) - distanceMatrix.getDistance(
                solution.orderedNodes().get(secondNodeIndex),
                solution.orderedNodes().get(afterSecondNodeIndex)
        );
    }

    @Override
    public boolean hasNext() {
        return false;
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

        List<Node> nodes = new ArrayList<>(solution.orderedNodes());
        Node temp = nodes.get(firstNodeIndex);
        nodes.set(firstNodeIndex, nodes.get(secondNodeIndex));
        nodes.set(secondNodeIndex, temp);
        updateIndexes();
        return new Solution(nodes);
    }

    private void updateIndexes() {
        if (secondNodeIndex == solution.orderedNodes().size() - 1) {
            firstNodeIndex++;
            secondNodeIndex = firstNodeIndex + 1;
        } else {
            secondNodeIndex++;
        }
    }
}
