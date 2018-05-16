package org.it611.das.domain;


public class User{

    //common
    private String id;

    private String phone;

    private String email;

    private String password;

    //idcard
    private String idcard;//身份证号

    private String name;

    private String sex;

    private String nation;

    private String date;

    private String address;

    private String files;//附件

    private int state;

    private String submitTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public User(String id, String phone, String email, String password, String idcard, String name, String sex, String nation, String date, String address, String files, int state, String submitTime) {
        this.id = id;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.idcard = idcard;
        this.name = name;
        this.sex = sex;
        this.nation = nation;
        this.date = date;
        this.address = address;
        this.files = files;
        this.state = state;
        this.submitTime = submitTime;
    }

    public User() {

    }
}
