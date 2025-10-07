package pl.mlsk.algorithm;

import pl.mlsk.common.Node;
import pl.mlsk.common.Solution;

import java.util.List;

public interface Algorithm {

    Solution solve(List<Node> nodes, int startNode);

    default long nodesToTake(List<Node> nodes) {
        return Math.round(nodes.size() / 2.0);
    }
}
