package pl.mlsk.algorithm.impl.lab3;

import pl.mlsk.common.DistanceMatrix;
import pl.mlsk.common.Node;
import pl.mlsk.common.Solution;
import pl.mlsk.utils.RandomUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class LocalSearchIterator implements Iterator<Solution> {

    protected Solution solution;
    protected List<Node> nodesOutOfSolution;
    protected DistanceMatrix distanceMatrix;

    protected LocalSearchIterator(Solution solution, DistanceMatrix distanceMatrix, List<Node> nodesOutOfSolution) {
        this.distanceMatrix = distanceMatrix;
        this.nodesOutOfSolution = new ArrayList<>(nodesOutOfSolution);
        Collections.shuffle(this.nodesOutOfSolution);
        this.solution = solution;
        randomizeSolution(solution.orderedNodes());
    }

    protected abstract double getDelta();

    private void randomizeSolution(List<Node> solutionNodes) {
        int randomIndex = RandomUtils.nextInt(solutionNodes.size());
        if (randomIndex == 0) return;

        List<Node> start = new ArrayList<>(solutionNodes.subList(randomIndex, solutionNodes.size()));
        List<Node> end = solutionNodes.subList(0, randomIndex);
        start.addAll(end);
        this.solution = new Solution(start);
    }
}
