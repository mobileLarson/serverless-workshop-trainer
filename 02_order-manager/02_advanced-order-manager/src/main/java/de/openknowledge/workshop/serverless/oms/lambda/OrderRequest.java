package de.openknowledge.workshop.serverless.oms.lambda;

import de.openknowledge.workshop.serverless.oms.model.Order;

/**
 * Wrapper order request.
 */
public class OrderRequest {

    // action to perform
    private String action;

    // order to perform requested action with
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
