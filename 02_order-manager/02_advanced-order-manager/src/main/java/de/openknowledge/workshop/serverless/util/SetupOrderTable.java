package de.openknowledge.workshop.serverless.util;

import de.openknowledge.workshop.serverless.oms.model.Order;
import de.openknowledge.workshop.serverless.oms.repository.DynamoDBEnhancedOrderRepository;
import software.amazon.awssdk.core.internal.waiters.ResponseOrException;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;
import software.amazon.awssdk.services.dynamodb.waiters.DynamoDbWaiter;

public class SetupOrderTable {


    public static void main(String[] args) {
        createTable();
    }


    public static void createTable() {

        DynamoDbEnhancedClient enhancedDbb = AwsClientProvider.getDynamoDBEnhancedClient();

        DynamoDbTable<Order> orderTable = enhancedDbb.table(DynamoDBEnhancedOrderRepository.ORDER_TABLE_NAME, TableSchema.fromBean(Order.class));
        // Create the table
        orderTable.createTable(builder -> builder
                .build()
        );

        System.out.println("Waiting for table creation...");

        try (DynamoDbWaiter waiter = DynamoDbWaiter.create()) { // DynamoDbWaiter is Autocloseable
            ResponseOrException<DescribeTableResponse> response = waiter
                    .waitUntilTableExists(builder -> builder.tableName("sw-order-table").build())
                    .matched();
            DescribeTableResponse tableDescription = response.response().orElseThrow(
                    () -> new RuntimeException("Order table was not created."));
            // The actual error can be inspected in response.exception()
            System.out.println(tableDescription.table().tableName() + " was created.");
        }
    }

    public static String createTableOld() {

        DynamoDbClient dbb = AwsClientProvider.getDynamoDbClient();
        String tableName = DynamoDBEnhancedOrderRepository.ORDER_TABLE_NAME;


        AttributeDefinition orderIdAttributeDefinition = AttributeDefinition.builder()
                .attributeName("PK")  // partition key: order id
                .attributeType(ScalarAttributeType.S)
                .build();

        AttributeDefinition orderNoAttributeDefinition = AttributeDefinition.builder()
                .attributeName("SK")  // sorting key: order no
                .attributeType(ScalarAttributeType.S)
                .build();

        KeySchemaElement primaryKey = KeySchemaElement.builder()
                .attributeName("PK")
                .keyType(KeyType.HASH)
                .build();

        KeySchemaElement secondaryKey = KeySchemaElement.builder()
                .attributeName("SK")
                .keyType(KeyType.RANGE)
                .build();

        CreateTableRequest createTableRequest = CreateTableRequest.builder()
                .attributeDefinitions(orderIdAttributeDefinition, orderNoAttributeDefinition)
                .keySchema(primaryKey, secondaryKey)
                .billingMode(BillingMode.PAY_PER_REQUEST)
                .tableName(tableName)
                .build();

        String tableId = "UNKNOWN";

        try {
            CreateTableResponse createTableResponse = dbb.createTable(createTableRequest);
            tableId = createTableResponse.tableDescription().tableId();
        } catch (DynamoDbException ex) {
            System.err.println(ex.getMessage());
            // System.exit(1);
        }
        return tableId;

    }

}
