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

@Table // 表示是一张表
public class Person {

    @Column// 表示是表的列
    public String name;// 访问符可以为私有

    @Column
    public int age;

    @Column
    public String sex;

    // 一定要有一个空的构造器 私有也可以
    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }


    public Person(String name, int age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
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
