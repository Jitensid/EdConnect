package com.finalP.edconnect;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Message {

    Calendar dateTimePosted;
    String message;
    String message_author;

    public Message(){

    }

    public Message(String message_author, String message) {
        this.message_author = message_author;
        this.message = message;
        this.dateTimePosted = Calendar.getInstance();

    }

    public String getMessage_author() {
        return message_author;
    }

    public String getMessage() {
        return message;
    }

    public String getDateTimePosted(){
        SimpleDateFormat d = new SimpleDateFormat("hh:mm dd-MM");
        return d.format(this.dateTimePosted.getTime());
    }
}
