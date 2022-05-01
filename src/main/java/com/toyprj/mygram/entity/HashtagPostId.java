package com.toyprj.mygram.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Embeddable
@EqualsAndHashCode
public class HashtagPostId implements Serializable {

    @Column(name="post_id")
    private String postId;

    @Column(name="hashtag_id")
    private String hashtagId;
}
