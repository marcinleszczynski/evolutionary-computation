package pl.mlsk.algorithm.impl.lab8.comparator;

import pl.mlsk.algorithm.impl.lab8.similarity.SimilarityMeasure;
import pl.mlsk.common.DistanceMatrix;
import pl.mlsk.common.Solution;

import java.util.List;
import java.util.Map;

public interface SimilarityComparator {

    Map<Solution, Double> calculateSimilarities(List<Solution> solutions, DistanceMatrix distanceMatrix);

    SimilarityMeasure getSimilarityMeasure();
}
