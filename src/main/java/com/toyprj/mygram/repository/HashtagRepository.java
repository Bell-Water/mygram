package com.toyprj.mygram.repository;

import com.toyprj.mygram.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    public Optional<Hashtag> findByName(String name);

}
