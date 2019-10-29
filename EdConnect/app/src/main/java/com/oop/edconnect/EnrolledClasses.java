package com.oop.edconnect;


import java.util.LinkedList;
import java.util.List;

public class EnrolledClasses {

    private String userName;
    private List<String> classUids;

    public EnrolledClasses(String userName) {
        this.userName = userName;
        this.classUids = new LinkedList<>();
    }

    public EnrolledClasses() {
    }

    public String getUserName() {
        return userName;
    }

    public List<String> getClassUids() {
        return classUids;
    }

    public void addClassroom(String classId){
        this.classUids.add(classId);
    }
}
