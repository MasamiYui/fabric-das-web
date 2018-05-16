package org.it611.das.domain;

public class Company {
    private String id;

    private String username;

    private String password;

    //company information
    private String companyName;

    private String companyAddress;

    private String companyEmail;

    private String landinePhone;//固话

    private String contact;//联系人

    private String phone;//手机

    private String creditId;//统一社会信用代码

    //yyzz
    private String companyType;

    private String representative;//法定代表

    private String establishmentTime;//成立时间

    private String startTime;

    private String endTime;

    private String businessScope;

    private String registrationAuthority;//登记机关

    private String registrationTime;

    private String files;//文件

    private int state;//状态

    private String submitTime;//提交时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getLandinePhone() {
        return landinePhone;
    }

    public void setLandinePhone(String landinePhone) {
        this.landinePhone = landinePhone;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getRepresentative() {
        return representative;
    }

    public void setRepresentative(String representative) {
        this.representative = representative;
    }

    public String getEstablishmentTime() {
        return establishmentTime;
    }

    public void setEstablishmentTime(String establishmentTime) {
        this.establishmentTime = establishmentTime;
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

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public String getRegistrationAuthority() {
        return registrationAuthority;
    }

    public void setRegistrationAuthority(String registrationAuthority) {
        this.registrationAuthority = registrationAuthority;
    }

    public String getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(String registrationTime) {
        this.registrationTime = registrationTime;
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

    public Company(String id, String username, String password, String companyName, String companyAddress, String companyEmail, String landinePhone, String contact, String phone, String creditId, String companyType, String representative, String establishmentTime, String startTime, String endTime, String businessScope, String registrationAuthority, String registrationTime, String files, int state, String submitTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.companyEmail = companyEmail;
        this.landinePhone = landinePhone;
        this.contact = contact;
        this.phone = phone;
        this.creditId = creditId;
        this.companyType = companyType;
        this.representative = representative;
        this.establishmentTime = establishmentTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.businessScope = businessScope;
        this.registrationAuthority = registrationAuthority;
        this.registrationTime = registrationTime;
        this.files = files;
        this.state = state;
        this.submitTime = submitTime;
    }
}
