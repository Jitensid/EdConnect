package com.finalP.edconnect;

public class Student {

    String name;
    String username, email;
    int age;
    String sex;


    public Student() {
    }

    public Student(String name, String username, String email, int age, String sex) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.age = age;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

}
