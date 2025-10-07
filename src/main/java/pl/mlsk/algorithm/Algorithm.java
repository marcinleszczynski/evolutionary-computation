package pl.mlsk.algorithm;

import pl.mlsk.common.AlgorithmInput;
import pl.mlsk.common.Node;
import pl.mlsk.common.Solution;

import java.util.List;

public interface Algorithm {

    Solution solve(AlgorithmInput input, int startNode);

    default long nodesToTake(List<Node> nodes) {
        return Math.round(nodes.size() / 2.0);
    }
}
