package pl.mlsk.algorithm.impl.lab5;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mlsk.algorithm.Algorithm;
import pl.mlsk.algorithm.impl.lab1.RandomSearchAlgorithm;
import pl.mlsk.algorithm.impl.lab5.dto.Edge;
import pl.mlsk.algorithm.impl.lab5.dto.Move;
import pl.mlsk.algorithm.impl.lab5.iterator.MoveIterator;
import pl.mlsk.algorithm.impl.lab5.validator.MoveValidationResult;
import pl.mlsk.algorithm.impl.lab5.validator.MoveValidator;
import pl.mlsk.common.*;

import java.util.*;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class MoveEvaluationAlgorithm implements Algorithm {

    private final RandomSearchAlgorithm randomSearchAlgorithm;
    private final MoveValidator moveValidator;

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
            Solution newSolution = applyMoves(pq, solution);
            if (newSolution.equals(solution)) {
                return newSolution;
            }
            solution = newSolution;
        }
    }

    private Solution applyMoves(PriorityQueue<Move> pq, Solution solution) {
        Set<Move> futureMoves = new HashSet<>();
        Set<Move> previousFutureMoves = Set.of();
        while (true) {
            while (!pq.isEmpty()) {
                Move move = pq.poll();
                solution = updateSolution(solution, move, futureMoves);
            }

            if (futureMoves.isEmpty() || previousFutureMoves.equals(futureMoves)) {
                return solution;
            }
            previousFutureMoves = new HashSet<>(futureMoves);
            pq.addAll(futureMoves);
            futureMoves.clear();
        }
    }

    private Solution updateSolution(Solution solution, Move move, Set<Move> futureMoves) {
        MoveValidationResult validation = moveValidator.validate(solution, move);
        if (validation.isValid()) {
            if (validation.isEdgesOutOfOrder()) {
                futureMoves.add(move);
            } else {
                solution = move.isEdgeSwap() ? applyEdgeSwap(solution, move) : applyInterMove(solution, move);
            }
        }
        return solution;
    }

    protected Solution applyEdgeSwap(Solution solution, Move move) {
        Map<Node, Node> edgeMap = mapFromSolution(solution);
        for (Edge toDelete : move.toDelete()) {
            edgeMap.remove(toDelete.node1());
        }
        reverseNodes(move, edgeMap);
        for (Edge toAdd : move.toAdd()) {
            edgeMap.put(toAdd.node1(), toAdd.node2());
        }
        return buildFromMap(edgeMap, solution);
    }

    private void reverseNodes(Move move, Map<Node, Node> edgeMap) {
        List<Edge> toAdd = move.toAdd();
        Node startReverse = toAdd.getLast().node1();
        Node endReverse = toAdd.getFirst().node2();
        Node current = startReverse;
        Deque<Node> stack = new LinkedList<>();
        while (current != endReverse) {
            stack.push(current);
            current = edgeMap.get(current);
        }
        while (!stack.isEmpty()) {
            Node next = stack.pop();
            edgeMap.put(current, next);
            current = next;
        }
    }

    protected Solution applyInterMove(Solution solution, Move move) {
        Map<Node, Node> edgeMap = mapFromSolution(solution);
        for (Edge toDelete : move.toDelete()) {
            edgeMap.remove(toDelete.node1());
        }
        for (Edge toAdd : move.toAdd()) {
            edgeMap.put(toAdd.node1(), toAdd.node2());
        }
        return buildFromMap(edgeMap, solution);
    }

    protected Solution buildFromMap(Map<Node, Node> map, Solution originalSolution) {
        List<Node> result = new ArrayList<>();
        Node start = map.values()
                .stream()
                .filter(node -> originalSolution.orderedNodes().contains(node))
                .min(Comparator.comparingInt(node -> originalSolution.orderedNodes().indexOf(node)))
                .orElseThrow(RuntimeException::new);
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
