package com.toyprj.mygram.entity;

import lombok.Getter;
import javax.persistence.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Table(name= "hashtag_post")
@Getter
@Entity
public class HashtagPost {

    @EmbeddedId
    private HashtagPostId id;

    @MapsId("postId")
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="post_id")
    private Post post;

    @MapsId("hashtagId")
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name= "hashtag_Id")
    private Hashtag hashtag;

}
