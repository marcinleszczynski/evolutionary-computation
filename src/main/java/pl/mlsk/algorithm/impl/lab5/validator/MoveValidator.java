package pl.mlsk.algorithm.impl.lab5.validator;

import org.springframework.stereotype.Service;
import pl.mlsk.algorithm.impl.lab5.dto.Edge;
import pl.mlsk.algorithm.impl.lab5.dto.Move;
import pl.mlsk.common.Node;
import pl.mlsk.common.Solution;

import java.util.List;

@Service
public class MoveValidator {

    public MoveValidationResult validate(Solution solution, Move move) {
        MoveValidationResult result = new MoveValidationResult();
        if (move.isEdgeSwap()) {
            verifyEdgesToRemoveStillInSolution(result, solution, move);
            if (!result.isValid())
                return result;
            verifyFirstEdgeBeforeSecondEdge(result, solution, move);
        }
        if (move.isInterMove()) {
            verifyNodeToRemoveInSolution(result, solution, move);
            verifyNodeToAddIsNotInSolution(result, solution, move);
        }
        return result;
    }

    private void verifyFirstEdgeBeforeSecondEdge(MoveValidationResult result, Solution solution, Move move) {
        List<Edge> toAdd = move.toAdd();

        Node toAdd11 = toAdd.getFirst().node1();
        Node toAdd21 = toAdd.getLast().node1();

        if (solution.orderedNodes().indexOf(toAdd11) > solution.orderedNodes().indexOf(toAdd21)) {
            result.edgesOutOfOrder();
        }
    }

    private void verifyEdgesToRemoveStillInSolution(MoveValidationResult result, Solution solution, Move move) {
        for (Edge toDelete : move.toDelete()) {
            int indexLeft = solution.orderedNodes().indexOf(toDelete.node1());
            int indexRight = solution.orderedNodes().indexOf(toDelete.node2());

            if (indexLeft == -1 || indexRight == -1) {
                result.invalid();
                break;
            }

            if (indexRight + 1 == indexLeft) {
                result.edgesOutOfOrder();
            } else {
                result.setEdgesOutOfOrder(false);
            }

            if (indexLeft + 1 != indexRight) {
                result.invalid();
            }
        }
    }

    private void verifyNodeToAddIsNotInSolution(MoveValidationResult result, Solution solution, Move move) {
        Node nodeToAdd = move.nodeToAdd();
        if (solution.orderedNodes().contains(nodeToAdd)) {
            result.invalid();
        }
    }

    private void verifyNodeToRemoveInSolution(MoveValidationResult result, Solution solution, Move move) {
        Node nodeToRemove = move.nodeToRemove();
        if (!solution.orderedNodes().contains(nodeToRemove)) {
            result.invalid();
        }
    }
}
