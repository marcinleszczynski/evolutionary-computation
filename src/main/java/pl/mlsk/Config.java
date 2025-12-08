package pl.mlsk;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.mlsk.algorithm.impl.lab1.NearestNeighborAnyNode;
import pl.mlsk.algorithm.impl.lab1.RandomSearchAlgorithm;
import pl.mlsk.algorithm.impl.lab2.Greedy2RegretAlgorithm;
import pl.mlsk.algorithm.impl.lab2.Greedy2RegretWeightedSum;
import pl.mlsk.algorithm.impl.lab2.evaluator.impl.GreedyCycleNodeEvaluator;
import pl.mlsk.algorithm.impl.lab2.evaluator.impl.NearestNeighborNodeEvaluator;
import pl.mlsk.algorithm.impl.lab3.impl.GreedyLocalSearch;
import pl.mlsk.algorithm.impl.lab3.impl.SteepestLocalSearch;
import pl.mlsk.algorithm.impl.lab3.impl.iterator.grouped.LocalEdgeIterator;
import pl.mlsk.algorithm.impl.lab3.impl.iterator.grouped.LocalNodeIterator;
import pl.mlsk.algorithm.impl.lab6.IteratedLocalSearch;
import pl.mlsk.algorithm.impl.lab8.ConvexityTester;
import pl.mlsk.algorithm.impl.lab8.comparator.SimilarityComparator;
import pl.mlsk.algorithm.impl.lab8.comparator.impl.AverageSimilarityComparator;
import pl.mlsk.algorithm.impl.lab8.comparator.impl.BestSimilarityComparator;
import pl.mlsk.algorithm.impl.lab8.comparator.impl.GoodSimilarityComparator;
import pl.mlsk.algorithm.impl.lab8.plot.ConvexityPlotService;
import pl.mlsk.algorithm.impl.lab8.similarity.impl.EdgeSimilarityMeasure;
import pl.mlsk.algorithm.impl.lab8.similarity.impl.NodeSimilarityMeasure;

@Configuration
@RequiredArgsConstructor
public class Config {

    @Bean(name = "greedy2RegretNearestNeighbor")
    public Greedy2RegretAlgorithm greedy2RegretNearestNeighbor(NearestNeighborNodeEvaluator evaluator) {
        return new Greedy2RegretAlgorithm(evaluator);
    }

    @Bean(name = "greedy2RegretGreedyCycle")
    public Greedy2RegretAlgorithm greedy2RegretGreedyCycle(GreedyCycleNodeEvaluator evaluator) {
        return new Greedy2RegretAlgorithm(evaluator);
    }

    @Bean(name = "greedy2RegretWeightedSumNearestNeighbor")
    public Greedy2RegretWeightedSum greedy2RegretWeightedSumNearestNeighbor(NearestNeighborNodeEvaluator evaluator) {
        return new Greedy2RegretWeightedSum(evaluator);
    }

    @Bean(name = "greedy2RegretWeightedSumGreedyCycle")
    public Greedy2RegretWeightedSum greedy2RegretWeightedSumGreedyCycle(GreedyCycleNodeEvaluator evaluator) {
        return new Greedy2RegretWeightedSum(evaluator);
    }

    @Bean(name = "localGreedyNodeBestStart")
    public GreedyLocalSearch localGreedyNodeBestStart(@Qualifier("greedy2RegretWeightedSumGreedyCycle") Greedy2RegretWeightedSum greedyAlgorithm) {
        return new GreedyLocalSearch(greedyAlgorithm, LocalNodeIterator::new);
    }

    @Bean(name = "localGreedyEdgeBestStart")
    public GreedyLocalSearch localGreedyEdgeBestStart(@Qualifier("greedy2RegretWeightedSumGreedyCycle") Greedy2RegretWeightedSum greedyAlgorithm) {
        return new GreedyLocalSearch(greedyAlgorithm, LocalEdgeIterator::new);
    }

    @Bean(name = "localSteepestNodeBestStart")
    public SteepestLocalSearch localSteepestNodeBestStart(@Qualifier("greedy2RegretWeightedSumGreedyCycle") Greedy2RegretWeightedSum greedyAlgorithm) {
        return new SteepestLocalSearch(greedyAlgorithm, LocalNodeIterator::new);
    }

    @Bean(name = "localSteepestEdgeBestStart")
    public SteepestLocalSearch localSteepestEdgeBestStart(@Qualifier("greedy2RegretWeightedSumGreedyCycle") Greedy2RegretWeightedSum greedyAlgorithm) {
        return new SteepestLocalSearch(greedyAlgorithm, LocalEdgeIterator::new);
    }

    @Bean(name = "localGreedyNodeRandomStart")
    public GreedyLocalSearch localGreedyNodeRandomStart(RandomSearchAlgorithm greedyAlgorithm) {
        return new GreedyLocalSearch(greedyAlgorithm, LocalNodeIterator::new);
    }

    @Bean(name = "localGreedyEdgeRandomStart")
    public GreedyLocalSearch localGreedyEdgeRandomStart(RandomSearchAlgorithm greedyAlgorithm) {
        return new GreedyLocalSearch(greedyAlgorithm, LocalEdgeIterator::new);
    }

    @Bean(name = "localSteepestNodeRandomStart")
    public SteepestLocalSearch localSteepestNodeRandomStart(RandomSearchAlgorithm greedyAlgorithm) {
        return new SteepestLocalSearch(greedyAlgorithm, LocalNodeIterator::new);
    }

    @Bean(name = "localSteepestEdgeRandomStart")
    public SteepestLocalSearch localSteepestEdgeRandomStart(RandomSearchAlgorithm greedyAlgorithm) {
        return new SteepestLocalSearch(greedyAlgorithm, LocalEdgeIterator::new);
    }

    // special case (lab 3) - Nearest neighbor is better than greedy cycle - weighted sum for dataset B
    @Bean(name = "localGreedyNodeNN")
    public GreedyLocalSearch localGreedyNodeNN(NearestNeighborAnyNode greedyAlgorithm) {
        return new GreedyLocalSearch(greedyAlgorithm, LocalNodeIterator::new);
    }

    @Bean(name = "localGreedyEdgeNN")
    public GreedyLocalSearch localGreedyEdgeNN(NearestNeighborAnyNode greedyAlgorithm) {
        return new GreedyLocalSearch(greedyAlgorithm, LocalEdgeIterator::new);
    }

    @Bean(name = "localSteepestNodeNN")
    public SteepestLocalSearch localSteepestNodeNN(NearestNeighborAnyNode greedyAlgorithm) {
        return new SteepestLocalSearch(greedyAlgorithm, LocalNodeIterator::new);
    }

    @Bean(name = "localSteepestEdgeNN")
    public SteepestLocalSearch localSteepestEdgeNN(NearestNeighborAnyNode greedyAlgorithm) {
        return new SteepestLocalSearch(greedyAlgorithm, LocalEdgeIterator::new);
    }

    @Bean(name = "averageSimilarityNode")
    public AverageSimilarityComparator averageSimilarityNode(NodeSimilarityMeasure similarityMeasure) {
        return new AverageSimilarityComparator(similarityMeasure);
    }

    @Bean(name = "averageSimilarityEdge")
    public AverageSimilarityComparator averageSimilarityEdge(EdgeSimilarityMeasure similarityMeasure) {
        return new AverageSimilarityComparator(similarityMeasure);
    }

    @Bean(name = "bestSimilarityNode")
    public BestSimilarityComparator bestSimilarityNode(NodeSimilarityMeasure similarityMeasure) {
        return new BestSimilarityComparator(similarityMeasure);
    }

    @Bean(name = "bestSimilarityEdge")
    public BestSimilarityComparator bestSimilarityEdge(EdgeSimilarityMeasure similarityMeasure) {
        return new BestSimilarityComparator(similarityMeasure);
    }

    @Bean(name = "goodSimilarityNode")
    public GoodSimilarityComparator goodSimilarityNode(NodeSimilarityMeasure sm, IteratedLocalSearch ils) {
        return new GoodSimilarityComparator(sm, ils);
    }

    @Bean(name = "goodSimilarityEdge")
    public GoodSimilarityComparator goodSimilarityEdge(EdgeSimilarityMeasure sm, IteratedLocalSearch ils) {
        return new GoodSimilarityComparator(sm, ils);
    }

    @Bean(name = "ctASN")
    public ConvexityTester ctASN(
            @Qualifier("averageSimilarityNode") SimilarityComparator sc,
            @Qualifier("localGreedyEdgeRandomStart") GreedyLocalSearch gls,
            ConvexityPlotService cps
    ) {
        return new ConvexityTester(sc, gls, cps);
    }

    @Bean(name = "ctASE")
    public ConvexityTester ctASE(
            @Qualifier("averageSimilarityEdge") SimilarityComparator sc,
            @Qualifier("localGreedyEdgeRandomStart") GreedyLocalSearch gls,
            ConvexityPlotService cps
    ) {
        return new ConvexityTester(sc, gls, cps);
    }

    @Bean(name = "ctBSN")
    public ConvexityTester ctBSN(
            @Qualifier("bestSimilarityNode") SimilarityComparator sc,
            @Qualifier("localGreedyEdgeRandomStart") GreedyLocalSearch gls,
            ConvexityPlotService cps
    ) {
        return new ConvexityTester(sc, gls, cps);
    }

    @Bean(name = "ctBSE")
    public ConvexityTester ctBSE(
            @Qualifier("bestSimilarityEdge") SimilarityComparator sc,
            @Qualifier("localGreedyEdgeRandomStart") GreedyLocalSearch gls,
            ConvexityPlotService cps
    ) {
        return new ConvexityTester(sc, gls, cps);
    }

    @Bean(name = "ctGSN")
    public ConvexityTester ctGSN(
            @Qualifier("goodSimilarityNode") SimilarityComparator sc,
            @Qualifier("localGreedyEdgeRandomStart") GreedyLocalSearch gls,
            ConvexityPlotService cps
    ) {
        return new ConvexityTester(sc, gls, cps);
    }

    @Bean(name = "ctGSE")
    public ConvexityTester ctGSE(
            @Qualifier("goodSimilarityEdge") SimilarityComparator sc,
            @Qualifier("localGreedyEdgeRandomStart") GreedyLocalSearch gls,
            ConvexityPlotService cps
    ) {
        return new ConvexityTester(sc, gls, cps);
    }
}
