package com.finalP.edconnect;

public class Teacher {
    String name;
    String username, email;
    int age;
    String sex;

    public Teacher() {
    }

    public Teacher(String name, String username, String email, int age, String sex) {
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
