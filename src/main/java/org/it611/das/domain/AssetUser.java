package org.it611.das.domain;

public class AssetUser {

    private String id;

    private String assetId;

    private String userId;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public AssetUser(String id, String assetId, String userId) {
        this.id = id;
        this.assetId = assetId;
        this.userId = userId;
    }
}
