package pl.mlsk.algorithm.impl.lab1;

import org.springframework.stereotype.Service;
import pl.mlsk.algorithm.GreedyAlgorithm;
import pl.mlsk.algorithm.impl.records.NodeWithIndex;
import pl.mlsk.common.DistanceMatrix;
import pl.mlsk.common.Node;

import java.util.Comparator;
import java.util.List;

@Service
public class NearestNeighborLastNode extends GreedyAlgorithm {

    @Override
    protected NodeWithIndex bestNextNode(DistanceMatrix distanceMatrix, List<Node> available, List<Node> result) {
        Node bestNode = available
                .stream()
                .min(Comparator.comparingDouble(node -> distanceMatrix.getDistance(result.getLast(), node) + node.cost()))
                .orElseThrow(RuntimeException::new);

        return new NodeWithIndex(bestNode, result.size());
    }
}
