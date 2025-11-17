package pl.mlsk;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import pl.mlsk.algorithm.CandidateMovesAlgorithm;
import pl.mlsk.algorithm.impl.lab1.GreedyCycleAlgorithm;
import pl.mlsk.algorithm.impl.lab1.NearestNeighborAnyNode;
import pl.mlsk.algorithm.impl.lab1.NearestNeighborLastNode;
import pl.mlsk.algorithm.impl.lab1.RandomSearchAlgorithm;
import pl.mlsk.algorithm.impl.lab2.Greedy2RegretAlgorithm;
import pl.mlsk.algorithm.impl.lab2.Greedy2RegretWeightedSum;
import pl.mlsk.algorithm.impl.lab3.impl.GreedyLocalSearch;
import pl.mlsk.algorithm.impl.lab3.impl.SteepestLocalSearch;
import pl.mlsk.algorithm.impl.lab5.MoveEvaluationAlgorithm;
import pl.mlsk.analyser.Analyser;

@Service
@ComponentScan("pl.mlsk")
public class Main {

    @Setter(onMethod_ = @Autowired)
    private Analyser analyser;

    @Setter(onMethod_ = @Autowired)
    private RandomSearchAlgorithm randomSearchAlgorithm;

    @Setter(onMethod_ = @Autowired)
    private NearestNeighborAnyNode nearestNeighborAnyNode;

    @Setter(onMethod_ = @Autowired)
    private NearestNeighborLastNode nearestNeighborLastNode;

    @Setter(onMethod_ = @Autowired)
    private GreedyCycleAlgorithm greedyCycleAlgorithm;

    @Setter(onMethod_ = {@Autowired, @Qualifier("greedy2RegretNearestNeighbor")})
    private Greedy2RegretAlgorithm greedy2RegretNN;

    @Setter(onMethod_ = {@Autowired, @Qualifier("greedy2RegretGreedyCycle")})
    private Greedy2RegretAlgorithm greedy2RegretGC;

    @Setter(onMethod_ = {@Autowired, @Qualifier("greedy2RegretWeightedSumNearestNeighbor")})
    private Greedy2RegretWeightedSum greedy2RegretWSNN;

    @Setter(onMethod_ = {@Autowired, @Qualifier("greedy2RegretWeightedSumGreedyCycle")})
    private Greedy2RegretWeightedSum greedy2RegretWSGC;

    @Setter(onMethod_ = {@Autowired, @Qualifier("localGreedyNodeBestStart")})
    private GreedyLocalSearch localGreedyNodeBestStart;

    @Setter(onMethod_ = {@Autowired, @Qualifier("localGreedyEdgeBestStart")})
    private GreedyLocalSearch localGreedyEdgeBestStart;

    @Setter(onMethod_ = {@Autowired, @Qualifier("localSteepestNodeBestStart")})
    private SteepestLocalSearch localSteepestNodeBestStart;

    @Setter(onMethod_ = {@Autowired, @Qualifier("localSteepestEdgeBestStart")})
    private SteepestLocalSearch localSteepestEdgeBestStart;

    @Setter(onMethod_ = {@Autowired, @Qualifier("localGreedyNodeRandomStart")})
    private GreedyLocalSearch localGreedyNodeRandomStart;

    @Setter(onMethod_ = {@Autowired, @Qualifier("localGreedyEdgeRandomStart")})
    private GreedyLocalSearch localGreedyEdgeRandomStart;

    @Setter(onMethod_ = {@Autowired, @Qualifier("localSteepestNodeRandomStart")})
    private SteepestLocalSearch localSteepestNodeRandomStart;

    @Setter(onMethod_ = {@Autowired, @Qualifier("localSteepestEdgeRandomStart")})
    private SteepestLocalSearch localSteepestEdgeRandomStart;

    @Setter(onMethod_ = {@Autowired, @Qualifier("localGreedyNodeNN")})
    private GreedyLocalSearch localGreedyNodeNN;

    @Setter(onMethod_ = {@Autowired, @Qualifier("localGreedyEdgeNN")})
    private GreedyLocalSearch localGreedyEdgeNN;

    @Setter(onMethod_ = {@Autowired, @Qualifier("localSteepestNodeNN")})
    private SteepestLocalSearch localSteepestNodeNN;

    @Setter(onMethod_ = {@Autowired, @Qualifier("localSteepestEdgeNN")})
    private SteepestLocalSearch localSteepestEdgeNN;

    @Setter(onMethod_ = @Autowired)
    private CandidateMovesAlgorithm candidateMovesAlgorithm;

    @Setter(onMethod_ = @Autowired)
    private MoveEvaluationAlgorithm moveEvaluationAlgorithm;


    private static final String DATA_A = "/TSPA.csv";
    private static final String DATA_B = "/TSPB.csv";

    public static void main(String[] args) {

        System.setProperty("org.graphstream.ui", "swing");

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        Main main = context.getBean(Main.class);
        main.run();
    }

    public void run() {

//        analyser.analyse(DATA_A, nearestNeighborLastNode);
//        analyser.analyse(DATA_A, greedyCycleAlgorithm);
//        analyser.analyse(DATA_A, nearestNeighborAnyNode);
//        analyser.analyse(DATA_A, randomSearchAlgorithm);

//        analyser.analyse(DATA_B, nearestNeighborLastNode);
//        analyser.analyse(DATA_B, greedyCycleAlgorithm);
//        analyser.analyse(DATA_B, nearestNeighborAnyNode);
//        analyser.analyse(DATA_B, randomSearchAlgorithm);

//        analyser.analyse(DATA_A, greedy2RegretGC);
//        analyser.analyse(DATA_A, greedy2RegretNN);
//        analyser.analyse(DATA_A, greedy2RegretWSGC);
//        analyser.analyse(DATA_A, greedy2RegretWSNN);

//        analyser.analyse(DATA_B, greedy2RegretGC);
//        analyser.analyse(DATA_B, greedy2RegretNN);
//        analyser.analyse(DATA_B, greedy2RegretWSGC);
//        analyser.analyse(DATA_B, greedy2RegretWSNN);

//        analyser.analyse(DATA_A, localGreedyNodeBestStart);
//        analyser.analyse(DATA_A, localGreedyEdgeBestStart);
//        analyser.analyse(DATA_A, localSteepestNodeBestStart);
//        analyser.analyse(DATA_A, localSteepestEdgeBestStart);
//
//        analyser.analyse(DATA_A, localGreedyNodeRandomStart);
//        analyser.analyse(DATA_A, localGreedyEdgeRandomStart);
//        analyser.analyse(DATA_A, localSteepestNodeRandomStart);
//        analyser.analyse(DATA_A, localSteepestEdgeRandomStart);
//
//        analyser.analyse(DATA_B, localGreedyNodeNN);
//        analyser.analyse(DATA_B, localGreedyEdgeNN);
//        analyser.analyse(DATA_B, localSteepestNodeNN);
//        analyser.analyse(DATA_B, localSteepestEdgeNN);
//
//        analyser.analyse(DATA_B, localGreedyNodeRandomStart);
//        analyser.analyse(DATA_B, localGreedyEdgeRandomStart);
//        analyser.analyse(DATA_B, localSteepestNodeRandomStart);
//        analyser.analyse(DATA_B, localSteepestEdgeRandomStart);

        analyser.analyse(DATA_A, candidateMovesAlgorithm);
        analyser.analyse(DATA_B, candidateMovesAlgorithm);

//        analyser.analyse(DATA_A, localSteepestEdgeRandomStart);
//        analyser.analyse(DATA_B, localSteepestEdgeRandomStart);

        analyser.analyse(DATA_A, moveEvaluationAlgorithm);
        analyser.analyse(DATA_B, moveEvaluationAlgorithm);
    }
}