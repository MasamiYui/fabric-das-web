package org.it611.das.domain.fabric;

public class FabricStudentIdCardAsset {

    private String typeId;//类型

    private String title;//资产标题

    private String[] owners;//资产拥有者，可能有多个

    private String des;//资产描述

    private String stuId;//学生证Id

    private String school;//学校

    private String name;//学生姓名

    private String sex;//学生性别

    private String dateOfBirth;//出生日期

    private String idCardNo;//身份证Id

    private String lengthOfSchooling;//学制

    private String college;//学院

    private String address;//地址

    private String schoolTime;//入校时间

    private String timeOfIssume;//发证时间

    private String[] fileHash;//文件Hash，可能有多个

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getOwners() {
        return owners;
    }

    public void setOwners(String[] owners) {
        this.owners = owners;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getLengthOfSchooling() {
        return lengthOfSchooling;
    }

    public void setLengthOfSchooling(String lengthOfSchooling) {
        this.lengthOfSchooling = lengthOfSchooling;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSchoolTime() {
        return schoolTime;
    }

    public void setSchoolTime(String schoolTime) {
        this.schoolTime = schoolTime;
    }

    public String getTimeOfIssume() {
        return timeOfIssume;
    }

    public void setTimeOfIssume(String timeOfIssume) {
        this.timeOfIssume = timeOfIssume;
    }

    public String[] getFileHash() {
        return fileHash;
    }

    public void setFileHash(String[] fileHash) {
        this.fileHash = fileHash;
    }

    public FabricStudentIdCardAsset(String typeId, String title, String[] owners, String des, String stuId, String school, String name, String sex, String dateOfBirth, String idCardNo, String lengthOfSchooling, String college, String address, String schoolTime, String timeOfIssume, String[] fileHash) {
        this.typeId = typeId;
        this.title = title;
        this.owners = owners;
        this.des = des;
        this.stuId = stuId;
        this.school = school;
        this.name = name;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.idCardNo = idCardNo;
        this.lengthOfSchooling = lengthOfSchooling;
        this.college = college;
        this.address = address;
        this.schoolTime = schoolTime;
        this.timeOfIssume = timeOfIssume;
        this.fileHash = fileHash;
    }
}
