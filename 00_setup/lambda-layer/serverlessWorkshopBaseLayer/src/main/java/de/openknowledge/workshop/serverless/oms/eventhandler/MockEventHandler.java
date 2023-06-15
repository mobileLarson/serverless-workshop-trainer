package de.openknowledge.workshop.serverless.oms.eventhandler;

import java.util.Map;

public class MockEventHandler implements EventHandler {

    @Override
    public String putEvent(String source, String detailType, Map<String, String> details) {
        return null;
    }
}
