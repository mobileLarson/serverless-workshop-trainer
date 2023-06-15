package de.openknowledge.workshop.serverless.cs.reporsitory;

import de.openknowledge.workshop.serverless.cs.model.MenuConfig;
import de.openknowledge.workshop.serverless.util.AwsClientProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

import java.util.Collections;

public class DynamoDBMenuConfigRepository implements MenuConfigRepository {

    private static final Logger logger = LoggerFactory.getLogger(DynamoDBMenuConfigRepository.class);

    // image table name
    public static final String CONFIG_TABLE_NAME = "sw-config-table";

    // dynamo db client
    public static final DynamoDbEnhancedClient DDB_ENH_CLIENT = AwsClientProvider.getDynamoDBEnhancedClient();

    @Override
    public MenuConfig getMenuConfig() {
        return getMenuConfigItemFromTable(DDB_ENH_CLIENT, MenuConfig.PK_VALUE);
    }

    private static MenuConfig getMenuConfigItemFromTable(DynamoDbEnhancedClient ddb, String key) {

        DynamoDbTable<MenuConfig> configTable = ddb.table(CONFIG_TABLE_NAME, TableSchema.fromBean(MenuConfig.class));
        MenuConfig menuConfig;
        try {
            menuConfig = configTable.getItem(Key.builder()
                    .partitionValue(key)     // partition key
                    .build());
            logger.debug(menuConfig.toString());
            return menuConfig;
        } catch (DynamoDbException ex) {
            logger.error(String.format("%s: error while retrieving item (%s)", CONFIG_TABLE_NAME, key));
            ex.printStackTrace();
            return new MenuConfig(Collections.emptyList());
        }
    }
}

