package org.it611.das.domain;

public class BaseAsset {

    private String id;//资产id

    private String typeId;//类型

    private String title;//资产标题

    private String des;//资产描述

    private String submitTime;//提交时间

    private int state;//状态

    private String txId;//交易Id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public BaseAsset(String id, String typeId, String title, String des, String submitTime, int state, String txId) {
        this.id = id;
        this.typeId = typeId;
        this.title = title;
        this.des = des;
        this.submitTime = submitTime;
        this.state = state;
        this.txId = txId;
    }
}
