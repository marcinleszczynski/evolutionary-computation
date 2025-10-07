package pl.mlsk.vizualization;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.springframework.stereotype.Service;
import pl.mlsk.common.Node;

import java.util.List;
import java.util.UUID;

@Service
public class Visualizer {

    private static final String NODE_PROPERTIES = "xy";
    private static final String COLOR_TEMPLATE = "fill-color: %s;";

    public void visualize(List<Node> allNodes, List<Node> resultNodes) {
        Graph graph = new SingleGraph("TSP - solution");
        graph.setAttribute("ui.title", "TSP - solution (red - high cost, green - low cost)");
        double maxCost = allNodes
                .stream()
                .mapToDouble(Node::cost)
                .max()
                .orElseThrow(RuntimeException::new);

        double minCost = allNodes
                .stream()
                .mapToDouble(Node::cost)
                .min()
                .orElseThrow(RuntimeException::new);
        allNodes.forEach(node -> {
            var newNode = graph.addNode(node.toString());
            newNode.setAttribute(NODE_PROPERTIES, node.x(), node.y());
            String color = normalizeForColor(maxCost, minCost, node.cost());
            newNode.setAttribute("ui.style", formatColor(color));
        });
        for (int i = 1; i < resultNodes.size(); i++) {
            addGraphEdge(graph, resultNodes.get(i-1), resultNodes.get(i));
        }
        addGraphEdge(graph, resultNodes.getLast(), resultNodes.getFirst());
        graph.display(false);
    }



    private void addGraphEdge(Graph graph, Node from, Node to) {
        graph.addEdge(UUID.randomUUID().toString(), from.toString(), to.toString());
    }

    private String normalizeForColor(double max, double min, double cost) {
        double normalized = (cost - min) / (max - min);
        int red = (int) (255 * normalized);
        int green = (int) (255 * (1 - normalized));
        return String.format("rgb(%d, %d, 0)", red, green);
    }

    private String formatColor(String color) {
        return String.format(COLOR_TEMPLATE, color);
    }
}
