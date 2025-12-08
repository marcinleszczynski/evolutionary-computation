package pl.mlsk.algorithm.impl.lab8.similarity.impl;

import org.springframework.stereotype.Service;
import pl.mlsk.algorithm.impl.lab8.similarity.SimilarityMeasure;
import pl.mlsk.common.Node;
import pl.mlsk.common.Solution;

import java.util.HashSet;
import java.util.Set;

@Service
public class NodeSimilarityMeasure implements SimilarityMeasure {

    @Override
    public int similarity(Solution a, Solution b) {
        Set<Node> set = new HashSet<>(a.orderedNodes());
        set.addAll(b.orderedNodes());
        return a.orderedNodes().size() - (set.size() - a.orderedNodes().size());
    }
}
