package de.openknowledge.workshop.serverless.model;

public class Order {

    private String orderId;
    private Long orderNo;
    private String orderStatus;
    private String userId;
    private String drink;

    public Order(String orderId, Long orderNo, String orderStatus, String userId, String baristaId, String drink) {
        this.orderId = orderId;
        this.orderNo = orderNo;
        this.orderStatus = orderStatus;
        this.userId = userId;
        this.drink = drink;
    }

    public Order() {
        // default constructor
    }

    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }


    public String getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDrink() {
        return drink;
    }
    public void setDrink(String drink) {
        this.drink = drink;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", userId='" + userId + '\'' +
                ", drink='" + drink + '\'' +
                '}';
    }
}
