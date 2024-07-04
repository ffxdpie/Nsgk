package com.fx.nsgk;

public class Workingconditiontype {
    public int rcdistance;
    public int otype;
    public int cwtype;
    public int rtype;

    // 新添加的成员变量
    public String rotationDescription;
    public String counterweightDescription;
    public String outriggerDescription;
    public String counterweightReach;

    public Workingconditiontype(int rcdistance, int otype, int cwtype, int rtype) {
        this.rcdistance = rcdistance;
        this.otype = otype;
        this.cwtype = cwtype;
        this.rtype = rtype;

        // 初始化新的成员变量
        this.rotationDescription = getfirst(cwtype);  // 代表回转信息
        this.counterweightDescription = getsecond(rtype);  // 代表配重信息
        this.outriggerDescription = getthird(otype);  // 支腿信息
        this.counterweightReach = getfourth(rcdistance);  // 配重伸出信息
    }

    public String getfirst(int code) {
        switch (code) {
            case 2:
                return "回转类型:      360°回转";
            case 1:
                return "回转类型:      30度摆动";
            default:
                return "回转类型:      未知回转";
        }
    }

    public String getsecond(int code) {
        switch (code) {
            case 2:
                return "配重类型:      两块配重";
            case 0:
                return "配重类型:      一块配重";
            default:
                return "配重类型:      错误";
        }
    }

    public String getthird(int code) {
        switch (code) {
            case 0:
                return "支腿类型:      无支腿";
            case 1:
                return "支腿类型:      单个支腿伸出 3.90米";
            case 2:
                return "支腿类型:      单个支腿伸出 3.0米-3.9米";
            case 3:
                return "支腿类型:      单个支腿伸出 2.0-3.0米";
            case 4:
                return "支腿类型:      单侧支腿伸出 3.90米";
            case 5:
                return "支腿类型:      单侧支腿伸出 3.0米-3.9米";
            case 6:
                return "支腿类型:      单侧支腿伸出 2.0-3.0米";
            case 7:
                return "支腿类型:      全部支腿伸出 6米";
            case 8:
                return "支腿类型:      全部支腿伸出 6.0 - 7.8米";
            case 9:
                return "支腿类型:      全部支腿伸出 4.0 - 6.0米";
            default:
                return "配重类型:         错误";
        }
    }

    public String getfourth(int code) {
        switch (code) {
            case 7:
                return "配重伸出距离: 7米";
            case 10:
                return "配重伸出距离: 10米";
            case 12:
                return "配重伸出距离: 12米";
            default:
                return "配重伸出距离: 错误";
        }
    }
}
