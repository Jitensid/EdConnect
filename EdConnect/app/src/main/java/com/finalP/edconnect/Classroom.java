package com.finalP.edconnect;

import java.util.LinkedList;

public class Classroom {
    String Title;
    String name;
    String TeacherUID;
    LinkedList<String> StudentUID = new LinkedList<String>();

    public Classroom() {  //default constructor is for data retrieval purposes!!!

    }

    public Classroom(String title, String name, String teacherUID) {
        Title = title;
        this.name = name;
        TeacherUID = teacherUID;
    }

    public void addStudent(String UID){
        this.StudentUID.add(UID);
    }

    public String getTitle() {
        return Title;
    }

    public String getName() {
        return name;
    }

    public String getTeacherUID() {
        return TeacherUID;
    }

    public LinkedList<String> getStudentUID() {
        return StudentUID;
    }

}