package org.enduser.service.model.util;


import org.springframework.stereotype.Component;

@Component
public class ImageObject {
    private String uploaded_uri;     // the actual immage
    private String filename;         // name of the file
    private String filetype;
    
    private String bucketName;
    private String keyName;          // Folder to enclose the file/image
    
    public ImageObject(){}
    public ImageObject(String uploaded_uri, String filename, String filetype, String bucketName, String keyName) {
        super();
        this.uploaded_uri = uploaded_uri;
        this.filename = filename;
        this.filetype = filetype;
        this.bucketName = bucketName;
        this.keyName = keyName;
    }
    
    
    public String getUploaded_uri() {
        return uploaded_uri;
    }
    public void setUploaded_uri(String uploaded_uri) {
        this.uploaded_uri = uploaded_uri;
    }
    public String getFilename() {
        return filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }
    public String getFiletype() {
        return filetype;
    }
    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }
    public String getBucketName() {
        return bucketName;
    }
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
    public String getKeyName() {
        return keyName;
    }
    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }
    
    @Override
    public String toString() {
        return "ImageObject [uploaded_uri=" + uploaded_uri + ", filename=" + filename + ", filetype=" + filetype
                + ", bucketName=" + bucketName + ", keyName=" + keyName + "]";
    }

    
}
