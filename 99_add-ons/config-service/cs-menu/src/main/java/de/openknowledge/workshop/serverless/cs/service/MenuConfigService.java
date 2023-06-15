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


import de.openknowledge.workshop.serverless.cs.model.MenuConfig;
import de.openknowledge.workshop.serverless.cs.model.MenuEntry;
import de.openknowledge.workshop.serverless.cs.reporsitory.DynamoDBMenuConfigRepository;
import de.openknowledge.workshop.serverless.cs.reporsitory.MenuConfigRepository;

import java.util.List;

/**
 * Service to create an order
 */
public class MenuConfigService {

    private static final MenuConfigRepository repository = new DynamoDBMenuConfigRepository();

    public static List<MenuEntry> getMenuEntries() {
        MenuConfig menuConfig = repository.getMenuConfig();
        List<MenuEntry> menuEntryList = menuConfig.getMenuEntries();
        return menuEntryList;
    }


    /**
    public static void main(String[] args) {
        System.out.println("Menu entries:");
        System.out.println(getMenuEntries());
    }
     **/



}