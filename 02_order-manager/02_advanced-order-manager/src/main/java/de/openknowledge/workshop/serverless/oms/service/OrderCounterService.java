package de.openknowledge.workshop.serverless.oms.service;

import de.openknowledge.workshop.serverless.util.AwsClientProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;
import java.util.Map;


// see https://nickolasfisher.com/blog/Atomic-Incrementing-in-DynamoDB-with-the-Java-AWS-SDK-20
public class OrderCounterService {

    public static final Integer UNKNOWN = -1;

    private static final Logger logger = LoggerFactory.getLogger(OrderCounterService.class);

    public static final String ORDER_ID_TABLE_NAME = "sw-order-counter-table";
    public static final String PARTITION_KEY = "PK";
    public static final String PARTITION_KEY_VALUE = "order-counter";

    // dynamo db client
    public static final DynamoDbClient DDB_CLIENT = AwsClientProvider.getDynamoDbClient();

    public static final Long next() {
        return updateViaExpression();
    }

    private static Long updateViaExpression() {
        // replace with ENHANCED DYNAMODB CLIENT WHEN FIXED BY AWS
        // see https://aws.amazon.com/de/blogs/developer/using-atomic-counters-in-the-enhanced-dynamodb-aws-sdk-for-java-2-x-client/
        HashMap<String, AttributeValue> keyToGet =
                new HashMap<String, AttributeValue>();

        keyToGet.put(PARTITION_KEY, AttributeValue.builder()
                .s(PARTITION_KEY_VALUE).build());

        HashMap<String, AttributeValue> updateExpressionAttributeValues =
                new HashMap<String, AttributeValue>();

        updateExpressionAttributeValues.put(":incr_amt", AttributeValue.builder().n("1").build());
        updateExpressionAttributeValues.put(":initial", AttributeValue.builder().n("0").build());

        UpdateItemRequest updateItemRequest = UpdateItemRequest.builder()
                .tableName(ORDER_ID_TABLE_NAME)
                .key(keyToGet)
                .updateExpression("SET currentValue = if_not_exists(currentValue, :initial) + :incr_amt")
                .expressionAttributeValues(updateExpressionAttributeValues)
                .returnValues(ReturnValue.UPDATED_NEW)
                .build();

        try {
            UpdateItemResponse response = DDB_CLIENT.updateItem(updateItemRequest);

            Map<String,AttributeValue> returnedItem = response.attributes();
            if (returnedItem != null) {
                AttributeValue attributeValue = returnedItem.get("currentValue");
                return Long.parseLong(attributeValue.n());
            } else {
                logger.error("No item found with the key %s!\n", "currentValue");
            }
        } catch (DynamoDbException ex) {
            ex.printStackTrace();
        }
        return -1L;

    }
}
