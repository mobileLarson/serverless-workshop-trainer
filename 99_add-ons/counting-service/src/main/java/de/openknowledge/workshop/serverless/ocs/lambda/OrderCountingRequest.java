package de.openknowledge.workshop.serverless.ocs.lambda;

public class OrderCountingRequest {

    private String action;
    private String value;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}