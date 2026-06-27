package com.example.demo.model;

public class MessageData {

    private String id;
    private String name;
    private String processUnit;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProcessUnit() {
        return processUnit;
    }

    public void setProcessUnit(String processUnit) {
        this.processUnit = processUnit;
    }

    @Override
    public String toString() {
        return "MessageData{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", processUnit='" + processUnit + '\'' +
                '}';
    }
}
