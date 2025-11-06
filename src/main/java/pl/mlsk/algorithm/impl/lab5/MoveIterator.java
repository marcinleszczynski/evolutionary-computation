package pl.mlsk.algorithm.impl.lab5;

import lombok.RequiredArgsConstructor;
import pl.mlsk.algorithm.impl.lab4.DeltaResult;
import pl.mlsk.common.DistanceMatrix;
import pl.mlsk.common.NearestNodeMap;
import pl.mlsk.common.Node;
import pl.mlsk.common.Solution;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class MoveIterator implements Iterator<Move> {

    private final Solution solution;
    private final DistanceMatrix distanceMatrix;
    private final NearestNodeMap nearestNodeMap;
    private int nodeIndex = 0;
    private int neighborIndex = 0;
    private int variantIndex = 0;

    private Node currentNode = null;
    private Node nodeNextToCurrentNode = null;
    private Node nodeNextNextToCurrentNode = null;
    private Node candidateNode = null;
    private Node nodeNextToCandidateNode = null;

    int candidateNodeIndex = 0;
    int nextToCandidateNodeIndex = 0;
    int nextToCurrentNodeIndex = 0;
    int nextNextToCurrentNodeIndex = 0;


    @Override
    public boolean hasNext() {
        return nodeIndex != solution.orderedNodes().size();
    }

    @Override
    public Move next() {
        if (!hasNext()) throw new NoSuchElementException();

        DeltaResult delta = getDelta();
        if (delta.delta() >= 0) {
            updateIndexes();
            return null;
        }
        boolean isVariant0 = variantIndex == 0;
        if (delta.isEdgeSwap()) {
            if (nodeIndex < candidateNodeIndex) {
                Edge toAdd1 = new Edge(currentNode, candidateNode);
                Edge toAdd2 = new Edge(nodeNextToCurrentNode, nodeNextToCandidateNode);

                Edge toRemove1 = isVariant0 ? new Edge(currentNode, nodeNextToCurrentNode) : new Edge(nodeNextToCurrentNode, currentNode);
                Edge toRemove2 = isVariant0 ? new Edge(candidateNode, nodeNextToCandidateNode) : new Edge(nodeNextToCandidateNode, candidateNode);
                List<Edge> toAdd = List.of(toAdd1, toAdd2);
                List<Edge> toRemove = List.of(toRemove1, toRemove2);
                updateIndexes();
                return new Move(toAdd, toRemove, delta.delta(), true);
            } else {
                Edge toAdd1 = new Edge(candidateNode, currentNode);
                Edge toAdd2 = new Edge(nodeNextToCandidateNode, nodeNextToCurrentNode);

                Edge toRemove1 = isVariant0 ? new Edge(nodeNextToCurrentNode, currentNode) : new Edge(currentNode, nodeNextToCurrentNode);
                Edge toRemove2 = isVariant0 ? new Edge(nodeNextToCandidateNode, candidateNode) : new Edge(candidateNode, nodeNextToCandidateNode);
                List<Edge> toAdd = List.of(toAdd1, toAdd2);
                List<Edge> toRemove = List.of(toRemove1, toRemove2);
                updateIndexes();
                return new Move(toAdd, toRemove, delta.delta(), true);
            }
        } else {
            if (nodeIndex < nextNextToCurrentNodeIndex) {
                Edge toAdd1 = new Edge(currentNode, candidateNode);
                Edge toAdd2 = new Edge(candidateNode, nodeNextNextToCurrentNode);

                Edge toRemove1 = new Edge(currentNode, nodeNextToCurrentNode);
                Edge toRemove2 = new Edge(nodeNextToCurrentNode, nodeNextNextToCurrentNode);

                List<Edge> toAdd = List.of(toAdd1, toAdd2);
                List<Edge> toRemove = List.of(toRemove1, toRemove2);
                updateIndexes();
                return new Move(toAdd, toRemove, delta.delta(), false);
            } else {
                Edge toAdd1 = new Edge(candidateNode, currentNode);
                Edge toAdd2 = new Edge(nodeNextNextToCurrentNode, candidateNode);

                Edge toRemove1 = new Edge(nodeNextToCurrentNode, currentNode);
                Edge toRemove2 = new Edge(nodeNextNextToCurrentNode, nodeNextToCurrentNode);

                List<Edge> toAdd = List.of(toAdd1, toAdd2);
                List<Edge> toRemove = List.of(toRemove1, toRemove2);
                updateIndexes();
                return new Move(toAdd, toRemove, delta.delta(), false);
            }
        }
    }

    private DeltaResult getDelta() {
        currentNode = solution.orderedNodes().get(nodeIndex);
        candidateNode = nearestNodeMap.getNeighbor(currentNode, neighborIndex);
        boolean isCandidateInSolution = solution.orderedNodes().contains(candidateNode);

        if (isCandidateInSolution) {
            candidateNodeIndex = solution.orderedNodes().indexOf(candidateNode);
            int a = Math.min(nodeIndex, candidateNodeIndex);
            int b = Math.max(nodeIndex, candidateNodeIndex);
            if (b - a <= 1 || (solution.orderedNodes().size() - b + a) <= 1) {
                return DeltaResult.edge(0.0); // adjacent candidates - illegal swap
            } else {
                return deltaFromEdgeSwap();
            }
        }

        return deltaFromInterMove();
    }

    private DeltaResult deltaFromEdgeSwap() {
        boolean isVariant0 = variantIndex == 0;
        nextToCurrentNodeIndex = isVariant0 ?
                (nodeIndex - 1 + solution.orderedNodes().size()) % solution.orderedNodes().size() :
                (nodeIndex + 1) % solution.orderedNodes().size();
        nextToCandidateNodeIndex = isVariant0 ?
                (candidateNodeIndex - 1 + solution.orderedNodes().size()) % solution.orderedNodes().size() :
                (candidateNodeIndex + 1) % solution.orderedNodes().size();
        nodeNextToCurrentNode = solution.orderedNodes().get(nextToCurrentNodeIndex);
        nodeNextToCandidateNode = solution.orderedNodes().get(nextToCandidateNodeIndex);

        return DeltaResult.edge(distanceMatrix.getDistance(
                currentNode, candidateNode
        ) + distanceMatrix.getDistance(
                nodeNextToCurrentNode, nodeNextToCandidateNode
        ) - distanceMatrix.getDistance(
                currentNode, nodeNextToCurrentNode
        ) - distanceMatrix.getDistance(
                candidateNode, nodeNextToCandidateNode
        ));
    }

    private DeltaResult deltaFromInterMove() {
        boolean isVariant0 = variantIndex == 0;
        nextToCurrentNodeIndex = isVariant0 ?
                (nodeIndex - 1 + solution.orderedNodes().size()) % solution.orderedNodes().size() :
                (nodeIndex + 1) % solution.orderedNodes().size();
        nextNextToCurrentNodeIndex = isVariant0 ?
                (nextToCurrentNodeIndex - 1 + solution.orderedNodes().size()) % solution.orderedNodes().size() :
                (nextToCurrentNodeIndex + 1) % solution.orderedNodes().size();
        nodeNextToCurrentNode = solution.orderedNodes().get(nextToCurrentNodeIndex);
        nodeNextNextToCurrentNode = solution.orderedNodes().get(nextNextToCurrentNodeIndex);

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
