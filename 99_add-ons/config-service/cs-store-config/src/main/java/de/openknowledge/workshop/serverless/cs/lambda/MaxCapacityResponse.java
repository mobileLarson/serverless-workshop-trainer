package de.openknowledge.workshop.serverless.cs.lambda;

public class MaxCapacityResponse {

    private Integer maxCapacity;

    public MaxCapacityResponse() {
        // empty by default
    }

    public MaxCapacityResponse(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
}
