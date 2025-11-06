package pl.mlsk.algorithm.impl.lab5;

import java.util.List;

public record Move(
        List<Edge> toAdd,
        List<Edge> toDelete,
        double delta,
        boolean isEdgeSwap
) {
}
