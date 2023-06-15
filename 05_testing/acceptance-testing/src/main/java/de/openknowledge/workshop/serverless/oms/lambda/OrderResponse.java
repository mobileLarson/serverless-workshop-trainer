package de.openknowledge.workshop.serverless.oms.lambda;

import de.openknowledge.workshop.serverless.oms.lambda.create.CreateOrderResponse;
import de.openknowledge.workshop.serverless.oms.lambda.delete.DeleteOrderResponse;
import de.openknowledge.workshop.serverless.oms.lambda.read.ReadOrderResponse;
import de.openknowledge.workshop.serverless.oms.lambda.update.UpdateOrderResponse;
import de.openknowledge.workshop.serverless.oms.model.Order;

public class OrderResponse {

    private String status;
    private String message;
    private Order order;

    public OrderResponse() {}


    public OrderResponse(ReadOrderResponse readOrderResponse) {
        this.order = readOrderResponse.getOrder();
        this.status = readOrderResponse.getStatus();
        this.message = readOrderResponse.getMessage();
    }

    public OrderResponse(CreateOrderResponse createOrderResponse) {
        this.status = createOrderResponse.getStatus();
        this.order = createOrderResponse.getOrder();
        this.message = createOrderResponse.getMessage();
    }

    public OrderResponse(UpdateOrderResponse updateOrderResponse) {
        this.order = updateOrderResponse.getOrder();
        this.status = updateOrderResponse.getStatus();
        this.message = updateOrderResponse.getMessage();
    }

    public OrderResponse(DeleteOrderResponse deleteOrderResponse) {
        this.status = deleteOrderResponse.getStatus();
        this.message = deleteOrderResponse.getMessage();
    }

    public String getMessage() {
        return message;
    }
    void setMessage(String message) { this.message = message; }

    public String getStatus() {
        return status;
    }
    void setStatus(String status) { this.status = status; }


    public Order getOrder() {
        return this.order;
    }

    public static OrderResponse emptyResponse() {
        return emptyResponse("", "");
    }

    public static OrderResponse emptyResponse(String status, String message) {
        OrderResponse response = new OrderResponse();
        response.setStatus(status);
        response.setMessage(message);
        return response;
    }

}
