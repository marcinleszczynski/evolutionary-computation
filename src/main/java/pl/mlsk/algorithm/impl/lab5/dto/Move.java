package pl.mlsk.algorithm.impl.lab5.dto;

import pl.mlsk.common.Node;

import java.util.List;

public record Move(
        List<Edge> toAdd,
        List<Edge> toDelete,
        double delta,
        boolean isEdgeSwap,
        Node nodeToRemove,
        Node nodeToAdd
) {

    public boolean isInterMove() {
        return !isEdgeSwap;
    }

    public Move(List<Edge> toAdd, List<Edge> toDelete, double delta, boolean isEdgeSwap) {
        this(toAdd, toDelete, delta, isEdgeSwap, null, null);
    }
}