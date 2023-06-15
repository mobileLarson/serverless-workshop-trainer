package de.openknowledge.workshop.serverless.oms.lambda.delete;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import de.openknowledge.workshop.serverless.oms.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteOrderHandler implements RequestHandler<DeleteOrderRequest, DeleteOrderResponse> {

    private static final Logger logger = LoggerFactory.getLogger(DeleteOrderHandler.class);


    /**
     * Deletes an order with the help of a given <code>DeleteOrderRequest</code>
     * representing the ordering user and the drink to create.
     *
     * @param deleteOrderRequest order event (userId, drink)
     * @param context            aws lambda context
     * @return order response wrapping the created order
     */
    public DeleteOrderResponse handleRequest(DeleteOrderRequest deleteOrderRequest, Context context) {

        DeleteOrderResponse response;

        try {
            String orderId = deleteOrderRequest.getOrderId();
            OrderService.deleteOrder(orderId);
            return new DeleteOrderResponse("OK", "Order successfully deleted.");
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage());
            return DeleteOrderResponse.emptyResponse("ERROR", String.format("Error during order deletion: %s.", ex.getMessage()));
        }
    }
}