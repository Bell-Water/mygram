package com.toyprj.mygram.repository;

import com.toyprj.mygram.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
