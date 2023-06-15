package de.openknowledge.workshop.serverless.ocs.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static de.openknowledge.workshop.serverless.ocs.service.OrderCountingService.*;

public class OrderCountingHandler implements RequestHandler<OrderCountingRequest, OrderCountingResponse> {

    private static final Logger logger = LoggerFactory.getLogger(OrderCountingHandler.class);


    @Override
    public OrderCountingResponse handleRequest(OrderCountingRequest orderCountingRequest, Context context) {

        Long returnCounter = 0L;

        OrderCountingAction action = OrderCountingAction.valueOf(orderCountingRequest.getAction());
        Integer value = Integer.valueOf(orderCountingRequest.getValue());

        logger.info(String.format("handle order counting request (%s with value %d)", action, value));

        switch (action) {
            case INIT: init(value);
                returnCounter = (long)value;
                break;
            case INCREMENT:
                returnCounter = increment(value);
                break;
            case DECREMENT:
                returnCounter = decrement(value);
                break;
            default:
                returnCounter = 0L;
        }
        return new OrderCountingResponse(returnCounter);
    }
}
