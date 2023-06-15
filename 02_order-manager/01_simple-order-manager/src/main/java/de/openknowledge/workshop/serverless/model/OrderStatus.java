package de.openknowledge.workshop.serverless.model;

import java.util.Arrays;
import java.util.Optional;

public enum OrderStatus {

    UNKNOWN("unknown"),
    CONFIRMED("confirmed"),
    CREATED("created"),
    CANCELED("canceled"),
    PICKED_UP("picked-up");

    private String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    // Reverse Lookup
    public static Optional<OrderStatus> get(String status) {
        return Arrays.stream(OrderStatus.values())
                .filter(env -> env.status.equals(status))
                .findFirst();
    }


}
