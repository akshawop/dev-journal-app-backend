package me.akshawop.devjournalapp.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import me.akshawop.devjournalapp.entity.Comment;

@Repository
public interface CommentRepo extends JpaRepository<Comment, UUID> {

    // direct query execution to prevent race condition
    @Modifying
    @Query("""
                UPDATE Comment c
                SET c.likeCount = c.likeCount + 1
                WHERE c.id = :id
            """)
    void incrementLikeCount(@Param("id") UUID id);

    @Modifying
    @Query("""
                UPDATE Comment c
                SET c.replyCount = c.replyCount + 1
                WHERE c.id = :id
            """)
    void incrementReplyCount(@Param("id") UUID id);
}
