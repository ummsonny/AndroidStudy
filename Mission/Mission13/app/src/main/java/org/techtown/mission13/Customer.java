package org.techtown.mission13;

public class Customer {
    String name;
    String birth;
    String phone;
    int resId;

    public Customer(String name, String birth, String phone, int resId) {
        this.name = name;
        this.birth = birth;
        this.phone = phone;
        this.resId = resId;
    }

    //생성자
    public Customer(String name, String birth, String phone) {
        this.name = name;
        this.birth = birth;
        this.phone = phone;
    }

    //getter/setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }


}
