package org.it611.das.vo;

import org.it611.das.domain.BaseAsset;

public class CollegeEducationAssetVO extends BaseAssetVO{

    private String name;//姓名

    private String sex;//性别

    private String birthOfDate;//出生日期

    private String educationType;//学历类别

    private String school;//院校名称

    private String major;//专业名称

    private String learnType;//学习形式

    private String lenOfSchooling;//学制

    private String dateOfAttendance;//入学日期

    private String dateOfGraduation;//毕业日期

    private String bjy;//毕业结业

    private String  certificateNum;//证书编号

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getBirthOfDate() {
        return birthOfDate;
    }

    public String getEducationType() {
        return educationType;
    }

    public String getSchool() {
        return school;
    }

    public String getMajor() {
        return major;
    }

    public String getLearnType() {
        return learnType;
    }

    public String getLenOfSchooling() {
        return lenOfSchooling;
    }

    public String getDateOfAttendance() {
        return dateOfAttendance;
    }

    public String getDateOfGraduation() {
        return dateOfGraduation;
    }

    public String getBjy() {
        return bjy;
    }

    public String getCertificateNum() {
        return certificateNum;
    }


    public CollegeEducationAssetVO(String id, String type, String title, String assetDes, String submitTime, int state, String name, String sex, String birthOfDate, String educationType, String school, String major, String learnType, String lenOfSchooling, String dateOfAttendance, String dateOfGraduation, String bjy, String certificateNum) {
        super(id, type, title, assetDes, submitTime, state);
        this.name = name;
        this.sex = sex;
        this.birthOfDate = birthOfDate;
        this.educationType = educationType;
        this.school = school;
        this.major = major;
        this.learnType = learnType;
        this.lenOfSchooling = lenOfSchooling;
        this.dateOfAttendance = dateOfAttendance;
        this.dateOfGraduation = dateOfGraduation;
        this.bjy = bjy;
        this.certificateNum = certificateNum;
    }
}
