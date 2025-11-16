package pl.mlsk.algorithm.impl.lab5.validator;

import lombok.Data;

@Data
public class MoveValidationResult {
    private boolean valid = true;
    private boolean edgesOutOfOrder = false;

    public void invalid() {
        valid = false;
    }

    public void edgesOutOfOrder() {
        edgesOutOfOrder = true;
    }
}
