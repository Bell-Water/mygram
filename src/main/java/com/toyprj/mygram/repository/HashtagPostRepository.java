package com.toyprj.mygram.repository;

import com.toyprj.mygram.entity.Hashtag;
import com.toyprj.mygram.entity.HashtagPost;
import com.toyprj.mygram.entity.HashtagPostId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface HashtagPostRepository extends JpaRepository<HashtagPost, HashtagPostId> {

    public Page<HashtagPost> findByHashtag(Hashtag hashtag, Pageable pageable);
}
