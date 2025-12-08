package pl.mlsk.algorithm.impl.lab8.plot;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.math.stat.correlation.PearsonsCorrelation;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.SeriesMarkers;
import org.springframework.stereotype.Service;
import pl.mlsk.common.Solution;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ConvexityPlotService {

    public void plot(Map<Solution, Double> data, String title) {
        Map<Solution, Double> evalMap = new HashMap<>();
        for (Solution solution : data.keySet()) {
            evalMap.put(solution, solution.evaluate());
        }

        Map<Double, Double> pointMap = new HashMap<>();
        data.keySet()
                .stream()
                .collect(Collectors.groupingBy(evalMap::get))
                .forEach((key, value) -> {
                    double avg = value.stream()
                            .mapToDouble(data::get)
                            .average()
                            .orElseThrow();
                    pointMap.put(key, avg);
                });
        Pair<double[], double[]> inputForPlot = mapToArrays(pointMap);
        plot(inputForPlot.getLeft(), inputForPlot.getRight(), title);
    }

    private Pair<double[], double[]> mapToArrays(Map<Double, Double> map) {
        double[] x = new double[map.size()];
        double[] y = new double[map.size()];
        int i = 0;
        for (Map.Entry<Double, Double> entry : map.entrySet()) {
            x[i] = entry.getKey();
            y[i++] = entry.getValue();
        }
        return Pair.of(x, y);
    }

    private void plot(double[] x, double[] y, String title) {
        XYChart chart = new XYChartBuilder()
                .width(600).height(400)
                .title(title + ", Correlation coefficient = (" + correlationCoefficient(x, y) + ")")
                .xAxisTitle("Evaluation")
                .yAxisTitle("Similarity")
                .build();

        XYSeries series = chart.addSeries("data", x, y);
        series.setMarker(SeriesMarkers.CIRCLE);
        series.setLineStyle(SeriesLines.NONE);

        new SwingWrapper<>(chart).displayChart();
    }

    private BigDecimal correlationCoefficient(double[] x, double[] y) {
        double result = new PearsonsCorrelation().correlation(x, y);
        return BigDecimal.valueOf(result);
    }
}
