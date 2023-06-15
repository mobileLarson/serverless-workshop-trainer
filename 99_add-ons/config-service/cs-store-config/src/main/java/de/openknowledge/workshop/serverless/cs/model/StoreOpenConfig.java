package de.openknowledge.workshop.serverless.cs.model;


import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class StoreOpenConfig {

    public static final String PK_VALUE= "config";

    private String pk;
    boolean open;

    public StoreOpenConfig() {
        // empty by default
    }

    public StoreOpenConfig(Boolean open) {
        this.pk = PK_VALUE;
        this.open = open;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("PK")
    public String getPk() {
        return pk;
    }
    public void setPk(String pk) {
        this.pk = pk;
    }

    @DynamoDbAttribute("open")
    // get open is needed for aws framework to work!
    public boolean getOpen() {
        return open;
    }
    public void setOpen(boolean open) {
        this.open = open;
    }

}
