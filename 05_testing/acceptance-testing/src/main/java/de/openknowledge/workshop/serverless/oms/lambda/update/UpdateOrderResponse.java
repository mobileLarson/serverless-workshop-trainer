package de.openknowledge.workshop.serverless.oms.lambda.update;

import de.openknowledge.workshop.serverless.oms.model.Order;

public class UpdateOrderResponse {

    private String status;
    private String message;
    private Order order;

    public UpdateOrderResponse() {
    }

    public UpdateOrderResponse(Order order, String status, String message) {
        this.status = status;
        this.message = message;
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getStatus() { return status;}

    public void setStatus(String status) {this.status = status;}

    public String getMessage() { return message;}

    public void setMessage(String message) {this.message = message;}

    public static UpdateOrderResponse emptyResponse() {
        return emptyResponse("", "");
    }

    public static UpdateOrderResponse emptyResponse(String status, String message) {
        UpdateOrderResponse response = new UpdateOrderResponse();
        response.setStatus(status);
        response.setMessage(message);
        return response;
    }

}
