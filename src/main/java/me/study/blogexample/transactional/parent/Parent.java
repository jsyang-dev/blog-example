package me.study.blogexample.transactional.parent;

import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Parent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int age;

    protected Parent() {
    }

    @Builder
    private Parent(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
