package com.fx.nsgk.Response;

public class ToolResponse {
    private String name;
    private String description;
    private String setup_time;

    private String expiry_time;
    private boolean is_active;

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

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }
}
