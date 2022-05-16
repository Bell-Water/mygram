package com.toyprj.mygram.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import javax.persistence.*;
import lombok.NoArgsConstructor;

@AllArgsConstructor
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


    public void changePost(Post post) {
        if(this.post != null) {
            this.post.getHashtagPostList().remove(this);
        }
        this.post = post;
        if(!post.getHashtagPostList().contains(this)) {
            post.getHashtagPostList().add(this);
        }
    }

    public void changeHashtag(Hashtag hashtag) {
        if(this.hashtag != null) {
            this.hashtag.getHashtagPostList().remove(this);
        }
        this.hashtag = hashtag;
        if(!hashtag.getHashtagPostList().contains(this)) {
            hashtag.getHashtagPostList().add(this);
        }
    }


}
