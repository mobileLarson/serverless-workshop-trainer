package de.openknowledge.workshop.serverless.oms.repository;

import de.openknowledge.workshop.serverless.oms.model.Order;
import de.openknowledge.workshop.serverless.oms.model.OrderStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MockOrderRepository implements OrderRepository {

    @Override
    public void storeOrder(Order order) {
        // no implementation by default
    }

    @Override
    public Order updateOrder(Order order) {
        return order;
    }

    @Override
    public Order readOrder(String orderId) {
        Order order = new Order();
        order.setOrderId(orderId);
        order.setOrderNo(12345L);
        order.setOrderStatus("mock-status");
        order.setDrink("mock-drink");
        order.setUserId("mock-user");
        order.setBaristaId("mock-barista");
        return order;
    }

    public List<Order> readOrders(OrderRepositoryFilter filter, Map<String, String> filterAttributes){
        Order order = readOrder("");
        return Arrays.asList(order);
    }


    @Override
    public Order cancelOrder(String orderId) {
        Order order = new Order();
        order.setOrderId(orderId);
        order.setOrderNo(12345L);
        order.setOrderStatus(OrderStatus.CANCELED.getStatus());
        order.setDrink("mock-drink");
        order.setUserId("mock-user");
        order.setBaristaId("mock-barista");
        return order;
    }

    @Override
    public void deleteOrder(String orderId) {
        // no implementation by default
    }
}
