package com.fx.nsgk.Response;

public class ToolRequest {
    private String name;
    private String description;
    private String setup_time;
    private String expiry_time;

    private String model;
    private String quantity;

    public ToolRequest(String name, String description , String setup_time, String expiry_time, String model, String quantity) {
        this.name = name;
        this.description = description;
        this.setup_time = setup_time;
        this.expiry_time = expiry_time;

        this.model = model;
        this.quantity = quantity;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSetup_time() {
        return setup_time;
    }

    public void setSetup_time(String setup_time) {
        this.setup_time = setup_time;
    }

    public String getExpiry_time() {
        return expiry_time;
    }

    public void setExpiry_time(String expiry_time) {
        this.expiry_time = expiry_time;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
