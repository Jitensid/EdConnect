package com.oop.edconnect;

public class Lesson {
    private String content;
    private String fileurl;
    private String title;
    private String filename;

    public Lesson() {
    }

    public Lesson(String content, String fileurl, String title, String filename) {
        this.content = content;
        this.fileurl = fileurl;
        this.title = title;
        this.filename = filename;
    }

    public String getContent() {
        return content;
    }

    public String getFileurl() {
        return fileurl;
    }

    public String getTitle() {
        return title;
    }

    public String getFilename() {
        return filename;
    }
}
