package pl.mlsk.algorithm.impl;

import org.springframework.stereotype.Service;
import pl.mlsk.common.AlgorithmInput;
import pl.mlsk.common.DistanceMatrix;
import pl.mlsk.common.Node;
import pl.mlsk.common.Solution;
import pl.mlsk.algorithm.Algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class NearestNeighborLastNode implements Algorithm {

    @Override
    public Solution solve(AlgorithmInput input, int startNode) {
        List<Node> nodes = input.nodes();
        DistanceMatrix distanceMatrix = input.distanceMatrix();
        long nodesToTake = nodesToTake(nodes);
        List<Node> solutions = new ArrayList<>();
        List<Node> available = new ArrayList<>(nodes);
        solutions.add(available.get(startNode));
        available.remove(startNode);

        for (int i = 0; i < nodesToTake - 1; i++) {
            Node closestNextNode = available
                    .stream()
                    .min(Comparator.comparingDouble(node -> distanceMatrix.getDistance(solutions.getLast(), node)))
                    .orElse(available.get(i));

            solutions.add(closestNextNode);
            available.remove(closestNextNode);
        }
        return new Solution(solutions);
    }
}
