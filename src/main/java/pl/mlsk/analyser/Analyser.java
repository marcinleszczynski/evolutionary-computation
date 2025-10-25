package pl.mlsk.analyser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mlsk.algorithm.Algorithm;
import pl.mlsk.common.AlgorithmInput;
import pl.mlsk.common.Node;
import pl.mlsk.common.NodeReader;
import pl.mlsk.common.Solution;
import pl.mlsk.vizualization.Visualizer;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class Analyser {

    private static final String TEN_EQUALS = "=".repeat(5);
    private static final String FORTY_EQUALS = "=".repeat(40);

    private final NodeReader reader;
    private final Visualizer visualizer;

    public void analyse(String pathToData, Algorithm algorithm) {
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

        handleResults(algorithm.algorithmName(), scores, times, bestSolution, nodes);
    }

    private void handleResults(String algorithmName, List<Double> scores, List<Double> times, Solution bestSolution, List<Node> nodes) {
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

        AnalysisResult analysisResult = new AnalysisResult(
                bestValue, bestTime,
                avgValue, avgTime,
                worstValue, worstTime,
                bestSolution
        );

        showResult(algorithmName, analysisResult);
        IO.println(bestSolution.orderedNodes().stream().map(nodes::indexOf).toList());
        visualizer.visualize(nodes, bestSolution.orderedNodes());
    }

    private void showResult(String title, AnalysisResult analysisResult) {
        IO.println("\n\n\n" + FORTY_EQUALS);
        IO.println(TEN_EQUALS + " ".repeat(timesRepeat(title.length()) / 2) + title + " ".repeat(timesRepeat(title.length()) / 2) + TEN_EQUALS);
        IO.println(FORTY_EQUALS);
        IO.println("Best value: " + analysisResult.bestValue() + "\t\t" + "time: " + analysisResult.bestValueTime() + "s");
        IO.println("Average value: " + analysisResult.averageValue() + "\t\t" + "time: " + analysisResult.averageValueTime() + "s");
        IO.println("Worst value: " + analysisResult.worstValue() + "\t\t" + "time: " + analysisResult.worstValueTime() + "s");
        IO.println(FORTY_EQUALS);
    }

    private int timesRepeat(int length) {
        return Math.max(1, 30 - length);
    }
}
