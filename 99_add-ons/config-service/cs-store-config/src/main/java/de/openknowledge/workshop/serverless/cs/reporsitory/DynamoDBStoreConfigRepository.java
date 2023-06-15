package de.openknowledge.workshop.serverless.cs.reporsitory;
import de.openknowledge.workshop.serverless.cs.model.MaxCapacityConfig;
import de.openknowledge.workshop.serverless.cs.model.StoreOpenConfig;

import de.openknowledge.workshop.serverless.util.AwsClientProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

public class DynamoDBStoreConfigRepository implements StoreConfigRepository {

    private static final Logger logger = LoggerFactory.getLogger(DynamoDBStoreConfigRepository.class);

    // image table name
    public static final String CONFIG_TABLE_NAME = "sw-config-table";

    // dynamo db client
    public static final DynamoDbEnhancedClient DDB_ENH_CLIENT = AwsClientProvider.getDynamoDBEnhancedClient();

    @Override
    public StoreOpenConfig getStoreOpenConfig() {
        return getStoreOpenConfigItemFromTable(DDB_ENH_CLIENT, StoreOpenConfig.PK_VALUE);
    }

    @Override
    public MaxCapacityConfig getMaxCapacityConfig() {
        return getMaxCapacityConfigItemFromTable(DDB_ENH_CLIENT, StoreOpenConfig.PK_VALUE);
    }

    private static MaxCapacityConfig getMaxCapacityConfigItemFromTable(DynamoDbEnhancedClient ddb, String key) {

        DynamoDbTable<MaxCapacityConfig> configTable = ddb.table(CONFIG_TABLE_NAME, TableSchema.fromBean(MaxCapacityConfig.class));
        MaxCapacityConfig maxCapacityConfig;
        try {
            maxCapacityConfig = configTable.getItem(Key.builder()
                    .partitionValue(key)     // partition key
                    .build());
            logger.info(String.format("%s: item retrieved (%d)", CONFIG_TABLE_NAME,
                    maxCapacityConfig.getMaxCapacity()));
            return maxCapacityConfig;
        } catch (DynamoDbException ex) {
            logger.info(String.format("%s: error while retrieving item (%s)", CONFIG_TABLE_NAME, key));
            return null;
        }
    }

    private static StoreOpenConfig getStoreOpenConfigItemFromTable(DynamoDbEnhancedClient ddb, String key) {

        DynamoDbTable<StoreOpenConfig> configTable = ddb.table(CONFIG_TABLE_NAME, TableSchema.fromBean(StoreOpenConfig.class));
        StoreOpenConfig storeOpenConfig;
        try {
            storeOpenConfig = configTable.getItem(Key.builder()
                    .partitionValue(key)     // partition key
                    .build());
            logger.info(String.format("%s: item retrieved (%b)", CONFIG_TABLE_NAME,
                    storeOpenConfig.getOpen()));
            return storeOpenConfig;
        } catch (DynamoDbException ex) {
            logger.info(String.format("%s: error while retrieving item (%s)", CONFIG_TABLE_NAME, key));
            return null;
        }
    }
}

