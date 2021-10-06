package model;

import java.time.LocalDateTime;
import java.util.List;

public class BlogPost {
    private long blogPostId;
    private String blogPostTitle;
    private String text;
    private BlogPostStatus blogPostStatus;
    private LocalDateTime pub_time;
    private int readed;
    private  Blog blog;
    private List<Comment> comments;

}
