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
import pl.mlsk.algorithm.impl.lab3.impl.iterator.TwoEdgesIterator;
import pl.mlsk.algorithm.impl.lab3.impl.iterator.TwoNodesIterator;

import java.util.Random;

@Configuration
@RequiredArgsConstructor
public class Config {

    @Bean
    public Random random() {
        return new Random(42);
    }

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
        return new GreedyLocalSearch(greedyAlgorithm, TwoNodesIterator::new);
    }

    @Bean(name = "localGreedyEdgeBestStart")
    public GreedyLocalSearch localGreedyEdgeBestStart(@Qualifier("greedy2RegretWeightedSumGreedyCycle") Greedy2RegretWeightedSum greedyAlgorithm) {
        return new GreedyLocalSearch(greedyAlgorithm, TwoEdgesIterator::new);
    }

    @Bean(name = "localSteepestNodeBestStart")
    public SteepestLocalSearch localSteepestNodeBestStart(@Qualifier("greedy2RegretWeightedSumGreedyCycle") Greedy2RegretWeightedSum greedyAlgorithm) {
        return new SteepestLocalSearch(greedyAlgorithm, TwoNodesIterator::new);
    }

    @Bean(name = "localSteepestEdgeBestStart")
    public SteepestLocalSearch localSteepestEdgeBestStart(@Qualifier("greedy2RegretWeightedSumGreedyCycle") Greedy2RegretWeightedSum greedyAlgorithm) {
        return new SteepestLocalSearch(greedyAlgorithm, TwoEdgesIterator::new);
    }

    @Bean(name = "localGreedyNodeRandomStart")
    public GreedyLocalSearch localGreedyNodeRandomStart(RandomSearchAlgorithm greedyAlgorithm) {
        return new GreedyLocalSearch(greedyAlgorithm, TwoNodesIterator::new);
    }

    @Bean(name = "localGreedyEdgeRandomStart")
    public GreedyLocalSearch localGreedyEdgeRandomStart(RandomSearchAlgorithm greedyAlgorithm) {
        return new GreedyLocalSearch(greedyAlgorithm, TwoEdgesIterator::new);
    }

    @Bean(name = "localSteepestNodeRandomStart")
    public SteepestLocalSearch localSteepestNodeRandomStart(RandomSearchAlgorithm greedyAlgorithm) {
        return new SteepestLocalSearch(greedyAlgorithm, TwoNodesIterator::new);
    }

    @Bean(name = "localSteepestEdgeRandomStart")
    public SteepestLocalSearch localSteepestEdgeRandomStart(RandomSearchAlgorithm greedyAlgorithm) {
        return new SteepestLocalSearch(greedyAlgorithm, TwoEdgesIterator::new);
    }

    // special case (lab 3) - Nearest neighbor is better than greedy cycle - weighted sum for dataset B
    @Bean(name = "localGreedyNodeNN")
    public GreedyLocalSearch localGreedyNodeNN(NearestNeighborAnyNode greedyAlgorithm) {
        return new GreedyLocalSearch(greedyAlgorithm, TwoNodesIterator::new);
    }

    @Bean(name = "localGreedyEdgeNN")
    public GreedyLocalSearch localGreedyEdgeNN(NearestNeighborAnyNode greedyAlgorithm) {
        return new GreedyLocalSearch(greedyAlgorithm, TwoEdgesIterator::new);
    }

    @Bean(name = "localSteepestNodeNN")
    public SteepestLocalSearch localSteepestNodeNN(NearestNeighborAnyNode greedyAlgorithm) {
        return new SteepestLocalSearch(greedyAlgorithm, TwoNodesIterator::new);
    }

    @Bean(name = "localSteepestEdgeNN")
    public SteepestLocalSearch localSteepestEdgeNN(NearestNeighborAnyNode greedyAlgorithm) {
        return new SteepestLocalSearch(greedyAlgorithm, TwoEdgesIterator::new);
    }
}
