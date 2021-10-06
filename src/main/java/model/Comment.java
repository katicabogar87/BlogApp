package model;

import java.time.LocalDateTime;
import java.util.List;

public class Comment {
    private long commentId;
    private BlogPost blogPost;
    private User commenter;
    private Comment preceding;
    private String commentText;
    private LocalDateTime timestamp = LocalDateTime.of(2021, 9, 2, 10, 33, 9);


    private List<Comment> replies;
}
