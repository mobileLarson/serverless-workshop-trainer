package de.openknowledge.workshop.serverless.oms.repository;

import de.openknowledge.workshop.serverless.oms.model.Order;
import de.openknowledge.workshop.serverless.oms.model.OrderStatus;
import de.openknowledge.workshop.serverless.util.AwsClientProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamoDbOrderRepository implements OrderRepository {

    private static final Logger logger = LoggerFactory.getLogger(DynamoDbOrderRepository.class);

    // order table name
    public static final String ORDER_TABLE_NAME = "sw-order-table";

    // dynamo db client
    public static final DynamoDbClient DDB_CLIENT = AwsClientProvider.getDynamoDbClient();

    private static final String COL_ORDER_ID = "PK";
    private static final String COL_ORDER_NO = "orderNo";
    private static final String COL_ORDER_STATE = "state";
    private static final String COL_USER_ID = "userId";
    private static final String COL_BARISTA_ID = "barista";
    private static final String COL_DRINK = "drink";

    @Override
    public void storeOrder(Order order) {

        HashMap<String, AttributeValue> itemValues = new HashMap<String,AttributeValue>();

        // Add all content to the table
        itemValues.put(COL_ORDER_ID, AttributeValue.builder().s(order.getOrderId()).build());
        itemValues.put(COL_ORDER_NO, AttributeValue.builder().n(order.getOrderNo().toString()).build());
        itemValues.put(COL_DRINK, AttributeValue.builder().s(order.getDrink()).build());
        itemValues.put(COL_USER_ID, AttributeValue.builder().s(order.getUserId()).build());
        itemValues.put(COL_ORDER_STATE, AttributeValue.builder().s(order.getOrderStatus()).build());

        // optional attribute
        if (order.getBaristaId() != null){
            itemValues.put(COL_BARISTA_ID, AttributeValue.builder().s(order.getBaristaId()).build());
        }

        PutItemRequest request = PutItemRequest.builder()
                .tableName(ORDER_TABLE_NAME)
                .item(itemValues)
                .build();

        try {
            PutItemResponse putItemResponse = DDB_CLIENT.putItem(request);
            logger.info(String.format("%s: item put (%s | %d)", ORDER_TABLE_NAME, order.getOrderId(), order.getOrderNo()));
            if (putItemResponse.hasAttributes()) {
                for (var attribute : putItemResponse.attributes().entrySet()) {
                    logger.debug(String.format("Attribute $s has value $s", attribute.getKey()), attribute.getValue().s());
                }
            }
        } catch (ResourceNotFoundException e) {
            logger.error(String.format("Error: The Amazon DynamoDB table \"%s\" can't be found.", ORDER_TABLE_NAME));
            logger.error("Be sure that it exists and that you've typed its name correctly!");
            // TODO some useful ex handling ;-)
        } catch (DynamoDbException e) {
            logger.error(String.format("Error: %s", e.getMessage()));
            // TODO some useful ex handling ;-)
        }
    }

    @Override
    public Order updateOrder(Order order) {

        HashMap<String,AttributeValue> itemKey = new HashMap<String,AttributeValue>();

        itemKey.put(COL_ORDER_ID, AttributeValue.builder().s(order.getOrderId()).build());

        Map<String,AttributeValueUpdate> updatedValues =
                new HashMap<String,AttributeValueUpdate>();

        updatedValues = orderToOrderItem(order);

        UpdateItemRequest request = UpdateItemRequest.builder()
                .tableName(ORDER_TABLE_NAME)
                .key(itemKey)
                .attributeUpdates(updatedValues)
                .returnValues(ReturnValue.ALL_NEW)
                .build();

        try {

            // TODO
            // - update order (item) with the help of the DDB_CLIENT and update item request
            // - extract order information from return attributes of update item response (see private method orderItemToOrder)
            // - return extracted order

            UpdateItemResponse updateItemResponse = DDB_CLIENT.updateItem(request);
            Order updatedOrder = orderItemToOrder(updateItemResponse.attributes());
            logger.info(String.format("%s: item updated (%s | %d)", ORDER_TABLE_NAME, updatedOrder.getOrderId(), updatedOrder.getOrderNo()));
            return updatedOrder;
        } catch (ResourceNotFoundException e) {
            logger.error(String.format("Error: The Amazon DynamoDB table \"%s\" can't be found.", ORDER_TABLE_NAME));
            logger.error("Be sure that it exists and that you've typed its name correctly!");
            // TODO some useful ex handling ;-)
            return null;
        } catch (DynamoDbException e) {
            logger.error(String.format("Error: %s", e.getMessage()));
            // TODO some useful ex handling ;-)
            return null;
        }
    }

    @Override
    public Order readOrder(String orderId) {

        Map<String, AttributeValue> orderItem =
                new HashMap<String,AttributeValue>();

        Map<String, AttributeValue> itemKey =
                new HashMap<String,AttributeValue>();

        itemKey.put(COL_ORDER_ID, AttributeValue.builder()
                .s(orderId).build());

        GetItemRequest getItemRequest =
                GetItemRequest.builder()
                        .key(itemKey)
                        .tableName(ORDER_TABLE_NAME)
                        .build();

        try {

            GetItemResponse getItemResponse = DDB_CLIENT.getItem(getItemRequest);
            if (getItemResponse.hasItem()) {

                logger.info(String.format("%s: item retrieved (%s)", ORDER_TABLE_NAME, orderId));

                orderItem = getItemResponse.item();
                return orderItemToOrder(orderItem);
            } else {
                logger.info(String.format("%s: no matching item found  (%s)", ORDER_TABLE_NAME, orderId));
                return null;
            }

        } catch (ResourceNotFoundException e) {
            logger.error(String.format("Error: The Amazon DynamoDB table \"%s\" can't be found.", ORDER_TABLE_NAME));
            logger.error("Be sure that it exists and that you've typed its name correctly!");
            // TODO some useful ex handling ;-)
            return null;
        } catch (DynamoDbException e) {
            logger.error(String.format("Error: %s", e.getMessage()));
            // TODO some useful ex handling ;-)
            return null;
        }
    }

    public List<Order> readOrders(OrderRepositoryFilter filter, Map<String, String> filterAttributes){
        // TODO to be implemented
        return Collections.emptyList();
    }


    @Override
    public Order cancelOrder(String orderId) {
        Order orderToCancel = readOrder(orderId);
        orderToCancel.setOrderStatus(OrderStatus.CANCELED.getStatus());
        return updateOrder(orderToCancel);
    }

    @Override
    public void deleteOrder(String orderId) {

        Map<String, AttributeValue> itemKey =
                new HashMap<String,AttributeValue>();

        itemKey.put(COL_ORDER_ID, AttributeValue.builder()
                .s(orderId).build());

        DeleteItemRequest deleteItemRequest =
                DeleteItemRequest.builder()
                        .tableName(ORDER_TABLE_NAME)
                        .key(itemKey)
                        .build();
        try {
            DeleteItemResponse deleteItemResponse = DDB_CLIENT.deleteItem(deleteItemRequest);
            logger.info(String.format("%s: item removed (%s)", ORDER_TABLE_NAME, orderId));
        } catch (ResourceNotFoundException e) {
            logger.error(String.format("Error: The Amazon DynamoDB table \"%s\" can't be found.", ORDER_TABLE_NAME));
            logger.error("Be sure that it exists and that you've typed its name correctly!");
            // TODO some useful ex handling ;-)
        } catch (DynamoDbException e) {
            logger.error(String.format("Error: %s", e.getMessage()));
            // TODO some useful ex handling ;-)
        }
    }

    //------- private methods

    private Order orderItemToOrder(Map<String, AttributeValue> orderItem) {
        Order order = new Order();
        order.setOrderId(orderItem.get(COL_ORDER_ID).s());
        order.setOrderNo(Long.parseLong(orderItem.get(COL_ORDER_NO).n()));

        // TODO
        // - extract and set order attributes from orderItem key/attribute map
        // - consider that barista id may not be present!

        order.setOrderStatus(orderItem.get(COL_ORDER_STATE).s());
        order.setUserId(orderItem.get(COL_USER_ID).s());
        order.setDrink(orderItem.get(COL_DRINK).s());
        if (orderItem.containsKey(COL_BARISTA_ID)) {
            order.setBaristaId(orderItem.get(COL_BARISTA_ID).s());
        }
        return order;
    }

    private Map<String, AttributeValueUpdate> orderToOrderItem(Order order) {

        Map<String, AttributeValueUpdate> orderItem = new HashMap<>();

        // there should be no need to change the key values
        // - PK: orderId

        /*
        orderItem.put(COL_ORDER_ID, AttributeValueUpdate.builder()
                .value(AttributeValue.builder().s(order.getOrderId()).build())
                .action(AttributeAction.PUT)
                .build());
        */

        // make sure to update only VALID aka NON-EMPTY fields

        if (order.getOrderNo() != null && order.getOrderNo() != 0L) {
            orderItem.put(COL_ORDER_NO, AttributeValueUpdate.builder()
                    .value(AttributeValue.builder().n(order.getOrderNo().toString()).build())
                    .action(AttributeAction.PUT)
                    .build());
        }

        if (order.getUserId() != null && !order.getUserId().isEmpty()) {
            orderItem.put(COL_USER_ID, AttributeValueUpdate.builder()
                    .value(AttributeValue.builder().s(order.getUserId()).build())
                    .action(AttributeAction.PUT)
                    .build());
        }

        if (order.getOrderStatus() != null && !order.getOrderStatus().isEmpty()) {
            orderItem.put(COL_ORDER_STATE, AttributeValueUpdate.builder()
                    .value(AttributeValue.builder().s(order.getOrderStatus()).build())
                    .action(AttributeAction.PUT)
                    .build());
        }

        if (order.getBaristaId() != null && !order.getBaristaId().isEmpty()) {
            orderItem.put(COL_BARISTA_ID, AttributeValueUpdate.builder()
                    .value(AttributeValue.builder().s(order.getBaristaId()).build())
                    .action(AttributeAction.PUT)
                    .build());
        }

        if (order.getDrink() != null && !order.getDrink().isEmpty()) {
            orderItem.put(COL_DRINK, AttributeValueUpdate.builder()
                    .value(AttributeValue.builder().s(order.getDrink()).build())
                    .action(AttributeAction.PUT)
                    .build());
        }

        return orderItem;
    }

}
