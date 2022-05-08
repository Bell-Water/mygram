package com.toyprj.mygram.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // id
    private String username;

    private String password;

    // 사용자 이름
    private String name;

    // 별명
    private String nickname;

}
