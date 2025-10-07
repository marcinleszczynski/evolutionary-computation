package pl.mlsk.analyser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mlsk.algorithm.Algorithm;
import pl.mlsk.common.AlgorithmInput;
import pl.mlsk.common.Node;
import pl.mlsk.common.NodeReader;
import pl.mlsk.common.Solution;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class Analyser {

    private final NodeReader reader;

    public AnalysisResult analyse(String pathToData, Algorithm algorithm) {
        AlgorithmInput algorithmInput = reader.readNodes(pathToData);
        List<Node> nodes = algorithmInput.nodes();
        List<Double> times = new ArrayList<>();
        List<Double> scores = new ArrayList<>();
        Solution bestSolution = null;

        for (int i = 0; i < nodes.size(); i++) {
            long start = System.nanoTime();
            Solution solution = algorithm.solve(algorithmInput, i);
            double time = (System.nanoTime() - start) / 1_000_000_000.0;
            times.add(time);
            scores.add(solution.evaluate());

            if (isNull(bestSolution) || solution.evaluate() < bestSolution.evaluate()) {
                bestSolution = solution;
            }
        }

        double bestValue = scores
                .stream()
                .mapToDouble(Double::doubleValue)
                .min()
                .orElseThrow(RuntimeException::new);

        double bestTime = times.get(scores.indexOf(bestValue));

        double avgValue = scores
                .stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElseThrow(RuntimeException::new);

        double avgTime = times
                .stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElseThrow(RuntimeException::new);

        double worstValue = scores
                .stream()
                .mapToDouble(Double::doubleValue)
                .max()
                .orElseThrow(RuntimeException::new);

        double worstTime = times.get(scores.indexOf(worstValue));

        return new AnalysisResult(
                bestValue, bestTime,
                avgValue, avgTime,
                worstValue, worstTime,
                bestSolution
        );
    }
}
