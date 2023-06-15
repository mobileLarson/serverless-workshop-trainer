package de.openknowledge.workshop.serverless.cs.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import de.openknowledge.workshop.serverless.cs.model.MenuEntry;
import de.openknowledge.workshop.serverless.cs.service.MenuConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

// de.openknowledge.workshop.serverless.cs.lambda.MenuItemsHandler::handleRequest
public class MenuItemsHandler implements RequestHandler<MenuItemsRequest, MenuItemsResponse> {

    private static final Logger logger = LoggerFactory.getLogger(MenuItemsHandler.class);

    /**
     *
     * @param menuItemsRequest
     * @param context
     * @return
     */
    public MenuItemsResponse handleRequest(MenuItemsRequest menuItemsRequest, Context context) {

        if (menuItemsRequest.isValid()) {

            logger.info("Retrieve menu items via 'MenuConfigService' ...");

            List<MenuEntry> menuItemList = MenuConfigService.getMenuEntries();
            logger.info(String.format("Retrieve menu items via 'MenuConfigService' ... %n items", menuItemList.size()));
            logger.info("Retrieve menu items via 'MenuConfigService' ... create response.");

            MenuItemsResponse response = new MenuItemsResponse(menuItemList);
            logger.info("Retrieve menu items via 'MenuConfigService' ...DONE");

            return response;
        } else {
            throw new IllegalArgumentException("ERROR: invalid request object.");
        }

    }

}