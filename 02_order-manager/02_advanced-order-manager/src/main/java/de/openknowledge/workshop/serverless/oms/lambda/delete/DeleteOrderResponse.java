package de.openknowledge.workshop.serverless.oms.lambda.delete;

public class DeleteOrderResponse {

    private String status;
    private String message;

    public DeleteOrderResponse() {
        // empty by default
    }

    public DeleteOrderResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() { return status;}

    public void setStatus(String status) {this.status = status;}

    public String getMessage() { return message;}

    public void setMessage(String message) {this.message = message;}


    public static DeleteOrderResponse emptyResponse() {
        return emptyResponse("", "");
    }

    public static DeleteOrderResponse emptyResponse(String status, String message) {
        DeleteOrderResponse response = new DeleteOrderResponse();
        response.setStatus(status);
        response.setMessage(message);
        return response;
    }
}
