package org.it611.das.domain;

public class Company {

    private String id;//编号/统一社会信用代码

    private String name;//公司名字

    private String type;//类型

    private String address;//地址

    private String legalRepresentative;//法人代表

    private String registeredCapital;//注册资本

    private String startTime;//起始年限

    private String endTime;//终止年限

    private String scopeOfBusiness;//经营范围

    private String registrationAuthority;//登记机关

    private String auditTime;//审核时间（右下角那个）

    private String submitTime;//提交时间

    private int state;//状态


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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLegalRepresentative() {
        return legalRepresentative;
    }

    public void setLegalRepresentative(String legalRepresentative) {
        this.legalRepresentative = legalRepresentative;
    }

    public String getRegisteredCapital() {
        return registeredCapital;
    }

    public void setRegisteredCapital(String registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getScopeOfBusiness() {
        return scopeOfBusiness;
    }

    public void setScopeOfBusiness(String scopeOfBusiness) {
        this.scopeOfBusiness = scopeOfBusiness;
    }

    public String getRegistrationAuthority() {
        return registrationAuthority;
    }

    public void setRegistrationAuthority(String registrationAuthority) {
        this.registrationAuthority = registrationAuthority;
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
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


    public Company(String id, String name, String type, String address, String legalRepresentative, String registeredCapital, String startTime, String endTime, String scopeOfBusiness, String registrationAuthority, String auditTime, String submitTime, int state) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.address = address;
        this.legalRepresentative = legalRepresentative;
        this.registeredCapital = registeredCapital;
        this.startTime = startTime;
        this.endTime = endTime;
        this.scopeOfBusiness = scopeOfBusiness;
        this.registrationAuthority = registrationAuthority;
        this.auditTime = auditTime;
        this.submitTime = submitTime;
        this.state = state;
    }
}
