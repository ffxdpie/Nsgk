package com.fx.nsgk.Response;
public class UserRequest {
    private String name;
    private String password;
    private String role;
    private String Position;//家庭住址

    private String phone_number;
    private String birth_date;



    // 构造函数、getter 和 setter
    public UserRequest(String name, String password , String role, String position, String phoneNumber, String birthDate) {
        this.name = name;
        this.password = password;
        this.role = role;
        this.Position = position;
        this.phone_number = phoneNumber;
        this.birth_date = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }
}

