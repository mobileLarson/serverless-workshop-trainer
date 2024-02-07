package de.openknowledge.workshop.serverless.util;


import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import static de.openknowledge.workshop.serverless.util.AwsRegionProvider.getDefaultRegion;

public class AwsClientProvider {


    /**
     * Builds an AWS dynamoDb client using the "default" region and
     * "default" profile credential provider
     *
     * @return AWS dynamoDb client
     */
    public static DynamoDbClient getDynamoDbClient() {
        return DynamoDbClient.builder()
                .region(getDefaultRegion())
                .build();
    }

    /**
     * Builds a AWS dynamoDb client using the "default" region
     * and given access key and secret key
     *
     * @param accessKey access key to use to build aws credentials
     * @param secretKey secret key to use to build aws credentials
     * @return AWS dynamoDb client
     */
    public static DynamoDbClient getDynamoDbClientFor(String accessKey, String secretKey) {

        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(credentials);

        return DynamoDbClient
                .builder()
                .region(getDefaultRegion())
                .credentialsProvider(credentialsProvider)
                .build();
    }
}