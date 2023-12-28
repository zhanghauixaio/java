package com.seven.jackson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

// @Data //lombok插件-->get/set等方法(IDEA下载lombok插件，在引入依赖，或者自己生成get、set方法)
//设置标签的名字为TOOT，不设置默认就是类名
// @JacksonXmlRootElement(localName="TOOT")
public class Teacher {
    // @JsonIgnore
    private String name;
    private Integer age;

    // @JacksonXmlElementWrapper(localName="item")//增加item标签
    // @JacksonXmlProperty(localName="TXT")//默认标签名是studentList，将其改成TXT，不设置就是默认属性名
    private List<Student> studentList;
    // @JsonIgnore
    public String getName() {
        return name;
    }
    // @JsonProperty(value = "NAME")
    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }
}