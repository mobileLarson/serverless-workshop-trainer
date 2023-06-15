package de.openknowledge.workshop.serverless.oms.eventhandler;

public class EventHandlerFactory {

    public static EventHandler getEventHandler(EventHandlerType eventHandlerType) {
        switch (eventHandlerType) {
            case EVENT_BRIDGE:
                return new EventBridgeEventHandler();
            case MOCK:
                return new MockEventHandler();
            default:
                return new EventBridgeEventHandler();
        }
    }
}
