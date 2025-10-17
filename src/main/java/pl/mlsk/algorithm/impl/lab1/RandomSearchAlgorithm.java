package pl.mlsk.algorithm.impl.lab1;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mlsk.algorithm.GreedyAlgorithm;
import pl.mlsk.algorithm.impl.records.NodeWithIndex;
import pl.mlsk.common.DistanceMatrix;
import pl.mlsk.common.Node;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RandomSearchAlgorithm extends GreedyAlgorithm {

    private final Random random;

    @Override
    protected NodeWithIndex bestNextNode(DistanceMatrix distanceMatrix, List<Node> available, List<Node> result) {
        int index = random.nextInt(0, available.size());
        return new NodeWithIndex(available.get(index), result.size());
    }
}
