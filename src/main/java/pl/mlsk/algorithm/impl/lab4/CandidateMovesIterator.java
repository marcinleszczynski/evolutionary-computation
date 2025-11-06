package pl.mlsk.algorithm.impl.lab4;

import lombok.RequiredArgsConstructor;
import pl.mlsk.common.DistanceMatrix;
import pl.mlsk.common.NearestNodeMap;
import pl.mlsk.common.Node;
import pl.mlsk.common.Solution;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class CandidateMovesIterator implements Iterator<Solution> {

    private final Solution solution;
    private final DistanceMatrix distanceMatrix;
    private final NearestNodeMap nearestNodeMap;
    private int nodeIndex = 0;
    private int neighborIndex = 0;
    private int variantIndex = 0;

    double bestDelta = 0.0;

    private Node currentNode = null;
    private Node nodeNextToCurrentNode = null;
    private Node candidateNode = null;

    @Override
    public boolean hasNext() {
        return nodeIndex != solution.orderedNodes().size();
    }

    @Override
    public Solution next() {
        if (!hasNext()) throw new NoSuchElementException();

        DeltaResult delta = getDelta();
        if (delta.delta() >= bestDelta) {
            updateIndexes();
            return null;
        }
        bestDelta = delta.delta();

        List<Node> solutionNodes = new ArrayList<>(solution.orderedNodes());
        if (delta.isEdgeSwap()) {
            handleEdgeSwap(solutionNodes);
        } else {
            handleNodeExchange(solutionNodes);
        }
        if (Math.abs(new Solution(solutionNodes).evaluate() - (solution.evaluate() + bestDelta)) >= 0.01) {
            throw new RuntimeException();
        }
        updateIndexes();
        return new Solution(solutionNodes);
    }

    private void handleEdgeSwap(List<Node> solutionNodes) {
        int candidateIndex = solution.orderedNodes().indexOf(candidateNode);
        int a = Math.min(nodeIndex, candidateIndex);
        int b = Math.max(nodeIndex, candidateIndex);
        if (variantIndex == 1) {
            a++;
            b++;
        }
        List<Node> sublist = new ArrayList<>(solution.orderedNodes().subList(a, b).reversed());
        for (int i = a; i < b; i++) {
            solutionNodes.set(i, sublist.get(i - a));
        }
    }

    private void handleNodeExchange(List<Node> solutionNodes) {
        int index = solution.orderedNodes().indexOf(nodeNextToCurrentNode);
        solutionNodes.set(index, candidateNode);
    }

    private DeltaResult getDelta() {
        currentNode = solution.orderedNodes().get(nodeIndex);
        candidateNode = nearestNodeMap.getNeighbor(currentNode, neighborIndex);
        boolean isCandidateInSolution = solution.orderedNodes().contains(candidateNode);

        if (isCandidateInSolution) {
            int candidateIndex = solution.orderedNodes().indexOf(candidateNode);
            int a = Math.min(nodeIndex, candidateIndex);
            int b = Math.max(nodeIndex, candidateIndex);
            if (b - a <= 1 || (solution.orderedNodes().size() - b + a) <= 1) {
                return DeltaResult.edge(0.0); // adjacent candidates - illegal swap
            } else {
                return deltaFromEdgeSwap(candidateIndex);
            }
        }

        return deltaFromInterMove();
    }

    private DeltaResult deltaFromEdgeSwap(int candidateIndex) {
        boolean isVariant0 = variantIndex == 0;
        int nodeNextToCurrent = isVariant0 ?
                (nodeIndex - 1 + solution.orderedNodes().size()) % solution.orderedNodes().size() :
                (nodeIndex + 1) % solution.orderedNodes().size();
        int nodeNextToCandidate = isVariant0 ?
                (candidateIndex - 1 + solution.orderedNodes().size()) % solution.orderedNodes().size() :
                (candidateIndex + 1) % solution.orderedNodes().size();
        nodeNextToCurrentNode = solution.orderedNodes().get(nodeNextToCurrent);
        Node nextToCandidateNode = solution.orderedNodes().get(nodeNextToCandidate);

        return DeltaResult.edge(distanceMatrix.getDistance(
                currentNode, candidateNode
        ) + distanceMatrix.getDistance(
                nodeNextToCurrentNode, nextToCandidateNode
        ) - distanceMatrix.getDistance(
                currentNode, nodeNextToCurrentNode
        ) - distanceMatrix.getDistance(
                candidateNode, nextToCandidateNode
        ));
    }

    private DeltaResult deltaFromInterMove() {
        boolean isVariant0 = variantIndex == 0;
        int nodeNextToCurrent = isVariant0 ?
                (nodeIndex - 1 + solution.orderedNodes().size()) % solution.orderedNodes().size() :
                (nodeIndex + 1) % solution.orderedNodes().size();
        int nodeNextNextToCurrent = isVariant0 ?
                (nodeNextToCurrent - 1 + solution.orderedNodes().size()) % solution.orderedNodes().size() :
                (nodeNextToCurrent + 1) % solution.orderedNodes().size();
        nodeNextToCurrentNode = solution.orderedNodes().get(nodeNextToCurrent);
        Node nodeNextNextToCurrentNode = solution.orderedNodes().get(nodeNextNextToCurrent);

        return DeltaResult.interMove(distanceMatrix.getDistance(
                currentNode, candidateNode
        ) + distanceMatrix.getDistance(
                nodeNextNextToCurrentNode, candidateNode
        ) - distanceMatrix.getDistance(
                currentNode, nodeNextToCurrentNode
        ) - distanceMatrix.getDistance(
                nodeNextToCurrentNode, nodeNextNextToCurrentNode
        ) - nodeNextToCurrentNode.cost() + candidateNode.cost());
    }

    private void updateIndexes() {
        if (variantIndex == 0)
            variantIndex = 1;
        else {
            variantIndex = 0;
            if (neighborIndex < nearestNodeMap.neighborhoodSize() - 1) {
                neighborIndex++;
            } else {
                neighborIndex = 0;
                nodeIndex++;
            }
        }
    }
}