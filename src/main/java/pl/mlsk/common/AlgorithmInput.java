package pl.mlsk.common;

import java.util.List;

public record AlgorithmInput(
        List<Node> nodes,
        DistanceMatrix distanceMatrix,
        NearestNodeMap nearestNodeMap
) {
}
