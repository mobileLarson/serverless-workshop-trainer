package de.openknowledge.workshop.serverless.oms.service;

import de.openknowledge.workshop.serverless.oms.repository.OrderRepository;
import de.openknowledge.workshop.serverless.oms.repository.OrderRepositoryFactory;
import de.openknowledge.workshop.serverless.oms.repository.OrderRepositoryType;
import de.openknowledge.workshop.serverless.util.AwsClientProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;


// see https://nickolasfisher.com/blog/Atomic-Incrementing-in-DynamoDB-with-the-Java-AWS-SDK-20
public class OrderCounterService {

    private static final Logger logger = LoggerFactory.getLogger(OrderCounterService.class);

    public static final Long next() {
        return ThreadLocalRandom.current().nextLong(100);
    }
}
