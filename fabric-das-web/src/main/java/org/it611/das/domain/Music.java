package org.it611.das.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "music")
public class Music {

    @Id
    private String id;//作品Id 资产Id

    private String title;//作品名称----  歌曲：

    private String des;//作品描述 ---自己描述一下这个作品

    private String ownerId;//著作权人的Id（申请人）

    private String author;//创作者（作者），可以是几个，用，隔开-------小明 小红

    private String files;//文件url  ，隔开

    private String filesHash;//文件hash  ，隔开

    private String transactionId;//交易号

    private String submitTime;//提交时间

    private String state;//状态

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getFilesHash() {
        return filesHash;
    }

    public void setFilesHash(String filesHash) {
        this.filesHash = filesHash;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Music(String id, String title, String des, String ownerId, String author, String files, String filesHash, String transactionId, String submitTime, String state) {
        this.id = id;
        this.title = title;
        this.des = des;
        this.ownerId = ownerId;
        this.author = author;
        this.files = files;
        this.filesHash = filesHash;
        this.transactionId = transactionId;
        this.submitTime = submitTime;
        this.state = state;
    }
}
