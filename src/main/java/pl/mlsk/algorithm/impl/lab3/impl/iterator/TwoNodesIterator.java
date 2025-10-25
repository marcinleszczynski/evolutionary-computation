package pl.mlsk.algorithm.impl.lab3.impl.iterator;

import lombok.Getter;
import pl.mlsk.algorithm.impl.lab3.LocalSearchIterator;
import pl.mlsk.common.DistanceMatrix;
import pl.mlsk.common.Node;
import pl.mlsk.common.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class TwoNodesIterator extends LocalSearchIterator {

    private int solutionNodeIndex;
    private int notSolutionNodeIndex;

    @Getter
    private double bestDelta = Double.MAX_VALUE;

    public TwoNodesIterator(Solution solution, DistanceMatrix distanceMatrix, List<Node> nodesOutOfSolution) {
        super(solution, distanceMatrix, nodesOutOfSolution);
        solutionNodeIndex = 0;
        notSolutionNodeIndex = 0;
    }

    @Override
    public boolean hasNext() {
        return solutionNodeIndex < solution.orderedNodes().size() - 1;
    }

    @Override
    public Solution next() {
        if (!hasNext()) throw new NoSuchElementException();

        double delta = getDelta();
        if (delta >= 0) {
            updateIndexes();
            return null;
        }

        List<Node> nodes = new ArrayList<>(solution.orderedNodes());
        nodes.set(solutionNodeIndex, nodesOutOfSolution.get(notSolutionNodeIndex));
        updateIndexes();
        return new Solution(nodes);
    }

    @Override
    protected double getDelta() {
        int nodeBeforeSolutionNode = (solutionNodeIndex - 1 + solution.orderedNodes().size()) % solution.orderedNodes().size();
        int nodeAfterSolutionNode = (solutionNodeIndex + 1) % solution.orderedNodes().size();

        double delta = distanceMatrix.getDistance(
                solution.orderedNodes().get(nodeBeforeSolutionNode),
                nodesOutOfSolution.get(notSolutionNodeIndex)
        ) + distanceMatrix.getDistance(
                solution.orderedNodes().get(nodeAfterSolutionNode),
                nodesOutOfSolution.get(notSolutionNodeIndex)
        ) - distanceMatrix.getDistance(
                solution.orderedNodes().get(nodeBeforeSolutionNode),
                solution.orderedNodes().get(solutionNodeIndex)
        ) - distanceMatrix.getDistance(
                solution.orderedNodes().get(nodeAfterSolutionNode),
                solution.orderedNodes().get(solutionNodeIndex)
        ) - solution.orderedNodes().get(solutionNodeIndex).cost() + nodesOutOfSolution.get(notSolutionNodeIndex).cost();

        if (delta < bestDelta) {
            bestDelta = delta;
        }
        return delta;
    }

    private void updateIndexes() {
        if (notSolutionNodeIndex == nodesOutOfSolution.size() - 1) {
            solutionNodeIndex++;
            notSolutionNodeIndex = 0;
        } else {
            notSolutionNodeIndex++;
        }
    }
}
