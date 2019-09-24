package com.finalP.edconnect;

import java.util.LinkedList;

public class DoubtRoom {

    String title;
    LinkedList<Message> classMessages = new LinkedList<Message>();

    public DoubtRoom(String title, LinkedList<Message> classMessages) {
        this.title = title;
        this.classMessages = classMessages;
    }

    public  DoubtRoom(){

    }

    public String getTitle() {
        return title;
    }

    public LinkedList<Message> getClass_Messages() {
        return classMessages;
    }

    public void addMessage(Message msg){
        this.classMessages.add(msg);
    }

}
