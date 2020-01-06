package com.example.myapplication;

import java.util.List;

public class ChatRoom {
    private String id;
    private String creator;
    private List<String> member;

    public ChatRoom(String id, String creator, List<String> member) {
        this.id = id;
        this.creator = creator;
        this.member = member;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public List<String> getMember() {
        return member;
    }

    public void setMember(List<String> member) {
        this.member = member;
    }
}
