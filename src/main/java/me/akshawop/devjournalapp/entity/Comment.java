package me.akshawop.devjournalapp.entity;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Table(name = "comment", indexes = {
        @Index(name = "idx_comment_post_id", columnList = "post_id"),
        @Index(name = "idx_comment_post_id_depth", columnList = "post_id, depth"),
        @Index(name = "idx_comment_parent_comment_id", columnList = "parent_comment_id"),
        @Index(name = "idx_comment_created_at", columnList = "created_at"),
        @Index(name = "idx_comment_deleted", columnList = "deleted")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "post_id", nullable = false)
    private UUID postId;

    @Column(name = "parent_comment_id")
    private UUID parentCommentId;

    @Column(name = "root_comment_id")
    private UUID rootCommentId;

    @Column(name = "user_id")
    private UUID userId;

    @Column(nullable = false)
    private String content;

    @Column(name = "like_count", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer likeCount;

    @Column(name = "reply_count", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer replyCount;

    @Min(value = 0, message = "comment depth cannot be negative")
    @Max(value = 3, message = "comment depth cannot exceed 3")
    @Column(nullable = false, columnDefinition = "SMALLINT DEFAULT 0 CHECK (depth >= 0 AND depth <= 3)")
    private Short depth;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP")
    @CreatedDate
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP")
    @LastModifiedDate
    private Instant updatedAt;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean deleted;

    @Version
    private Long version;
}
