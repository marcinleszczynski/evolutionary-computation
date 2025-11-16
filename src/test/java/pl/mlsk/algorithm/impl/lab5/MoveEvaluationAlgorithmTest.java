package pl.mlsk.algorithm.impl.lab5;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.mlsk.algorithm.impl.lab1.RandomSearchAlgorithm;
import pl.mlsk.algorithm.impl.lab5.dto.Edge;
import pl.mlsk.algorithm.impl.lab5.dto.Move;
import pl.mlsk.algorithm.impl.lab5.validator.MoveValidator;
import pl.mlsk.common.Node;
import pl.mlsk.common.Solution;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class MoveEvaluationAlgorithmTest {

    @Mock
    private RandomSearchAlgorithm randomSearchAlgorithm;

    @Mock
    private MoveValidator moveValidator;

    @InjectMocks
    private MoveEvaluationAlgorithm moveEvaluationAlgorithm;

    @Test
    void testBuildFromMap() {

        Node node1 = new Node(1, 1, 1);
        Node node2 = new Node(2, 2, 2);
        Node node3 = new Node(3, 3, 3);
        Node node4 = new Node(4, 4, 4);
        Node node5 = new Node(5, 5, 5);

        Map<Node, Node> map = Map.ofEntries(
                Map.entry(node1, node2),
                Map.entry(node2, node3),
                Map.entry(node3, node4),
                Map.entry(node4, node5),
                Map.entry(node5, node1)
        );

        // second argument (solution) is just for determining, where the list will start - we want to start from node1
        Solution result = moveEvaluationAlgorithm.buildFromMap(map, new Solution(List.of(node1)));
        assertEquals(List.of(node1, node2, node3, node4, node5), result.orderedNodes());
    }

    @Test
    void testApplyInterMove() {
        Node node1 = new Node(1, 1, 1);
        Node node2 = new Node(2, 2, 2);
        Node node3 = new Node(3, 3, 3);
        Node node4 = new Node(4, 4, 4);
        Node node5 = new Node(5, 5, 5);
        Node node6 = new Node(6, 6, 6);
        Solution solution = new Solution(List.of(node1, node2, node3, node4, node5));

        List<Edge> toAdd = List.of(new Edge(node3, node6), new Edge(node6, node5));
        List<Edge> toDelete = List.of(new Edge(node3, node4), new Edge(node4, node5));

        // third argument (delta) is for priority queue, not important for this test
        Move move = new Move(toAdd, toDelete, 1, false);
        Solution newSolution = moveEvaluationAlgorithm.applyInterMove(solution, move);
        assertEquals(List.of(node1, node2, node3, node6, node5), newSolution.orderedNodes());
    }

    @Test
    void testApplyEdgeSwap() {
        Node node1 = new Node(1, 1, 1);
        Node node2 = new Node(2, 2, 2);
        Node node3 = new Node(3, 3, 3);
        Node node4 = new Node(4, 4, 4);
        Node node5 = new Node(5, 5, 5);
        Node node6 = new Node(6, 6, 6);
        Node node7 = new Node(7, 7, 7);
        Node node8 = new Node(8, 8, 8);
        Solution solution = new Solution(List.of(node1, node2, node3, node4, node5, node6, node7, node8));

        List<Edge> toAdd = List.of(new Edge(node2, node7), new Edge(node3, node8));
        List<Edge> toDelete = List.of(new Edge(node2, node3), new Edge(node7, node8));

        Move move = new Move(toAdd, toDelete, 1, true);
        Solution newSolution = moveEvaluationAlgorithm.applyEdgeSwap(solution, move);
        assertEquals(List.of(node1, node2, node7, node6, node5, node4, node3, node8), newSolution.orderedNodes());
    }
}