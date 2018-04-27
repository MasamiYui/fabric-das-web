package org.it611.das.domain;

public class StudentIdCardAsset{

    private String id;

    private String assetId;

    private String stuId;//学生证Id

    private String school;//学校

    private String name;//学生姓名

    private String sex;//学生性别

    private String dataOfBirth;//出生日期

    private String idCardNo;//身份证Id

    private String lengthOfSchooling;//学制

    private String college;//学院

    private String address;//地址

    private String schoolTime;//入校时间

    private String timeOfIssume;//发证时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
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

    public String getDataOfBirth() {
        return dataOfBirth;
    }

    public void setDataOfBirth(String dataOfBirth) {
        this.dataOfBirth = dataOfBirth;
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

    public StudentIdCardAsset(String id, String assetId, String stuId, String school, String name, String sex, String dataOfBirth, String idCardNo, String lengthOfSchooling, String college, String address, String schoolTime, String timeOfIssume) {
        this.id = id;
        this.assetId = assetId;
        this.stuId = stuId;
        this.school = school;
        this.name = name;
        this.sex = sex;
        this.dataOfBirth = dataOfBirth;
        this.idCardNo = idCardNo;
        this.lengthOfSchooling = lengthOfSchooling;
        this.college = college;
        this.address = address;
        this.schoolTime = schoolTime;
        this.timeOfIssume = timeOfIssume;
    }
}
