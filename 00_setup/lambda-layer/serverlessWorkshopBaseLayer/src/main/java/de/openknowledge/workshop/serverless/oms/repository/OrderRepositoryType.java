package de.openknowledge.workshop.serverless.oms.repository;

import java.util.Arrays;
import java.util.Optional;

public enum OrderRepositoryType {

    MOCK("mock"),
    DYNAMODB("dynamoDb"),
    DYNAMODB_ENHANCED("dynamoDbEnhanced");

    private String type;

    OrderRepositoryType(String status) {
        this.type = status;
    }

    public String getType() {
        return type;
    }

    // Reverse Lookup
    public static Optional<OrderRepositoryType> get(String type) {
        return Arrays.stream(OrderRepositoryType.values())
                .filter(env -> env.type.equals(type))
                .findFirst();
    }

}
