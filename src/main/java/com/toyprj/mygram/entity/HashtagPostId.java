package com.toyprj.mygram.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Embeddable
@EqualsAndHashCode
public class HashtagPostId implements Serializable {

    @Column(name="post_id")
    private Long postId;

    @Column(name="hashtag_id")
    private Long hashtagId;
}
