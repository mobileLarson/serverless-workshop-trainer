package de.openknowledge.workshop.serverless.oms.lambda.read;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import de.openknowledge.workshop.serverless.oms.model.Order;
import de.openknowledge.workshop.serverless.oms.service.OrderService;

public class ReadOrderHandler implements RequestHandler<ReadOrderRequest, ReadOrderResponse> {

    /**
     * Creates an order response with the help of a given <code>OrderRequest</code>
     * representing the ordering user and the drink to create.
     *
     * @param readOrderRequest order event (userId, drink)
     * @param context          aws lambda context
     * @return order response wrapping the created order
     */
    public ReadOrderResponse handleRequest(ReadOrderRequest readOrderRequest, Context context) {

        ReadOrderResponse response;

        try {
            String orderId = readOrderRequest.getOrderId();
            Order order = OrderService.readOrder(orderId);
            return new ReadOrderResponse(order, "OK", "Order successfully read.");
        } catch (Exception ex) {
            // TODO should be lambda exception
            // response is already set to LAMBDA_EXCEPTION_GREET
            // any additional action required?
            // WHAT TO RETURN?
            return ReadOrderResponse.emptyResponse("ERROR", String.format("Error during order retrieval: %s.", ex.getMessage()));
        }
    }

}
