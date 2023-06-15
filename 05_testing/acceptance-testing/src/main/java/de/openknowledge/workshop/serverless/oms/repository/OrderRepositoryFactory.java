package de.openknowledge.workshop.serverless.oms.repository;

public class OrderRepositoryFactory {

    public static OrderRepository getOrderRepository(OrderRepositoryType orderRepositoryType) {
        switch (orderRepositoryType) {
            case DYNAMODB:
                return new DynamoDbOrderRepository();
            case DYNAMODB_LOCAL:
                return new DynamoDbLocalOrderRepository();
            case DYNAMODB_ENHANCED:
                return new DynamoDbEnhancedOrderRepository();
            case MOCK:
                return new MockOrderRepository();
            default:
                return new DynamoDbOrderRepository();
        }
    }
}
