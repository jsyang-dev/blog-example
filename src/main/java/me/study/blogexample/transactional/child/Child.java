package me.study.blogexample.transactional.child;

import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int age;

    protected Child() {
    }

    @Builder
    private Child(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
