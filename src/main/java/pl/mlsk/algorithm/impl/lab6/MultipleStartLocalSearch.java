package pl.mlsk.algorithm.impl.lab6;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mlsk.algorithm.Algorithm;
import pl.mlsk.algorithm.impl.lab1.RandomSearchAlgorithm;
import pl.mlsk.common.AlgorithmInput;
import pl.mlsk.common.DistanceMatrix;
import pl.mlsk.common.Solution;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class MultipleStartLocalSearch implements Algorithm {

    private final LocalSearch localSearch;
    private final RandomSearchAlgorithm randomSearchAlgorithm;

    @Override
    public Solution solve(AlgorithmInput input, int pos) {
        DistanceMatrix distanceMatrix = input.distanceMatrix();

        List<Solution> result = IntStream.range(0, 200)
                .parallel()
                .mapToObj(_ -> {
                    Solution randomSolution = randomSearchAlgorithm.solve(input, pos);
                    return localSearch.localSearch(randomSolution, distanceMatrix);
                })
                .toList();

        return result.stream()
                .min(Comparator.comparingDouble(Solution::evaluate))
                .orElseThrow();
    }

    @Override
    public String algorithmName() {
        return getClass().getSimpleName();
    }
}
