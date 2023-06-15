package de.openknowledge.workshop.serverless.oms.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class Order {

    private String orderId;
    private Long orderNo;
    private String orderStatus;
    private String userId;
    private String baristaId;
    private String drink;

    public Order(String orderId, Long orderNo, String orderStatus, String userId, String baristaId, String drink) {
        this.orderId = orderId;
        this.orderNo = orderNo;
        this.orderStatus = orderStatus;
        this.userId = userId;
        this.baristaId = baristaId;
        this.drink = drink;
    }

    public Order() {
        // default constructor for dynamoDB Schema creation
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("PK")
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @DynamoDbAttribute("orderNo")
    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }


    @DynamoDbAttribute("state")
    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @DynamoDbAttribute("userId")
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @DynamoDbAttribute("barista")
    public String getBaristaId() {
        return baristaId;
    }
    public void setBaristaId(String baristaId) {
        this.baristaId = baristaId;
    }

    @DynamoDbAttribute("drink")
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
                ", baristaId='" + baristaId + '\'' +
                ", drink='" + drink + '\'' +
                '}';
    }
}
