package de.openknowledge.workshop.serverless.oms.lambda;

import de.openknowledge.workshop.serverless.oms.model.Order;

public class OrderRequest {

    private String action;
    private Order order;

    public OrderRequest() {
        // empty by default
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Order getOrder() { return this.order; }
    public String getAction() { return this.action; }

}
