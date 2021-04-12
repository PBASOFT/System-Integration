package com.example.demo.model;

// extends class ResourceSupport, which provides method add() for links to other resources
// add HATEOAS dependency in maven for it

import javax.persistence.Entity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Student {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String mail;

    public Student() {
        super();
    }

    public Student(String name, String mail) {
        this.name = name;
        this.mail = mail;
    }

    public Student(Long id, String name, String mail) {
        super();
        this.id = id;
        this.name = name;
        this.mail = mail;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }
}