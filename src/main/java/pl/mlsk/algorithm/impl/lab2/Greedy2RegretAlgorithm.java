package pl.mlsk.algorithm.impl.lab2;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.mlsk.algorithm.GreedyRegretAlgorithm;
import pl.mlsk.algorithm.impl.lab2.evaluator.EvaluationResult;
import pl.mlsk.algorithm.impl.lab2.evaluator.NodeEvaluator;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class Greedy2RegretAlgorithm extends GreedyRegretAlgorithm {

    private final NodeEvaluator nodeEvaluator;

    @Override
    protected double calculateRegret(List<EvaluationResult> evaluationResultsForNode) {

        if (evaluationResultsForNode.size() <= 1)
            return 0.0;

        return evaluationResultsForNode.get(1).distance() - evaluationResultsForNode.get(0).distance();
    }


    @Override
    public String algorithmName() {
        return super.algorithmName() + " - " + nodeEvaluator.getClass().getSimpleName();
    }
}