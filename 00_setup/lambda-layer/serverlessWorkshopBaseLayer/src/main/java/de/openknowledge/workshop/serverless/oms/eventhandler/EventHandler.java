package de.openknowledge.workshop.serverless.oms.eventhandler;

import java.util.Map;

public interface EventHandler {

    String putEvent(String source, String detailType, Map<String, String> details);
}
