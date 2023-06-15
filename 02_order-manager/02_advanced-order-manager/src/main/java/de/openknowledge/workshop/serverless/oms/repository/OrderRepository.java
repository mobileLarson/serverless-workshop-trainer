package de.openknowledge.workshop.serverless.oms.repository;

import de.openknowledge.workshop.serverless.oms.model.Order;

import java.util.List;
import java.util.Map;

public interface OrderRepository {

    void storeOrder(Order order);
    Order updateOrder(Order order);
    Order readOrder(String orderId);
    List<Order> readOrders(OrderRepositoryFilter filter, Map<String, String> filterAttributes);
    Order cancelOrder(String orderId);
    void deleteOrder(String orderId);
}
