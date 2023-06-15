package de.openknowledge.workshop.serverless.oms.lambda.update;

import de.openknowledge.workshop.serverless.oms.lambda.OrderAction;
import de.openknowledge.workshop.serverless.oms.lambda.OrderRequest;
import de.openknowledge.workshop.serverless.oms.model.Order;

import java.util.Optional;

public class UpdateOrderRequest {

    Order order;

    public UpdateOrderRequest() {
    }

    public UpdateOrderRequest(OrderRequest orderRequest) {
        if (isValidUpdateOrderRequest(orderRequest)) {
            this.order = orderRequest.getOrder();
        } else {
            throw new IllegalArgumentException();
        }
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public static boolean isValidUpdateOrderRequest(OrderRequest request) {

        // check for action
        Optional<OrderAction> action = OrderAction.get(request.getAction());
        if (action.isEmpty() || !action.get().equals(OrderAction.UPDATE)) {
            return false;
        }

        // check for userId and drink
        Order order = request.getOrder();
        return (order != null && order.getOrderId() != null);
    }
}
