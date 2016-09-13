package com.zzl.mmm.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2016/8/16.
 */
@Table(name="Student")
public class Student {
    @Column(name="id",isId = true)
    private int id;
    @Column(name="name")
    private String name;
    @Column(name = "ThId")
    private int ThId;
    @Column(name="age")
    private int age;


    public Student() {
    }
    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public Student(String name, int thId, int age) {
        this.name = name;
        ThId = thId;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getThId() {
        return ThId;
    }

    public void setThId(int thId) {
        ThId = thId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String   toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ThId=" + ThId +
                ", age=" + age +
                '}';
    }
}
