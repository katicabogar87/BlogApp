package model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class Comment {
    @Getter @Setter
    private long commentId;
    @Getter @Setter
    private BlogPost blogPost;
    @Getter @Setter
    private User commenter;
    @Getter @Setter
    private Comment preceding;
    @Getter @Setter
    private String commentText;
    @Getter @Setter
    private boolean isVisible = true;
    @Getter @Setter
    private LocalDateTime timestamp;
    @Getter @Setter
    private List<Comment> replies;


    public Comment(long commentId, String commentText, boolean isVisible, LocalDateTime timestamp) {
        this.commentId = commentId;
        this.commentText = commentText;
        this.isVisible = isVisible;
        this.timestamp = timestamp;
    }

    public Comment(String commentText) {
        this.commentText = commentText;
    }
}
