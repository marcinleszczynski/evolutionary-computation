package pl.mlsk.common;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

@Service
public class NodeReader {

    private static final String SEMICOLON = ";";

    public AlgorithmInput readNodes(String path) {
        InputStream in = NodeReader.class.getResourceAsStream(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        List<Node> nodes = reader.lines()
                .map(this::parseNode)
                .toList();

        return new AlgorithmInput(nodes, new DistanceMatrix(nodes));
    }

    private Node parseNode(String line) {
        List<Integer> numbers = Arrays
            .stream(line.split(SEMICOLON))
            .map(Integer::parseInt)
            .toList();
        return new Node(
            numbers.get(0),
            numbers.get(1),
            numbers.get(2)
        );
    }
}
