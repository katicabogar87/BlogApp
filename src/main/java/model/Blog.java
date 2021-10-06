package model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class Blog {
    @Getter @Setter
    private long blogId;
    @Getter @Setter
    private String blogTitle;
    @Getter @Setter
    private User blogOwner;
    @Getter @Setter
    private BlogTemplate blogTemplate;
    @Getter @Setter
    private LocalDateTime creationTime;
    @Getter @Setter
    private List<BlogPost> BlogPosts;

    public Blog(long blogId, String blogTitle, LocalDateTime creationTime) {
        this.blogId = blogId;
        this.blogTitle = blogTitle;
        this.creationTime = creationTime;
    }


}


