package de.openknowledge.workshop.serverless.cs.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@DynamoDbBean
public class MenuEntry {

    private boolean available;
    private String drink;
    private String icon;

    public boolean getAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getDrink() {
        return drink;
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "MenuEntry{" +
                "available=" + available +
                ", drink='" + drink + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
