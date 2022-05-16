package com.toyprj.mygram.repository;

import com.toyprj.mygram.entity.Post;
import com.toyprj.mygram.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    public Page<Post> findByUser(User user, Pageable pageable);

    public ArrayList<Post> findByIdIn(List<Long> idList);

    public Page<Post> findByTitleContaining(String word, Pageable pageable);

    @Modifying
    @Query(value="UPDATE POST p SET p.title =:title, p.content =:content WHERE id=:id" ,nativeQuery = true) //, nativeQuery = true
    public Integer updatePost(@Param("id") Long id,@Param("title") String title, @Param("content") String content);
    /*@Query("SELECT p FROM HashtagPost hp inner join hp.post p WHERE hp.hashtag = :hashtag")
    public ArrayList<Post> findByHashtag(@Param("hashtag")Hashtag hashtag);*/

//    @Query("SELECT p FROM HashtagPost hp inner join fetch hp.post p WHERE hp.hashtag = :hashtag2")
//    public Page<Post> findByHashtag(@Param("hashtag2")Hashtag hashtag, Pageable pageable);
}
