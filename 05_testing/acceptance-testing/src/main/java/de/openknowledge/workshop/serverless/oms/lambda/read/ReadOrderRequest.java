package de.openknowledge.workshop.serverless.oms.lambda.read;

import de.openknowledge.workshop.serverless.oms.lambda.OrderAction;
import de.openknowledge.workshop.serverless.oms.lambda.OrderRequest;
import de.openknowledge.workshop.serverless.oms.model.Order;

import java.util.Optional;

public class ReadOrderRequest {

    private String orderId;

    public ReadOrderRequest() {
    }

    public ReadOrderRequest(OrderRequest orderRequest) {
        if (isValidReadOrderRequest(orderRequest)) {
            this.orderId = orderRequest.getOrder().getOrderId();
        } else {
            throw new IllegalArgumentException();
        }
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public boolean isValid() {
        return orderId != null;
    }


    public static boolean isValidReadOrderRequest(OrderRequest request) {

        // check for action
        Optional<OrderAction> action = OrderAction.get(request.getAction());
        if (action.isEmpty() || !action.get().equals(OrderAction.DELETE)) {
            return false;
        }

        // check for userId and drink
        Order order = request.getOrder();
        return (order != null && order.getOrderId() != null);
    }

}