package org.it611.das.vo;

public class StudentIdCardAssetVO extends BaseAssetVO {

    //学生证描述
    private String stuId;//身份证Id

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

    //资产拥有者描述
    private String owners;

    //电子文件
    private String files;

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
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

    public String getOwners() {
        return owners;
    }

    public void setOwners(String owners) {
        this.owners = owners;
    }

    public StudentIdCardAssetVO(String id, String type, String title, String assetDes, String submitTime, int state, String stuId, String school, String name, String sex, String dataOfBirth, String idCardNo, String lengthOfSchooling, String college, String address, String schoolTime, String timeOfIssume, String owners, String files) {
        super(id, type, title, assetDes, submitTime, state);
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
        this.owners = owners;
        this.files = files;
    }

    public StudentIdCardAssetVO() {

    }

}
