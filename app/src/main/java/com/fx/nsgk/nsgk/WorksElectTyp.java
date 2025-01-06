package com.fx.nsgk.nsgk;

public class WorksElectTyp {
    public int rcdistance;
    public int otype;
    public int cwtype;
    public int rtype;

    // 新添加的成员变量
    public String rcdistancetype;
    public String otypetpye;
    public String cwtypetype;
    public String  rtypetype;

    public  WorksElectTyp(int rcdistance, int otype, int cwtype, int rtype) {
        this.rcdistance = rcdistance;
        this.otype = otype;
        this.cwtype = cwtype;
        this.rtype = rtype;

        // 初始化新的成员变量
        // rcdistance 配重伸出距离
        // Otype 支腿类型
        // Cwtype 配重类型
        // Rtype  回转类型
        this.rcdistancetype = getrcdistancetype(rcdistance);  // 配重伸出距离
        this.otypetpye = getotype(otype);  // 支腿类型
        this.cwtypetype = getcwtype(cwtype);  // 配重类型
        this. rtypetype = getrtype(rtype);  // 回转类型
    }

    public String getcwtype(int code) {
        switch (code) {
            case 2:
                return "2";
            case 1:
                return "1";
            default:
                return "-1";
        }
    }

    public String getrtype(int code) {
        switch (code) {
            case 2:
                return "2";
            case 1:
                return "0";
            default:
                return "-1";
        }
    }

    public String getotype(int code) {
        switch (code) {
            case 1:
                return "0";
            case 2:
                return "1";
            case 3:
                return "2";
            case 4:
                return "3";
            case 5:
                return "4";
            case 6:
                return "5";
            case 7:
                return "6";
            case 8:
                return "7";
            case 9:
                return "8";
            case 10:
                return "9";
            default:
                return "-1";
        }
    }

    public String getrcdistancetype(int code) {
        switch (code) {
            case 1:
                return "7";
            case 2:
                return "10";
            case 3:
                return "12";
            default:
                return "-1";
        }
    }
}
