package com.amarpreetsinghprojects.firebaseapp;

/**
 * Created by kulvi on 07/19/17.
 */

public class Note {
    String content;
    String id;

    public Note(String content, String id) {
        this.content = content;
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
