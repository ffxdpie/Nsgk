package com.fx.nsgk.Response;
/**
 * ToolRequest 类表示工具请求的详细信息。
 */
public class ToolRequest {

    private String name;
    private String description;
    private String setup_time;
    private String expiry_time;

    private String model;
    private String quantity;

    /**
     * 工具请求构造函数
     *
     * @param name        工具名称
     * @param description 工具描述
     * @param setup_time  设置时间
     * @param expiry_time 过期时间
     * @param model       工具型号
     * @param quantity    工具数量
     */

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
