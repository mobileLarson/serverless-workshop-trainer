package de.openknowledge.workshop.serverless.cs.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import de.openknowledge.workshop.serverless.cs.service.StoreConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MaxCapacityHandler implements RequestHandler<MaxCapacityRequest, MaxCapacityResponse> {

    private static final Logger logger = LoggerFactory.getLogger(MaxCapacityHandler.class);

    /**
     * @param maxCapacityRequest
     * @param context
     * @return
     */
    public MaxCapacityResponse handleRequest(MaxCapacityRequest maxCapacityRequest, Context context) {

        try {
            Integer maxCapacity = StoreConfigService.maxCapacity();
            return new MaxCapacityResponse(maxCapacity);
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage());
            ex.printStackTrace();
            return new MaxCapacityResponse(0);
        }
    }
}
