package pl.mlsk.algorithm.impl.lab8.similarity.impl;

import org.springframework.stereotype.Service;
import pl.mlsk.algorithm.impl.lab8.similarity.SimilarityMeasure;
import pl.mlsk.common.Node;
import pl.mlsk.common.Solution;
import pl.mlsk.utils.MapUtils;

import java.util.Map;

@Service
public class EdgeSimilarityMeasure implements SimilarityMeasure {

    @Override
    public int similarity(Solution a, Solution b) {
        Map<Node, Node> map1 = MapUtils.mapFromSolution(a);
        Map<Node, Node> map2 = MapUtils.mapFromSolution(b);

        return (int) map1.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(map2.get(entry.getKey())) || entry.getKey().equals(map2.get(entry.getValue())))
                .count();
    }
}
