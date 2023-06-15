package de.openknowledge.workshop.serverless.ocs.lambda;

public class OrderCountingResponse {

    private long counter;

    public OrderCountingResponse() {
        // empty by default
    }

    public OrderCountingResponse(long counter) {
        this.counter = counter;
    }

    public long getCounter() {
        return counter;
    }

    public void setCounter(long counter) {
        this.counter = counter;
    }
}
