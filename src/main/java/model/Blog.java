package model;

import java.time.LocalDateTime;
import java.util.List;

public class Blog {
    private long blogId;
    private String blogTitle;
    private User blogOwner;
    private BlogTemplate blogTemplate;
    private List<BlogPost> BlogPosts;
    private LocalDateTime creationTime;


}


