package de.openknowledge.workshop.serverless.oms.lambda.update;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import de.openknowledge.workshop.serverless.oms.lambda.read.ReadOrderResponse;
import de.openknowledge.workshop.serverless.oms.model.Order;
import de.openknowledge.workshop.serverless.oms.model.OrderStatus;
import de.openknowledge.workshop.serverless.oms.service.OrderService;

import static de.openknowledge.workshop.serverless.oms.lambda.OrderResponse.STATUS_ERROR;
import static de.openknowledge.workshop.serverless.oms.lambda.OrderResponse.STATUS_OK;

public class UpdateOrderHandler implements RequestHandler<UpdateOrderRequest, UpdateOrderResponse> {

    private final static String LOG_CONTEXT = "logContext";

    private static final String SUCCESS_MESSAGE_TEMPLATE = "Order successfully updated (orderNo %d)";
    private static final String ERROR_MESSAGE_TEMPLATE = "Error during order update: %s.";

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

        // TODO
        // handle update order request
        // - check if call is valid
        // - call order service to update order
        // - create update order response with updated order and success message
        //
        // BONUS TODO
        // - discuss with your neighbour how to handle errors the best way

        try {
            Order order = OrderService.updateOrder(updateOrderRequest.getOrder());
            return new UpdateOrderResponse(order, STATUS_OK, String.format(SUCCESS_MESSAGE_TEMPLATE, order.getOrderNo()));
        } catch (Exception ex) {
            return UpdateOrderResponse.emptyResponse(STATUS_ERROR, String.format(ERROR_MESSAGE_TEMPLATE, ex.getMessage()));
        }
    }
}