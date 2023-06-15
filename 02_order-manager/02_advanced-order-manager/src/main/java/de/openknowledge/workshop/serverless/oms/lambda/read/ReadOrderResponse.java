package de.openknowledge.workshop.serverless.oms.lambda.read;

import de.openknowledge.workshop.serverless.oms.lambda.delete.DeleteOrderResponse;
import de.openknowledge.workshop.serverless.oms.lambda.update.UpdateOrderResponse;
import de.openknowledge.workshop.serverless.oms.model.Order;

public class ReadOrderResponse {

    private String status;
    private String message;
    private Order order;

    public ReadOrderResponse() {
    }

    public ReadOrderResponse(Order order, String status, String message) {
        this.status = status;
        this.message = getMessage();
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() { return message;}

    public void setMessage(String message) {this.message = message;}

    public static ReadOrderResponse emptyResponse() {
        return emptyResponse("", "");
    }

    public static ReadOrderResponse emptyResponse(String status, String message) {
        ReadOrderResponse response = new ReadOrderResponse();
        response.setStatus(status);
        response.setMessage(message);
        return response;
    }

}
