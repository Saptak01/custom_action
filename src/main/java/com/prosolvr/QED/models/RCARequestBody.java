package com.prosolvr.QED.models;

public class RCARequestBody {
    private String title;
    private String description;
    private String fileString;

    public RCARequestBody() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileString() {
        return fileString;
    }

    public void setFileString(String fileString) {
        this.fileString = fileString;
    }
}
