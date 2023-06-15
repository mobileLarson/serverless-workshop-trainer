package de.openknowledge.workshop.serverless.oms.repository;

import de.openknowledge.workshop.serverless.oms.model.Order;
import de.openknowledge.workshop.serverless.oms.model.OrderStatus;
import de.openknowledge.workshop.serverless.util.AwsClientProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static software.amazon.awssdk.enhanced.dynamodb.internal.AttributeValues.numberValue;

// https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/examples-dynamodb-enhanced.html
// see https://github.com/awsdocs/aws-doc-sdk-examples/blob/main/javav2/example_code/dynamodb/src/main/java/com/example/dynamodb/PutItem.java
public class DynamoDbLocalOrderRepository implements OrderRepository {

    private static final Logger logger = LoggerFactory.getLogger(DynamoDbLocalOrderRepository.class);

    // order table name
    public static final String ORDER_TABLE_NAME = "sw-order-table";

    private static final String ACCESS_KEY = "fakeMyKeyId";
    private static final String SECRET_ACCESS_KEY = "fakeSecretAccessKey";


    // dynamo db client
    public static final DynamoDbEnhancedClient DDB_ENH_CLIENT
            = AwsClientProvider.getDynamoDbLocalClientFor(ACCESS_KEY,SECRET_ACCESS_KEY );

    /**
     *
     * @param orderId
     */
    public Order readOrder(String orderId) {
        return getItemFromTable(DDB_ENH_CLIENT, orderId);
    }

    public List<Order> readOrders(OrderRepositoryFilter filter, Map<String, String> filterAttributes) {
        return getItemsFromTable(DDB_ENH_CLIENT, filter, filterAttributes);
    }


    /**
     *
     * @param order
     */
    public void storeOrder(Order order) {
        putItemInTable(DDB_ENH_CLIENT, order);
    }

    /**
     *
     * @param order
     */
    public Order updateOrder(Order order) {
        return optionalUpdateItemInTable(DDB_ENH_CLIENT, order);
    }

    /**
     *
     * @param orderId
     */
    public Order cancelOrder(String orderId) {
        Order orderToUpdate = readOrder(orderId);

        if (orderToUpdate != null)  {
            orderToUpdate.setOrderStatus(OrderStatus.CANCELED.getStatus());
        }
        return updateItemInTable(DDB_ENH_CLIENT, orderToUpdate);
    }

    /**
     *
     * @param orderId
     */
    public void deleteOrder(String orderId) {
        removeItemFromTable(DDB_ENH_CLIENT, orderId);
    }

    /**
     *
     * @param ddb
     * @param pk
     */
    private static Order getItemFromTable(DynamoDbEnhancedClient ddb, String pk) {
        DynamoDbTable<Order> orderTable = ddb.table(ORDER_TABLE_NAME, TableSchema.fromBean(Order.class));
        Order order;
        try {
            order = orderTable.getItem(Key.builder()
                    .partitionValue(pk)     // partition key == orderId
                    .build());
            if (order != null) {
                logger.info(String.format("%s: item retrieved (%s | %d)", ORDER_TABLE_NAME, pk, order.getOrderNo()));
            } else {
                logger.info(String.format("%s: no matching item found  (%s)", ORDER_TABLE_NAME, pk));
            }
            return order;
        } catch (DynamoDbException ex) {
            logger.info(String.format("%s: error while retrieving item (%s)", ORDER_TABLE_NAME, pk));
            return null;
        }
    }

    /**
     *
     * @param ddb
     * @param filter
     * @param filterAttributes
     */
    private static List<Order> getItemsFromTable(DynamoDbEnhancedClient ddb, OrderRepositoryFilter filter, Map<String,String> filterAttributes) {

        DynamoDbTable<Order> orderTable = ddb.table(ORDER_TABLE_NAME, TableSchema.fromBean(Order.class));
        List<Order> orders = new ArrayList<>();

        try {

            Map<String, AttributeValue> expressionValues = Map.of(
                    ":min_value", numberValue(1),
                    ":max_value", numberValue(100));

            ScanEnhancedRequest request = ScanEnhancedRequest.builder()
                    .consistentRead(true)
                    // 1. the 'attributesToProject()' method allows you to specify which values you want returned.
                    // .attributesToProject("id", "title", "authors", "price")
                    // 2. Filter expression limits the items returned that match the provided criteria.
                    //.filterExpression(Expression.builder()
                    //        .expression("orderId >= :min_value AND orderId <= :max_value")
                    //        .expressionValues(expressionValues)
                    //        .build())
                    .build();

            // 3. A PageIterable object is returned by the scan method.
            PageIterable<Order> pagedResults = orderTable.scan(request);
            logger.info("page count: {}", pagedResults.stream().count());

            // sorts and logs all items for all pages.
            pagedResults.items().stream()
                    .sorted(Comparator.comparing(Order::getOrderNo))
                    .forEach(
                            // item -> logger.info(item.toString()));
                            item -> orders.add(item));
            return orders;
        } catch (DynamoDbException ex) {
            logger.info(String.format("%s: error while retrieving items (%s | %s)", ORDER_TABLE_NAME, filter, filterAttributes));
            return null;
        }
    }


    /**
     *
     * @param ddb
     * @param pk
     */
    private static void removeItemFromTable(DynamoDbEnhancedClient ddb, String pk) {
        DynamoDbTable<Order> orderTable = ddb.table(ORDER_TABLE_NAME, TableSchema.fromBean(Order.class));
        try {
            orderTable.deleteItem(Key.builder()
                    .partitionValue(pk)     // partition key == orderId
                    .build());
            logger.info(String.format("%s: item removed (%s)", ORDER_TABLE_NAME, pk));
        } catch (DynamoDbException ex) {
            logger.info(String.format("%s: error while removing item (%s)", ORDER_TABLE_NAME, pk));
        }
    }

    /**
     *
     * @param ddb
     * @param order
     */
    private static void putItemInTable(DynamoDbEnhancedClient ddb,
                                       Order order) {

        DynamoDbTable<Order> orderTable = ddb.table(ORDER_TABLE_NAME, TableSchema.fromBean(Order.class));

        String pk = order.getOrderId();
        Long orderNo = order.getOrderNo();

        try {
            // put item (order) to table
            orderTable.putItem(order);
            logger.info(String.format("%s: item put (%s | %d)", ORDER_TABLE_NAME, pk, orderNo));
        } catch (DynamoDbException ex) {
            logger.error(String.format("Error while %s putting item (%s | %d)", ORDER_TABLE_NAME, pk, orderNo));
        }
    }

    // update all fields of incoming order !
    private static Order updateItemInTable(DynamoDbEnhancedClient ddb,
                                       Order order) {

        DynamoDbTable<Order> orderTable = ddb.table(ORDER_TABLE_NAME, TableSchema.fromBean(Order.class));

        Order updatedOrder = order;

        String pk = order.getOrderId();

        try {
            // put item (order) to table
            updatedOrder = orderTable.updateItem(order);
            logger.info(String.format("%s: item updated (%s))", ORDER_TABLE_NAME, pk));
        } catch (DynamoDbException ex) {
            logger.error(String.format("Error while %s updating item (%s)", ORDER_TABLE_NAME, pk));
        }

        return updatedOrder;
    }

    // update only existing fields of incoming order !
    private static Order optionalUpdateItemInTable(DynamoDbEnhancedClient ddb,
                                           Order order) {

        DynamoDbTable<Order> orderTable = ddb.table(ORDER_TABLE_NAME, TableSchema.fromBean(Order.class));

        Order updatedOrder = order;
        String pk = order.getOrderId();

        // read / merge / update
        try {
            Order currentOrder = getItemFromTable(ddb, pk);

            // put item (order) to table
            updatedOrder = orderTable.updateItem(mergeOrders(currentOrder, order));
            logger.info(String.format("%s: item updated (%s))", ORDER_TABLE_NAME, pk));
        } catch (DynamoDbException ex) {
            logger.error(String.format("Error while %s updating item (%s)", ORDER_TABLE_NAME, pk));
        }
        return updatedOrder;
    }

    private static Order mergeOrders(Order original, Order changes) {
        Order mergedOrder = original;


        // check for and set changes (NOT for pk orderId)
        if (changes != null) {

            if (changes.getDrink() != null && !changes.getDrink().isEmpty()) {
                mergedOrder.setDrink(changes.getDrink());
            }

            if (changes.getOrderStatus() != null && !changes.getOrderStatus().isEmpty()) {
                mergedOrder.setOrderStatus(changes.getOrderStatus());
            }

            if (changes.getUserId() != null && !changes.getUserId().isEmpty()) {
                mergedOrder.setUserId(changes.getUserId());
            }

            if (changes.getBaristaId() != null && !changes.getBaristaId().isEmpty()) {
                mergedOrder.setBaristaId(changes.getBaristaId());
            }

            if (changes.getOrderNo() != null && !changes.getOrderNo().equals(0L)) {
                mergedOrder.setOrderNo(changes.getOrderNo());
            }
        }
        return mergedOrder;
    }
}



