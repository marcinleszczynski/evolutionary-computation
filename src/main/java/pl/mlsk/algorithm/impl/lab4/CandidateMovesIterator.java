package pl.mlsk.algorithm.impl.lab4;

import lombok.RequiredArgsConstructor;
import pl.mlsk.common.DistanceMatrix;
import pl.mlsk.common.NearestNodeMap;
import pl.mlsk.common.Node;
import pl.mlsk.common.Solution;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
public class CandidateMovesIterator implements Iterator<Solution> {

    private final Solution solution;
    private final DistanceMatrix distanceMatrix;
    private final NearestNodeMap nearestNodeMap;
    private int nodeIndex = 0;
    private int neighborIndex = 0;
    private int variantIndex = 0;

    double bestDelta = 0.0;

    private Node beforeBeforeCurrentNode = null;
    private Node beforeCurrentNode = null;
    private Node currentNode = null;
    private Node afterCurrentNode = null;
    private Node afterAfterCurrentNode = null;

    private Node beforeCandidateNode = null;
    private Node candidateNode = null;
    private Node afterCandidateNode = null;

    @Override
    public boolean hasNext() {
        return nodeIndex != solution.orderedNodes().size();
    }

    @Override
    public Solution next() {
        DeltaResult delta = getDelta();

        if (delta.delta == null || delta.delta() >= bestDelta) {
            updateIndexes();
            return null;
        }
        bestDelta = delta.delta();
        List<Node> solutionNodes = new ArrayList<>(solution.orderedNodes());
        if (delta.isEdgeSwap()) {
            int candidateIndex = solution.orderedNodes().indexOf(candidateNode);
            int a = Math.min(nodeIndex, candidateIndex);
            int b = Math.max(nodeIndex, candidateIndex);
            if (variantIndex == 0) {
                List<Node> sublist = new ArrayList<>(solution.orderedNodes().subList(a, b).reversed());
                for (int i = a; i <= b - 1; i++) {
                    solutionNodes.set(i, sublist.get(i - a));
                }
            } else {
                List<Node> sublist = new ArrayList<>(solution.orderedNodes().subList(a + 1, b + 1).reversed());
                for (int i = a + 1; i <= b; i++) {
                    solutionNodes.set(i, sublist.get(i - a - 1));
                }
            }
        } else {
            if (variantIndex == 0) {
                int nodeBeforeCurrentIndex = solution.orderedNodes().indexOf(beforeCurrentNode);
                solutionNodes.set(nodeBeforeCurrentIndex, candidateNode);
            } else {
                int nodeAfterCurrentIndex = solution.orderedNodes().indexOf(afterCurrentNode);
                solutionNodes.set(nodeAfterCurrentIndex, candidateNode);
            }
        }
        updateIndexes();
        return new Solution(solutionNodes);
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
                return DeltaResult.edge(null); // adjacent candidates - illegal swap
            } else {
                return deltaFromEdgeSwap(candidateIndex);
            }
        }

        return deltaFromInterMove();
    }

    private DeltaResult deltaFromEdgeSwap(int candidateIndex) {
        if (variantIndex == 0) {
            int nodeBeforeCurrentIndex = (nodeIndex - 1 + solution.orderedNodes().size()) % solution.orderedNodes().size();
            int nodeBeforeCandidateIndex = (candidateIndex - 1 + solution.orderedNodes().size()) % solution.orderedNodes().size();

            beforeCurrentNode = solution.orderedNodes().get(nodeBeforeCurrentIndex);
            beforeCandidateNode = solution.orderedNodes().get(nodeBeforeCandidateIndex);

            return DeltaResult.edge(distanceMatrix.getDistance(
                    currentNode, candidateNode
            ) + distanceMatrix.getDistance(
                    beforeCurrentNode, beforeCandidateNode
            ) - distanceMatrix.getDistance(
                    currentNode, beforeCurrentNode
            ) - distanceMatrix.getDistance(
                    candidateNode, beforeCandidateNode
            ));
        } else {
            int nodeAfterCurrentIndex = (nodeIndex + 1) % solution.orderedNodes().size();
            int nodeAfterCandidateIndex = (candidateIndex + 1) % solution.orderedNodes().size();

            afterCurrentNode = solution.orderedNodes().get(nodeAfterCurrentIndex);
            afterCandidateNode = solution.orderedNodes().get(nodeAfterCandidateIndex);

            return DeltaResult.edge(distanceMatrix.getDistance(
                    currentNode, candidateNode
            ) + distanceMatrix.getDistance(
                    afterCurrentNode, afterCandidateNode
            ) - distanceMatrix.getDistance(
                    currentNode, afterCurrentNode
            ) - distanceMatrix.getDistance(
                    candidateNode, afterCandidateNode
            ));
        }
    }

    private DeltaResult deltaFromInterMove() {
        if (variantIndex == 0) {
            int nodeBeforeCurrentIndex = (nodeIndex - 1 + solution.orderedNodes().size()) % solution.orderedNodes().size();
            int nodeBeforeBeforeCurrentIndex = (nodeBeforeCurrentIndex - 1 + solution.orderedNodes().size()) % solution.orderedNodes().size();

            beforeCurrentNode = solution.orderedNodes().get(nodeBeforeCurrentIndex);
            beforeBeforeCurrentNode = solution.orderedNodes().get(nodeBeforeBeforeCurrentIndex);

            return DeltaResult.interMove(distanceMatrix.getDistance(
                    currentNode, candidateNode
            ) + distanceMatrix.getDistance(
                    beforeBeforeCurrentNode, candidateNode
            ) - distanceMatrix.getDistance(
                    currentNode, beforeCurrentNode
            ) - distanceMatrix.getDistance(
                    beforeCurrentNode, beforeBeforeCurrentNode
            ) - beforeCurrentNode.cost() + candidateNode.cost());
        } else {
            int nodeAfterCurrentIndex = (nodeIndex + 1) % solution.orderedNodes().size();
            int nodeAfterAfterCurrentIndex = (nodeAfterCurrentIndex + 1) % solution.orderedNodes().size();

            afterCurrentNode = solution.orderedNodes().get(nodeAfterCurrentIndex);
            afterAfterCurrentNode = solution.orderedNodes().get(nodeAfterAfterCurrentIndex);

            return DeltaResult.interMove(distanceMatrix.getDistance(
                    currentNode, candidateNode
            ) + distanceMatrix.getDistance(
                    afterAfterCurrentNode, candidateNode
            ) - distanceMatrix.getDistance(
                    currentNode, afterCurrentNode
            ) - distanceMatrix.getDistance(
                    afterCurrentNode, afterAfterCurrentNode
            ) - afterCurrentNode.cost() + candidateNode.cost());
        }
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

    private record DeltaResult(
            Double delta,
            boolean isEdgeSwap
    ) {
        public static DeltaResult edge(Double delta) {
            return new DeltaResult(delta, true);
        }

        public static DeltaResult interMove(double delta) {
            return new DeltaResult(delta, false);
        }
    }
}
