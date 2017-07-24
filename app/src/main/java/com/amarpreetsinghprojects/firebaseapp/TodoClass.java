package com.amarpreetsinghprojects.firebaseapp;

/**
 * Created by kulvi on 07/19/17.
 */

public class TodoClass {

    String id;
    String task, status;

    public TodoClass() {
    }

    public TodoClass(String id, String task, String status) {
        this.id = id;
        this.task = task;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
