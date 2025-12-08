package pl.mlsk.algorithm.impl.lab8.comparator.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.mlsk.algorithm.impl.lab8.comparator.SimilarityComparator;
import pl.mlsk.algorithm.impl.lab8.similarity.SimilarityMeasure;
import pl.mlsk.common.DistanceMatrix;
import pl.mlsk.common.Solution;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class BestSimilarityComparator implements SimilarityComparator {

    private final SimilarityMeasure similarityMeasure;

    @Override
    public Map<Solution, Double> calculateSimilarities(List<Solution> solutions, DistanceMatrix distanceMatrix) {
        var result = new HashMap<Solution, Double>();
        Solution best = solutions.stream()
                .min(Comparator.comparingDouble(Solution::evaluate))
                .orElseThrow();
        for (Solution other : solutions) {
            int comparison = similarityMeasure.similarity(best, other);
            result.put(other, (double) comparison);
        }
        result.remove(best);
        return result;
    }
}
