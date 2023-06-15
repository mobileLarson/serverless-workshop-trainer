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
package de.openknowledge.workshop.serverless.cs.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import de.openknowledge.workshop.serverless.cs.service.StoreConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AWS lambda creating an order with the help of a given event
 */
// de.openknowledge.workshop.serverless.oms.lambda.IsStoreOpenHandler::handleRequest
public class IsStoreOpenHandler implements RequestHandler<IsStoreOpenRequest, IsStoreOpenResponse> {

    private static final Logger logger = LoggerFactory.getLogger(IsStoreOpenHandler.class);

    /**
     *
     * @param isStoreOpenRequest
     * @param context
     * @return
     */
    public IsStoreOpenResponse handleRequest(IsStoreOpenRequest isStoreOpenRequest, Context context) {

        try {
            Boolean isStoreOpen = StoreConfigService.isOpen();
            return new IsStoreOpenResponse(isStoreOpen);
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage());
            ex.printStackTrace();
            return new IsStoreOpenResponse(Boolean.FALSE);

        }
    }

}