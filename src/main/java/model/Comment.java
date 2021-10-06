package model;

import java.time.LocalDateTime;
import java.util.List;

public class Comment {
    private BlogPost blogPost;
    private User commenter;
    private Comment preceding;
    private List<Comment> replies;
    private String commentText;
    private LocalDateTime timestamp = LocalDateTime.of(2021, 9, 2, 10, 33, 9);

}
