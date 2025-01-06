package com.fx.nsgk.Response;

public class ToolRequest {
    private String name;
    private String description;
    private String setup_time;
    public ToolRequest(String name, String description ,String setup_time) {
        this.name = name;
        this.description = description;
        this.setup_time = setup_time;
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
}
