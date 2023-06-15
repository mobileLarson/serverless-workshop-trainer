package de.openknowledge.workshop.serverless.oms.model;

import software.amazon.awssdk.enhanced.dynamodb.extensions.annotations.DynamoDbAtomicCounter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class OrderNo {

    private String pk;
    private Long currentValue = 0L;

    public OrderNo() {
    }

    public OrderNo(String pk, Long currentValue) {
        this.pk = pk;
        this.currentValue = currentValue;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("PK")
    public String getPk() {
        return pk;
    }
    public void setPk(String pk) { this.pk = pk;}

    @DynamoDbAttribute("currentValue")
    @DynamoDbAtomicCounter
    public Long getCurrentValue() { return currentValue; }
    // public void setCurrentValue(Long currentValue) { this.currentValue = currentValue; }
}
