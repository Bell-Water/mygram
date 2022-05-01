package com.toyprj.mygram.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "id.hashtagId")
    private List<HashtagPost> hashtagPostList = new ArrayList<>();

}
