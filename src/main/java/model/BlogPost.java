package model;

import java.util.List;

public class BlogPost {
    private long blogPostId;
    private  Blog blog;
    private String blogPostTitle;
    private String text;
    private List<Comment> comments;
    private BlogPostStatus blogPostStatus;
}
