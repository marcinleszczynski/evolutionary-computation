package pl.mlsk.algorithm.impl.lab8.comparator.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.mlsk.algorithm.impl.lab8.comparator.SimilarityComparator;
import pl.mlsk.algorithm.impl.lab8.similarity.SimilarityMeasure;
import pl.mlsk.common.DistanceMatrix;
import pl.mlsk.common.Solution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class AverageSimilarityComparator implements SimilarityComparator {

    private final SimilarityMeasure similarityMeasure;

    @Override
    public Map<Solution, Double> calculateSimilarities(List<Solution> solutions, DistanceMatrix distanceMatrix) {
        var result = new HashMap<Solution, Double>();
        for (int i = 1; i < solutions.size(); i++) {
            Solution current = solutions.get(i);
            for (int j = 0; j < i; j++) {
                Solution other = solutions.get(j);
                int comparison = similarityMeasure.similarity(current, other);
                result.put(current, result.getOrDefault(current, 0.0) + comparison);
                result.put(other, result.getOrDefault(other, 0.0) + comparison);
            }
        }
        result.replaceAll((_, v) -> v / (solutions.size() - 1));
        return result;
    }

}
