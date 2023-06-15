package de.openknowledge.workshop.serverless.oms.eventhandler;

import java.util.Arrays;
import java.util.Optional;

public enum EventHandlerType {

    MOCK("mock"),
    EVENT_BRIDGE("eventBridge");

    private String type;

    EventHandlerType(String status) {
        this.type = status;
    }

    public String getType() {
        return type;
    }

    // Reverse Lookup
    public static Optional<EventHandlerType> get(String type) {
        return Arrays.stream(EventHandlerType.values())
                .filter(env -> env.type.equals(type))
                .findFirst();
    }

}
