package pl.mlsk.algorithm;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mlsk.algorithm.impl.lab1.RandomSearchAlgorithm;
import pl.mlsk.algorithm.impl.lab4.CandidateMovesIterator;
import pl.mlsk.common.AlgorithmInput;
import pl.mlsk.common.DistanceMatrix;
import pl.mlsk.common.NearestNodeMap;
import pl.mlsk.common.Solution;

import java.util.Iterator;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class CandidateMovesAlgorithm implements Algorithm {

    private final RandomSearchAlgorithm randomSearchAlgorithm;

    @Override
    public Solution solve(AlgorithmInput input, int pos) {
        Solution initialSolution = randomSearchAlgorithm.solve(input, pos);
        return candidateMovesSearch(initialSolution, input.distanceMatrix(), input.nearestNodeMap());
    }

    private Solution candidateMovesSearch(Solution initialSolution, DistanceMatrix distanceMatrix, NearestNodeMap nearestNodeMap) {
        Solution bestSolution = initialSolution;
        while (true) {
            Solution nextSolution = nextBestSolution(bestSolution, distanceMatrix, nearestNodeMap);
            if (isNull(nextSolution)) {
                return bestSolution;
            }
            bestSolution = nextSolution;
        }
    }

    private Solution nextBestSolution(Solution solution, DistanceMatrix distanceMatrix, NearestNodeMap nearestNodeMap) {
        Iterator<Solution> iterator = new CandidateMovesIterator(solution, distanceMatrix, nearestNodeMap);
        Solution result = solution;
        while (iterator.hasNext()) {
            Solution nextSolution = iterator.next();
            if (nonNull(nextSolution)) {
                result = nextSolution;
            }
        }
        return result == solution ? null : result;
    }

    @Override
    public String algorithmName() {
        return getClass().getSimpleName();
    }
}
