package de.openknowledge.workshop.serverless.ocs.lambda;

import java.util.Arrays;
import java.util.Optional;

public enum OrderCountingAction {

    UNKNOWN("unknown"),
    INIT("init"),
    INCREMENT("increment"),
    DECREMENT("decrement");

    private String action;

    OrderCountingAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    // Reverse Lookup
    public static Optional<OrderCountingAction> get(String action) {
        return Arrays.stream(OrderCountingAction.values())
                .filter(env -> env.action.equals(action))
                .findFirst();
    }


}