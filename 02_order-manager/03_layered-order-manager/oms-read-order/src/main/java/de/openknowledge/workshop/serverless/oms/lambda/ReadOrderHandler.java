package de.openknowledge.workshop.serverless.oms.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import de.openknowledge.workshop.serverless.oms.model.Order;
import de.openknowledge.workshop.serverless.oms.repository.OrderRepositoryFilter;
import de.openknowledge.workshop.serverless.oms.service.ReadOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

// de.openknowledge.workshop.serverless.oms.lambda.ReadOrderHandler::handleRequest
public class ReadOrderHandler implements RequestStreamHandler {

    private static final Logger logger = LoggerFactory.getLogger(ReadOrderHandler.class);

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(input, Charset.forName("UTF-8")));
        PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(output, Charset.forName("UTF-8"))));

        try {

            ReadOrderRequest readOrderRequest = gson.fromJson(reader, ReadOrderRequest.class);
            logger.info("STREAM TYPE: " + input.getClass().toString());
            logger.info("EVENT TYPE: " + readOrderRequest.getClass().toString());

            String orderId = readOrderRequest.getOrderId();

            if (orderId != null && !orderId.isEmpty()) {

                logger.info(String.format("Read single order with orderNo=%s", orderId));
                Order order = ReadOrderService.readOrder(orderId);
                logger.info("Read single order ... SUCCESS");

                ReadSingleOrderResponse response;

                if (order != null) {
                   response = new ReadSingleOrderResponse(order, "OK", "Order successfully read.");
                } else {
                   response = ReadSingleOrderResponse.emptyResponse("ERROR", String.format("No matching order found for id: %s.", orderId));
                }
                writer.write(gson.toJson(response, ReadSingleOrderResponse.class));

                // previous version without response wrapper object:
                // writer.write(gson.toJson(response, ReadSingleOrderResponse.class));

            } else {
                logger.info(String.format("Read multiple orders with filterType=%s", OrderRepositoryFilter.NO_FILTER));
                List<Order> listOfOrders = ReadOrderService.readOrders(OrderRepositoryFilter.NO_FILTER, Collections.EMPTY_MAP);
                logger.info(String.format("Read multiple orders SUCCESS (%d orders)", listOfOrders.size()));

                ReadMulitpleOrdersResponse response;

                if (listOfOrders != null && !listOfOrders.isEmpty()) {
                    response = new ReadMulitpleOrdersResponse(listOfOrders, "OK", "Orders successfully read.");
                } else {
                    response = ReadMulitpleOrdersResponse.emptyResponse("OK", "No matching order(s) found.");
                }
                writer.write(gson.toJson(response, ReadMulitpleOrdersResponse.class));

                // previous version without response wrapper object:
                // Type listOfOrderObject = new TypeToken<ArrayList<Order>>() {}.getType();
                // writer.write(gson.toJson(listOfOrders, listOfOrderObject));
            }

            if (writer.checkError()) {
                logger.info("WARNING: Writer encountered an error.");
            }
        } catch (IllegalStateException | JsonSyntaxException exception) {
            logger.info(exception.toString());
        } finally {
            reader.close();
            writer.close();
        }
    }
}