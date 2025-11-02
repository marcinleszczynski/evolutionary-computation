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
            Solution nextSolution = nextStep(bestSolution, distanceMatrix, nearestNodeMap);
            if (nextSolution != null) {
                bestSolution = nextSolution;
            } else {
                return bestSolution;
            }
        }
    }

    private Solution nextStep(Solution solution, DistanceMatrix distanceMatrix, NearestNodeMap nearestNodeMap) {
        Iterator<Solution> canditateMovesIterator = new CandidateMovesIterator(solution, distanceMatrix, nearestNodeMap);
        Solution bestSolution = solution;
        while (canditateMovesIterator.hasNext()) {
            Solution nextSolution = canditateMovesIterator.next();
            if (nextSolution != null) {
                bestSolution = nextSolution;
            }
        }
        return solution == bestSolution ? null : bestSolution;
    }

    @Override
    public String algorithmName() {
        return getClass().getSimpleName();
    }
}
