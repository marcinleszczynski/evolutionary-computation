package pl.mlsk.algorithm.impl.lab4;

public record DeltaResult(
        Double delta,
        boolean isEdgeSwap
) {
    public static DeltaResult edge(Double delta) {
        return new DeltaResult(delta, true);
    }

    public static DeltaResult interMove(double delta) {
        return new DeltaResult(delta, false);
    }
}