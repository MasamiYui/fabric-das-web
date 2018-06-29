package org.it611.das.vo;

/**
 * VO资产基类
 */
public class BaseAssetVO {

    private String id;//资产id

    private String type;//类型

    private String title;//资产标题

    private String des;//资产描述

    private String submitTime;//提交时间

    private int state;//状态

    public BaseAssetVO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
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

    public BaseAssetVO(String id, String type, String title, String assetDes, String submitTime, int state) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.des = des;
        this.submitTime = submitTime;
        this.state = state;
    }

}
