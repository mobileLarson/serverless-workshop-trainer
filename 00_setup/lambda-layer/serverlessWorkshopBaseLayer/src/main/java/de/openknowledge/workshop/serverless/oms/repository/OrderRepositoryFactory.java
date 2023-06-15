package de.openknowledge.workshop.serverless.oms.repository;

public class OrderRepositoryFactory {

    public static OrderRepository getOrderRepository(OrderRepositoryType orderRepositoryType) {
        switch (orderRepositoryType) {
            case DYNAMODB:
                return new DynamoDbOrderRepository();
            case DYNAMODB_ENHANCED:
                return new DynamoDBEnhancedOrderRepository();
            case MOCK:
                return new MockOrderRepository();
            default:
                return new DynamoDbOrderRepository();
        }
    }
}
