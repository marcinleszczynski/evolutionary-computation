package pl.mlsk.algorithm.impl.lab7;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.mlsk.algorithm.Algorithm;
import pl.mlsk.algorithm.impl.lab1.RandomSearchAlgorithm;
import pl.mlsk.algorithm.impl.lab2.Greedy2RegretWeightedSum;
import pl.mlsk.algorithm.impl.lab6.LocalSearch;
import pl.mlsk.common.AlgorithmInput;
import pl.mlsk.common.Node;
import pl.mlsk.common.Solution;
import pl.mlsk.utils.RandomUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public abstract class LargeNeighborhoodSearch implements Algorithm {

    @Setter(onMethod_ = {@Autowired, @Qualifier("greedy2RegretWeightedSumGreedyCycle")})
    private Greedy2RegretWeightedSum greedy;

    @Setter(onMethod_ = @Autowired)
    private RandomSearchAlgorithm randomSearchAlgorithm;

    @Setter(onMethod_ = @Autowired)
    private LocalSearch localSearch;

    //    private static final long RUNNING_TIME = 18_600_000_000L; // DATASET A: 18_600_000_000L
    private static final long RUNNING_TIME = 19_600_000_000L; // DATASET B: 19_600_000_000L
    private static final int DESTROY_SIZE = 30;

    @Override
    public Solution solve(AlgorithmInput input, int pos) {
        Solution result = randomSearchAlgorithm.solve(input, pos);
        if (shouldLocalSearch())
            result = localSearch.localSearch(result, input.distanceMatrix());
        double best = result.evaluate();

        long start = System.nanoTime();
        while (System.nanoTime() - start < RUNNING_TIME) {
            Solution newSolution = repair(destroy(result), input);
            if (shouldLocalSearch())
                newSolution = localSearch.localSearch(newSolution, input.distanceMatrix());
            double value = newSolution.evaluate();
            if (value < best) {
                result = newSolution;
                best = value;
            }
        }
        return result;
    }

    private Solution destroy(Solution solution) {
        List<Node> result = new ArrayList<>(solution.orderedNodes());
        int index = RandomUtils.nextInt(result.size());
        int index2 = (index + DESTROY_SIZE - 1) % result.size();
        boolean outsideRange = index < index2;
        if (!outsideRange) {
            result.subList(index + 1, result.size()).clear();
            result.subList(0, index2).clear();
        } else {
            result.subList(index, index2 + 1).clear();
        }
        return new Solution(result);
    }

    private Solution repair(Solution solution, AlgorithmInput input) {
        return greedy.solveForCurrent(input, solution);
    }

    @Override
    public String algorithmName() {
        return getClass().getSimpleName();
    }

    protected abstract boolean shouldLocalSearch();
}
