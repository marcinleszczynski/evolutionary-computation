package pl.mlsk.algorithm;

import pl.mlsk.algorithm.impl.records.NodeWithIndex;
import pl.mlsk.common.AlgorithmInput;
import pl.mlsk.common.DistanceMatrix;
import pl.mlsk.common.Node;
import pl.mlsk.common.Solution;

import java.util.ArrayList;
import java.util.List;

public abstract class GreedyAlgorithm implements Algorithm {

    @Override
    public Solution solve(AlgorithmInput input, int startNode) {
        List<Node> nodes = input.nodes();
        DistanceMatrix distanceMatrix = input.distanceMatrix();
        long nodesToTake = nodesToTake(nodes);
        ArrayList<Node> result = new ArrayList<>();
        ArrayList<Node> available = new ArrayList<>(nodes);
        result.add(available.get(startNode));
        available.remove(startNode);

        for (long i = 1; i < nodesToTake; i++) {
            NodeWithIndex nextNode = bestNextNode(distanceMatrix, available, result);
            result.add(nextNode.index(), nextNode.node());
            available.remove(nextNode.node());
        }

        return new Solution(result);
    }

    protected abstract NodeWithIndex bestNextNode(DistanceMatrix distanceMatrix, List<Node> available, List<Node> result);

    protected long nodesToTake(List<Node> nodes) {
        return Math.round(nodes.size() / 2.0);
    }

    @Override
    public String algorithmName() {
        return this.getClass().getSimpleName();
    }
}
