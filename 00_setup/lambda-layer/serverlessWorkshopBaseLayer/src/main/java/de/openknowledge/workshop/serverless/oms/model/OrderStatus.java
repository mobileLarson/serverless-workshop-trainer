package de.openknowledge.workshop.serverless.oms.model;

import java.util.Arrays;
import java.util.Optional;

public enum OrderStatus {

    UNKNOWN("unknown"),
    ORDERED("ordered"),            // by customer
    CONFIRMED("confirmed"),         // by system
    IN_PROGRESS("in progress"),     // by barista
    FULFILLED("fulfilled"),         // by barista
    CANCELED("canceled"),           // by customer or barista
    PICKED_UP("picked-up");         // by customer

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
