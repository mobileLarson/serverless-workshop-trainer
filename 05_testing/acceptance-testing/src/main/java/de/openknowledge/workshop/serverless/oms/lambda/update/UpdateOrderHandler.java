package de.openknowledge.workshop.serverless.oms.lambda.update;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import de.openknowledge.workshop.serverless.oms.model.Order;
import de.openknowledge.workshop.serverless.oms.service.OrderService;

public class UpdateOrderHandler implements RequestHandler<UpdateOrderRequest, UpdateOrderResponse> {

    private final static String LOG_CONTEXT = "logContext";

    /**
     * Creates an order response with the help of a given <code>OrderRequest</code>
     * representing the ordering user and the drink to create.
     *
     * @param updateOrderRequest order event (userId, drink)
     * @param context            aws lambda context
     * @return order response wrapping the created order
     */
    public UpdateOrderResponse handleRequest(UpdateOrderRequest updateOrderRequest, Context context) {

        UpdateOrderResponse response;

        try {
            Order order = OrderService.updateOrder(updateOrderRequest.getOrder());
            return new UpdateOrderResponse(order, "OK", "Order successfully updated.");
        } catch (Exception ex) {
            return UpdateOrderResponse.emptyResponse("ERROR", String.format("Error during order update: %s.", ex.getMessage()));
        }
    }
}