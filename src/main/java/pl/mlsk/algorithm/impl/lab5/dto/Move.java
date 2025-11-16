package pl.mlsk.algorithm.impl.lab5.dto;

import java.util.List;

public record Move(
        List<Edge> toAdd,
        List<Edge> toDelete,
        double delta,
        boolean isEdgeSwap
) {

    public boolean isInterMove() {
        return !isEdgeSwap;
    }
}