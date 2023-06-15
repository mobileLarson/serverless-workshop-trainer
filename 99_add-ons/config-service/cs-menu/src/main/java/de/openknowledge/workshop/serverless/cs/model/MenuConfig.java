package de.openknowledge.workshop.serverless.cs.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.List;

@DynamoDbBean
public class MenuConfig {

    public static final String PK_VALUE = "menu";

    private String pk;
    List<MenuEntry> menuEntryList;

    public MenuConfig() {
        // empty by default
    }

    public MenuConfig(List<MenuEntry> menuEntryList) {
        this.pk = PK_VALUE;
        this.menuEntryList = menuEntryList;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("PK")
    public String getPk() {
        return pk;
    }
    public void setPk(String pk) {
        this.pk = pk;
    }

    @DynamoDbAttribute("entries")
    public List<MenuEntry> getMenuEntries() {
        return menuEntryList;
    }
    public void setMenuEntries(List<MenuEntry> menuEntryList) {
        this.menuEntryList = menuEntryList;
    }

    @Override
    public String toString() {
        return "MenuConfig{" +
                "pk='" + pk + '\'' +
                ", menuEntryList=" + menuEntryList +
                '}';
    }
}
