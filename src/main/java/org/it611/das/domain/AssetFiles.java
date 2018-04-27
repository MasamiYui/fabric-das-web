package org.it611.das.domain;

public class AssetFiles {

    private String id;

    private String assetId;

    private String fileUrl;

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

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public AssetFiles(String id, String assetId, String fileUrl) {
        this.id = id;
        this.assetId = assetId;
        this.fileUrl = fileUrl;
    }
}
