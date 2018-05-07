package org.it611.das.domain;

/**
 * 用来先对申请者进行登记
 */
public class User {

    private String id;//身份证号

    private String name;

    private String sex;

    private String nation;

    private String date;

    private String address;

    private String submitTime;

    private int state;

    private String files;//如果有多个files，用；进行分割

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public User(String id, String name, String sex, String nation, String date, String address, String submitTime, int state, String files) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.nation = nation;
        this.date = date;
        this.address = address;
        this.submitTime = submitTime;
        this.state = state;
        this.files = files;
    }
}
