package pl.mlsk.algorithm.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mlsk.common.Node;
import pl.mlsk.common.Solution;
import pl.mlsk.algorithm.Algorithm;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RandomSearchAlgorithm implements Algorithm {

    private final Random random;

    @Override
    public Solution solve(List<Node> nodes, int startNode) {
        long nodesToTake = nodesToTake(nodes);
        List<Node> available = new ArrayList<>(nodes);
        List<Node> resultNodes = new ArrayList<>();

        for (long i = 0; i < nodesToTake; i++) {
            int index = i == 0 ? startNode : random.nextInt(0, available.size());
            resultNodes.add(available.get(index));
            available.remove(available.get(index));
        }

        return new Solution(resultNodes);
    }
}
