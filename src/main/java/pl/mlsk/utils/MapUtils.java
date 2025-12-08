package pl.mlsk.utils;

import lombok.experimental.UtilityClass;
import pl.mlsk.common.Node;
import pl.mlsk.common.Solution;

import java.util.*;

@UtilityClass
public class MapUtils {

    public Map<Node, Node> mapFromSolution(Solution solution) {
        Map<Node, Node> nodeMap = new HashMap<>();
        for (int i = 1; i < solution.orderedNodes().size(); i++) {
            Node nodeLeft = solution.orderedNodes().get(i - 1);
            Node nodeRight = solution.orderedNodes().get(i);
            nodeMap.put(nodeLeft, nodeRight);
        }
        nodeMap.put(solution.orderedNodes().getLast(), solution.orderedNodes().getFirst());
        return nodeMap;
    }

    public Solution buildFromMap(Map<Node, Node> map, Solution originalSolution) {
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
}
