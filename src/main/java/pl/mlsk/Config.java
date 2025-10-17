package pl.mlsk;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.mlsk.algorithm.impl.lab2.Greedy2RegretAlgorithm;
import pl.mlsk.algorithm.impl.lab2.Greedy2RegretWeightedSum;
import pl.mlsk.algorithm.impl.lab2.evaluator.impl.GreedyCycleNodeEvaluator;
import pl.mlsk.algorithm.impl.lab2.evaluator.impl.NearestNeighborNodeEvaluator;

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
}
