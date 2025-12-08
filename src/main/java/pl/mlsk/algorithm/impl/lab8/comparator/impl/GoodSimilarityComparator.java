package pl.mlsk.algorithm.impl.lab8.comparator.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.mlsk.algorithm.impl.lab6.IteratedLocalSearch;
import pl.mlsk.algorithm.impl.lab8.comparator.SimilarityComparator;
import pl.mlsk.algorithm.impl.lab8.similarity.SimilarityMeasure;
import pl.mlsk.common.AlgorithmInput;
import pl.mlsk.common.DistanceMatrix;
import pl.mlsk.common.Solution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class GoodSimilarityComparator implements SimilarityComparator {

    private final SimilarityMeasure similarityMeasure;
    private final IteratedLocalSearch bestMethod;

    @Override
    public Map<Solution, Double> calculateSimilarities(List<Solution> solutions, DistanceMatrix distanceMatrix) {
        Map<Solution, Double> result = new HashMap<>();
        Solution best = bestMethod.solve(
                new AlgorithmInput(distanceMatrix.streamNodes().toList(), distanceMatrix, null), 0
        );
        for (Solution other : solutions) {
            int comparison = similarityMeasure.similarity(best, other);
            result.put(other, (double) comparison);
        }
        return result;
    }
}
