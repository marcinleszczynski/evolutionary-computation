package pl.mlsk;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import pl.mlsk.analyser.Analyser;
import pl.mlsk.algorithm.impl.*;
import pl.mlsk.analyser.AnalysisResult;
import pl.mlsk.common.AlgorithmInput;
import pl.mlsk.common.Node;
import pl.mlsk.common.NodeReader;
import pl.mlsk.vizualization.Visualizer;

import java.util.List;

@Service
@RequiredArgsConstructor
@ComponentScan("pl.mlsk")
public class Main {

    private final Analyser analyser;
    private final RandomSearchAlgorithm randomSearchAlgorithm;
    private final NearestNeighborAnyNode nearestNeighborAnyNode;
    private final NearestNeighborLastNode nearestNeighborLastNode;
    private final GreedyCycleAlgorithm greedyCycleAlgorithm;
    private final Visualizer visualizer;
    private final NodeReader nodeReader;

    private static final String TEN_EQUALS = "=".repeat(5);
    private static final String FORTY_EQUALS = "=".repeat(40);

    private static final String DATA_A = "/TSPA.csv";
    private static final String DATA_B = "/TSPB.csv";

    public static void main(String[] args) {

        System.setProperty("org.graphstream.ui", "swing");

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        Main main = context.getBean(Main.class);
        main.run();
    }

    public void run() {

        AlgorithmInput algorithmInputA = nodeReader.readNodes(DATA_A);
//        AlgorithmInput algorithmInputB = nodeReader.readNodes(DATA_B);

        AnalysisResult analysisResultA1 = analyser.analyse(DATA_A, nearestNeighborLastNode);
        showResult(nearestNeighborLastNode.getClass().getSimpleName(), analysisResultA1);
        IO.println(analysisResultA1.bestSolution().orderedNodes().stream().map(algorithmInputA.nodes()::indexOf).toList());
        visualizer.visualize(algorithmInputA.nodes(), analysisResultA1.bestSolution().orderedNodes());

        AnalysisResult analysisResultA2 = analyser.analyse(DATA_A, nearestNeighborAnyNode);
        showResult(nearestNeighborAnyNode.getClass().getSimpleName(), analysisResultA2);
        IO.println(analysisResultA2.bestSolution().orderedNodes().stream().map(algorithmInputA.nodes()::indexOf).toList());
        visualizer.visualize(algorithmInputA.nodes(), analysisResultA2.bestSolution().orderedNodes());


        AnalysisResult analysisResultA3 = analyser.analyse(DATA_A, greedyCycleAlgorithm);
        showResult(greedyCycleAlgorithm.getClass().getSimpleName(), analysisResultA3);
        IO.println(analysisResultA3.bestSolution().orderedNodes().stream().map(algorithmInputA.nodes()::indexOf).toList());
        visualizer.visualize(algorithmInputA.nodes(), analysisResultA3.bestSolution().orderedNodes());


        AnalysisResult analysisResultA4 = analyser.analyse(DATA_A, randomSearchAlgorithm);
        showResult(randomSearchAlgorithm.getClass().getSimpleName(), analysisResultA4);
        IO.println(analysisResultA4.bestSolution().orderedNodes().stream().map(algorithmInputA.nodes()::indexOf).toList());
        visualizer.visualize(algorithmInputA.nodes(), analysisResultA4.bestSolution().orderedNodes());
//
//        AnalysisResult analysisResultB1 = analyser.analyse(DATA_B, nearestNeighborLastNode);
//        showResult(nearestNeighborLastNode.getClass().getSimpleName(), analysisResultB1);
//        IO.println(analysisResultB1.bestSolution().orderedNodes().stream().map(algorithmInputB.nodes()::indexOf).toList());
//        visualizer.visualize(algorithmInputB.nodes(), analysisResultB1.bestSolution().orderedNodes());
//
//        AnalysisResult analysisResultB2 = analyser.analyse(DATA_B, nearestNeighborAnyNode);
//        showResult(nearestNeighborAnyNode.getClass().getSimpleName(), analysisResultB2);
//        IO.println(analysisResultB2.bestSolution().orderedNodes().stream().map(algorithmInputB.nodes()::indexOf).toList());
//        visualizer.visualize(algorithmInputB.nodes(), analysisResultB2.bestSolution().orderedNodes());
//
//
//        AnalysisResult analysisResultB3 = analyser.analyse(DATA_B, greedyCycleAlgorithm);
//        showResult(greedyCycleAlgorithm.getClass().getSimpleName(), analysisResultB3);
//        IO.println(analysisResultB3.bestSolution().orderedNodes().stream().map(algorithmInputB.nodes()::indexOf).toList());
//        visualizer.visualize(algorithmInputB.nodes(), analysisResultB3.bestSolution().orderedNodes());
//
//
//        AnalysisResult analysisResultB4 = analyser.analyse(DATA_B, randomSearchAlgorithm);
//        showResult(randomSearchAlgorithm.getClass().getSimpleName(), analysisResultB4);
//        IO.println(analysisResultB4.bestSolution().orderedNodes().stream().map(algorithmInputB.nodes()::indexOf).toList());
//        visualizer.visualize(algorithmInputB.nodes(), analysisResultB4.bestSolution().orderedNodes());

    }

    private void showResult(String title, AnalysisResult analysisResult) {
        IO.println("\n\n\n" + FORTY_EQUALS);
        IO.println(TEN_EQUALS + " ".repeat((30 - title.length()) / 2) + title + " ".repeat((30 - title.length()) / 2) + TEN_EQUALS);
        IO.println(FORTY_EQUALS);
        IO.println("Best value: " + analysisResult.bestValue() + "\t\t" + "time: " + analysisResult.bestValueTime() + "s");
        IO.println("Average value: " + analysisResult.averageValue() + "\t\t" + "time: " + analysisResult.averageValueTime() + "s");
        IO.println("Worst value: " + analysisResult.worstValue() + "\t\t" + "time: " + analysisResult.worstValueTime() + "s");
        IO.println(FORTY_EQUALS);
    }
}