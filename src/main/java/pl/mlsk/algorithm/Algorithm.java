package pl.mlsk.algorithm;

import pl.mlsk.common.AlgorithmInput;
import pl.mlsk.common.Solution;

public interface Algorithm {

    Solution solve(AlgorithmInput input, int pos);

    String algorithmName();
}
