package pl.mlsk.algorithm.impl.lab5;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mlsk.algorithm.Algorithm;
import pl.mlsk.algorithm.impl.lab1.RandomSearchAlgorithm;
import pl.mlsk.common.*;

import java.util.*;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class MoveEvaluationAlgorithm implements Algorithm {

    private final RandomSearchAlgorithm randomSearchAlgorithm;

    @Override
    public Solution solve(AlgorithmInput input, int pos) {
        Solution initialSolution = randomSearchAlgorithm.solve(input, pos);
        return moveEvaluation(initialSolution, input.distanceMatrix(), input.nearestNodeMap());
    }

    private Solution moveEvaluation(Solution solution, DistanceMatrix distanceMatrix, NearestNodeMap nearestNodeMap) {
        PriorityQueue<Move> pq = new PriorityQueue<>(Comparator.comparingDouble(Move::delta));
        while (true) {
            boolean changed = findMoves(pq, solution, distanceMatrix, nearestNodeMap);
            if (!changed) {
                return solution;
            }
            applyMoves(pq, solution);
        }
    }

    private void applyMoves(PriorityQueue<Move> pq, Solution solution) {
        PriorityQueue<Move> pqFutureMoves = new PriorityQueue<>(Comparator.comparingDouble(Move::delta));
        while (!pq.isEmpty()) {
            Move move = pq.poll();
            boolean isEdgeRemoved = false;
            boolean isEdgeOrderChanged = false;

            for (Edge toDelete : move.toDelete()) {
                int indexLeft = solution.orderedNodes().indexOf(toDelete.node1());
                int indexRight = solution.orderedNodes().indexOf(toDelete.node2());

                if (indexLeft == -1 || indexRight == -1 || Math.abs(indexLeft - indexRight) > 1) {
                    isEdgeRemoved = true;
                }

                if (indexLeft + 1 == indexRight) {
                    isEdgeOrderChanged = true;
                }
            }
            if (!isEdgeRemoved) {
                if (isEdgeOrderChanged) {
                    pqFutureMoves.add(move);
                } else {
                    solution = move.isEdgeSwap() ? applyEdgeSwap(solution, move) : applyInterMove(solution, move);
                }
            }
        }
    }

    private Solution applyEdgeSwap(Solution solution, Move move) {
        Map<Node, Node> edgeMap = mapFromSolution(solution);
        Set<Node> removedNodes = new HashSet<>();
        for (Edge toAdd : move.toAdd()) {
            removedNodes.add(toAdd.node1());
            edgeMap.put(toAdd.node1(), toAdd.node2());
        }
        for (Edge toDelete : move.toDelete()) {
            if (removedNodes.contains(toDelete.node1())) {
                removedNodes.remove(toDelete.node1());
                Node edgeTo = removedNodes
                        .stream()
                        .findFirst()
                        .orElseThrow(RuntimeException::new);
                edgeMap.put(toDelete.node2(), edgeTo);
            }
        }
        return buildFromMap(edgeMap, solution);
    }

    private Solution applyInterMove(Solution solution, Move move) {
        Map<Node, Node> edgeMap = mapFromSolution(solution);
        for (Edge toDelete : move.toDelete()) {
            edgeMap.remove(toDelete.node1());
        }
        for (Edge toAdd : move.toAdd()) {
            edgeMap.put(toAdd.node1(), toAdd.node2());
        }
        return buildFromMap(edgeMap, solution);
    }

    private Solution buildFromMap(Map<Node, Node> map, Solution originalSolution) {
        List<Node> result = new ArrayList<>();
        Node start = map.values()
                .stream()
                .filter(node -> originalSolution.orderedNodes().contains(node))
                .sorted(Comparator.comparingInt(node -> originalSolution.orderedNodes().indexOf(node)))
                .findFirst().orElseThrow(RuntimeException::new);
        result.add(start);
        Node current = start;
        while (true) {
            current = map.get(current);
            if (current == start)
                return new Solution(result);
            result.add(current);
        }
    }

    private Map<Node, Node> mapFromSolution(Solution solution) {
        Map<Node, Node> nodeMap = new HashMap<>();
        for (int i = 1; i < solution.orderedNodes().size(); i++) {
            Node nodeLeft = solution.orderedNodes().get(i - 1);
            Node nodeRight = solution.orderedNodes().get(i);
            nodeMap.put(nodeLeft, nodeRight);
        }
        nodeMap.put(solution.orderedNodes().getLast(), solution.orderedNodes().getFirst());
        return nodeMap;
    }

    private boolean findMoves(PriorityQueue<Move> pq, Solution solution, DistanceMatrix distanceMatrix, NearestNodeMap nearestNodeMap) {
        Iterator<Move> iterator = new MoveIterator(solution, distanceMatrix, nearestNodeMap);
        boolean changed = false;
        while (iterator.hasNext()) {
            Move nextMove = iterator.next();
            if (nonNull(nextMove)) {
                pq.add(nextMove);
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public String algorithmName() {
        return getClass().getSimpleName();
    }
}
