package pl.mlsk.algorithm.impl.lab6;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import pl.mlsk.algorithm.Algorithm;
import pl.mlsk.algorithm.impl.lab1.RandomSearchAlgorithm;
import pl.mlsk.common.AlgorithmInput;
import pl.mlsk.common.DistanceMatrix;
import pl.mlsk.common.Node;
import pl.mlsk.common.Solution;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class IteratedLocalSearch implements Algorithm {

    private final LocalSearch localSearch;
    private final RandomSearchAlgorithm randomSearchAlgorithm;

    private static final long RUNNING_TIME = 15_000_000_000L;

    @Override
    public Solution solve(AlgorithmInput input, int pos) {
        long startTime = System.nanoTime();
        DistanceMatrix distanceMatrix = input.distanceMatrix();
        Solution result = localSearch.localSearch(randomSearchAlgorithm.solve(input, pos), distanceMatrix);
        double bestValue = result.evaluate();
        while (System.nanoTime() - startTime < RUNNING_TIME) {
            Solution perturbation = perturbation(result);
            Solution afterLocalSearch = localSearch.localSearch(perturbation, distanceMatrix);
            double nextValue = afterLocalSearch.evaluate();
            if (nextValue < bestValue) {
                bestValue = nextValue;
                result = afterLocalSearch;
            }
        }
        return result;
    }

    @Override
    public String algorithmName() {
        return getClass().getSimpleName();
    }

    private Solution perturbation(Solution solution) {
        List<Node> nodes = new ArrayList<>(solution.orderedNodes());
        Set<Integer> indexes = new HashSet<>();
        while (indexes.size() < 10) {
            int next = RandomUtils.insecure().randomInt(0, nodes.size());
            indexes.add(next);
        }
        List<Integer> indexList = indexes.stream().toList();
        Node temp = nodes.get(indexList.getFirst());
        for (int i = 1; i < indexList.size(); i++) {
            nodes.set(indexList.get(i - 1), nodes.get(indexList.get(i)));
        }
        nodes.set(indexList.getLast(), temp);
        return new Solution(nodes);
    }
}
