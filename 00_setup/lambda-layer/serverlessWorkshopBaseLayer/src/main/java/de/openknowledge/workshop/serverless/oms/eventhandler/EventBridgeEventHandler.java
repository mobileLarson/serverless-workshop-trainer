package de.openknowledge.workshop.serverless.oms.eventhandler;

import de.openknowledge.workshop.serverless.util.AwsClientProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.eventbridge.EventBridgeClient;
import software.amazon.awssdk.services.eventbridge.model.PutEventsRequest;
import software.amazon.awssdk.services.eventbridge.model.PutEventsRequestEntry;
import software.amazon.awssdk.services.eventbridge.model.PutEventsResponse;
import software.amazon.awssdk.services.eventbridge.model.PutEventsResultEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EventBridgeEventHandler implements EventHandler{

    private static final Logger logger = LoggerFactory.getLogger(EventBridgeEventHandler.class);

    // image table name
    public static final String EVENT_BUS_NAME = "sw-event-bus";
    public static final String EVENT_BUS_NAME_ARN = "arn:aws:events:eu-central-1:460357271599:event-bus/sw-event-bus";

    // dynamo db client
    public static final EventBridgeClient EVENT_BRIDGE_CLIENT = AwsClientProvider.getEventBridgeClientClient();

    public String putEvent(String source, String detailType, Map<String, String> details) {
        String eventId = "UNKNOWN-ID";

        logger.info(String.format("CREATE EVENT: %s | %s | %s", source,detailType,  details));

        String detailString = generateDetailEntry(details);

        logger.info(String.format("DETAIL STRING: %s",  detailString));

        PutEventsRequestEntry requestEntry = PutEventsRequestEntry.builder()
                .eventBusName("sw-event-bus")
                // .resources(EVENT_BUS_NAME_ARN)
                .source(source)
                .detailType(detailType)
                .detail(detailString)
                .build();

        List<PutEventsRequestEntry> requestEntries = new ArrayList<>();
        requestEntries.add(requestEntry);

        PutEventsRequest eventsRequest = PutEventsRequest.builder()
                .entries(requestEntries)
                .build();

        PutEventsResponse eventsResponse = EVENT_BRIDGE_CLIENT.putEvents(eventsRequest);
        for (PutEventsResultEntry resultEntry : eventsResponse.entries()) {
            if (resultEntry.eventId() != null) {
                eventId = resultEntry.eventId();
            }
        }

        logger.info(String.format("RESULT: %s",  eventId));

        return eventId;
    }

    private static String generateDetailEntry(Map<String, String> details) {
         // "{ \"key1\": \"value1\", \"key2\": \"value2\" }")

        if (details.isEmpty()) {
            return "{}";
        }

        String detailsAsString = "";
        StringBuilder builder = new StringBuilder();

        String DETAIL_PATTERN = " \"%s\": \"%s\"";

        builder.append("{");
        for (Map.Entry<String, String> set : details.entrySet()) {
            builder.append(String.format(DETAIL_PATTERN, set.getKey(), set.getValue()));
            builder.append(",");
        }
        builder.deleteCharAt(builder.length() - 1);  // delete last ","
        builder.append("}");
        return builder.toString();
    }
}
