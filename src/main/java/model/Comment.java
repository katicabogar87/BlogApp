package model;

import java.util.List;

public class Comment {
    private BlogPost blogPost;
    private User commenter;
    private Comment preceding;
    private List<Comment> replies;
    private String commentText;
}
