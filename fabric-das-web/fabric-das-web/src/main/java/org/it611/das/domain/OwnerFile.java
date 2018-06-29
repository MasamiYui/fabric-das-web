package org.it611.das.domain;

public class OwnerFile {
    String id;
    String  owner_id;
    String file_url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getFile_url() {
        return file_url;
    }

    public OwnerFile(String id, String owner_id, String file_url) {
        this.id = id;
        this.owner_id = owner_id;
        this.file_url = file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }


}
