/*
 * Copyright (C) open knowledge GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package de.openknowledge.workshop.serverless.cs.service;


import de.openknowledge.workshop.serverless.cs.model.MaxCapacityConfig;
import de.openknowledge.workshop.serverless.cs.model.StoreOpenConfig;
import de.openknowledge.workshop.serverless.cs.reporsitory.DynamoDBStoreConfigRepository;
import de.openknowledge.workshop.serverless.cs.reporsitory.StoreConfigRepository;

/**
 * Service to create an order
 */
public class StoreConfigService {

    private static final StoreConfigRepository repository = new DynamoDBStoreConfigRepository();

    public static Boolean isOpen() {
        StoreOpenConfig storeOpenConfig = repository.getStoreOpenConfig();
        return storeOpenConfig.getOpen();
    }

    public static Integer maxCapacity() {
        MaxCapacityConfig maxCapacityConfig = repository.getMaxCapacityConfig();
        return maxCapacityConfig.getMaxCapacity();
    }

    /*
    public static void main(String[] args) {
        System.out.println("Is open? ..." + isOpen());
        System.out.println("Max capacity? ..." + maxCapacity());
    }
    */



}