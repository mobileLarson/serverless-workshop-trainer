package de.openknowledge.workshop.serverless.cs.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class MaxCapacityConfig {

    public static final String PK_VALUE = "config";

    private String pk;
    Integer maxCapacity;

    public MaxCapacityConfig() {
        // empty by default
    }

    public MaxCapacityConfig(Integer maxCapacity) {
        this.pk = PK_VALUE;
        this.maxCapacity = maxCapacity;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("PK")
    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    @DynamoDbAttribute("maxCapacity")
    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

}
