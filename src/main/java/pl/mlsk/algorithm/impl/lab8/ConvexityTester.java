package pl.mlsk.algorithm.impl.lab8;

import lombok.RequiredArgsConstructor;
import pl.mlsk.algorithm.impl.lab3.impl.GreedyLocalSearch;
import pl.mlsk.algorithm.impl.lab8.comparator.SimilarityComparator;
import pl.mlsk.algorithm.impl.lab8.plot.ConvexityPlotService;
import pl.mlsk.common.AlgorithmInput;
import pl.mlsk.common.Solution;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class ConvexityTester {

    private final SimilarityComparator similarityComparator;
    private final GreedyLocalSearch greedy;
    private final ConvexityPlotService convexityPlotService;

    public void test(AlgorithmInput input) {
        List<Solution> solutions = IntStream.range(0, 1000)
                .mapToObj(_ -> greedy.solve(input, 0))
                .toList();
        Map<Solution, Double> similaritiesMap = similarityComparator.calculateSimilarities(solutions, input.distanceMatrix());
        convexityPlotService.plot(similaritiesMap, plotText());
    }

    private String plotText() {
        return String.join(" - ",
                getClass().getSimpleName(),
                similarityComparator.getClass().getSimpleName(),
                similarityComparator.getSimilarityMeasure().getClass().getSimpleName()
        );
    }
}
