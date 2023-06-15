package de.openknowledge.workshop.serverless.oms.lambda;

import java.util.Arrays;
import java.util.Optional;

public enum OrderAction {

    UNKNOWN("unknown"),
    CREATE("createOrder"),
    READ("readOrder"),
    UPDATE("updateOrder"),
    DELETE("deleteOrder");

    private String action;

    OrderAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    // Reverse Lookup
    public static Optional<OrderAction> get(String actionToCheck) {
        if (actionToCheck == null || actionToCheck.isEmpty()) {
            return Optional.empty();
        }
        return Arrays.stream(OrderAction.values())
                .filter(env -> env.action.toUpperCase().equals(actionToCheck.toUpperCase()))
                .findFirst();
    }


}
