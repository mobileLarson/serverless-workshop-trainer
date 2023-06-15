package de.openknowledge.workshop.serverless.cs.reporsitory;

import de.openknowledge.workshop.serverless.cs.model.MaxCapacityConfig;
import de.openknowledge.workshop.serverless.cs.model.StoreOpenConfig;

public interface StoreConfigRepository {

    StoreOpenConfig getStoreOpenConfig();
    MaxCapacityConfig getMaxCapacityConfig();

}
