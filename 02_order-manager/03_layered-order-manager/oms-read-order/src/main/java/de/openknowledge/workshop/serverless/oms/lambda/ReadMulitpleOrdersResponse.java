package de.openknowledge.workshop.serverless.oms.lambda;

import de.openknowledge.workshop.serverless.oms.model.Order;

import java.util.Collections;
import java.util.List;

public class ReadMulitpleOrdersResponse {

    private String status;
    private String message;
    private int orderCount;
    private List<Order> orders;

    public ReadMulitpleOrdersResponse() {
    }

    public ReadMulitpleOrdersResponse(List<Order> orders, String status, String message) {
        this.status = status;
        this.message = message;
        this.orders = orders;
        if (orders != null) {
            this.orderCount = orders.size();
        } else {
            this.orderCount = 0;
        }
    }

    public int getOrderCount() {
        return this.orderCount;
    }

    public List<Order>  getOrders() {
        return orders;
    }
    public void setOrders(List<Order>  orders) {
        this.orders = orders;
        if ( this.orders != null) {
            this.orderCount =  this.orders.size();
        } else {
            this.orderCount = 0;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() { return message;}

    public void setMessage(String message) {this.message = message;}

    public static ReadMulitpleOrdersResponse emptyResponse() {
        return emptyResponse("", "");
    }

    public static ReadMulitpleOrdersResponse emptyResponse(String status, String message) {
        ReadMulitpleOrdersResponse response = new ReadMulitpleOrdersResponse();
        response.setStatus(status);
        response.setMessage(message);
        response.setOrders(Collections.emptyList());
        return response;
    }

}
