package pl.mlsk.algorithm.impl.lab3;

import pl.mlsk.common.DistanceMatrix;
import pl.mlsk.common.Node;
import pl.mlsk.common.Solution;

import java.util.Iterator;
import java.util.List;

public abstract class LocalSearchIterator implements Iterator<Solution> {

    protected Solution solution;
    protected List<Node> nodesOutOfSolution;
    protected DistanceMatrix distanceMatrix;

    protected LocalSearchIterator(Solution solution, DistanceMatrix distanceMatrix, List<Node> nodesOutOfSolution) {
        this.solution = solution;
        this.distanceMatrix = distanceMatrix;
        this.nodesOutOfSolution = nodesOutOfSolution;
    }

    protected abstract double getDelta();
    public abstract double getBestDelta();
}
