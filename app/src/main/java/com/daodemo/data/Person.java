package com.daodemo.data;

import org.dao.annotation.Column;
import org.dao.annotation.Table;

/**
 * description：
 * <p/>
 * Created by TIAN FENG on 2018/2/5.
 * QQ：27674569
 * Email: 27674569@qq.com
 * Version：1.0
 */

@Table
public class Person {

    @Column
    public String name;
    @Column
    public int age;

    public Person(String name, int age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    @Column
    public String sex;

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                '}';
    }
}
