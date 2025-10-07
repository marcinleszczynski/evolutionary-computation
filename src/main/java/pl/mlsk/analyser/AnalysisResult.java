package pl.mlsk.analyser;

import pl.mlsk.common.Solution;

public record AnalysisResult(
    double bestValue,
    double bestValueTime,
    double averageValue,
    double averageValueTime,
    double worstValue,
    double worstValueTime,
    Solution bestSolution
) { }
