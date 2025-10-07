package pl.mlsk.algorithm.impl;

import org.springframework.stereotype.Service;
import pl.mlsk.algorithm.Algorithm;
import pl.mlsk.algorithm.impl.records.NodeWithIndex;
import pl.mlsk.common.Node;
import pl.mlsk.common.Solution;
import pl.mlsk.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class GreedyCycleAlgorithm implements Algorithm {

    @Override
    public Solution solve(List<Node> nodes, int startNode) {
        long nodesToTake = nodesToTake(nodes);
        List<Node> solutions = new ArrayList<>();
        List<Node> available = new ArrayList<>(nodes);
        solutions.add(available.get(startNode));
        available.remove(startNode);

        for (long i = 1; i < nodesToTake; i++) {
            NodeWithIndex newNode = findNewNodeForCycle(available, solutions);
            solutions.add(newNode.index(), newNode.node());
            available.remove(newNode.node());
        }

        return new Solution(solutions);
    }

    private NodeWithIndex findNewNodeForCycle(List<Node> available, List<Node> solutions) {

        Node bestNode = null;
        int bestIndex = -1;
        double bestValue = Double.MAX_VALUE;

        for (Node nodeCandidate : available) {
            for (int i = 0; i < solutions.size() + 1; i++) {
                List<Node> nodes = ListUtils.listWithElementAtIndex(solutions, nodeCandidate, i);
                double value = new Solution(nodes).evaluate();
                if (value < bestValue) {
                    bestNode = nodeCandidate;
                    bestIndex = i;
                    bestValue = value;
                }
            }
        }
        return new NodeWithIndex(bestNode, bestIndex);
    }
}
