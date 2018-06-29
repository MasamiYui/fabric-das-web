package org.it611.das.vo;

public class UserVo {

    private String id;//身份证作为唯一ID

    private String name;

    private String sex;

    private String nation;

    private String date;

    private String address;

    private String files;

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

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public UserVo(String id, String name, String sex, String nation, String date, String address, String files) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.nation = nation;
        this.date = date;
        this.address = address;
        this.files = files;
    }

    public UserVo(){

    }




}
