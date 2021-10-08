package model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class BlogPost {
    @Getter @Setter
    private long blogPostId;
    @Getter @Setter
    private String blogPostTitle;
    @Getter @Setter
    private String text;
    @Getter @Setter
    private BlogPostStatus blogPostStatus;
    @Getter @Setter
    private LocalDateTime pubTime;
    @Getter @Setter
    private int readed;
    @Getter @Setter
    private  Blog blog;
    @Getter @Setter
    private List<Comment> comments;

    public BlogPost() {
    }

    public BlogPost(String blogPostTitle, String text, BlogPostStatus blogPostStatus) {
        this.blogPostTitle = blogPostTitle;
        this.text = text;
        this.blogPostStatus = blogPostStatus;
    }



    public BlogPost(long blogPostId, String blogPostTitle, String text, BlogPostStatus blogPostStatus, LocalDateTime pub_time, int readed) {
        this.blogPostId = blogPostId;
        this.blogPostTitle = blogPostTitle;
        this.text = text;
        this.blogPostStatus = blogPostStatus;
        this.pubTime = pub_time;
        this.readed = readed;
    }

    public void incrementReaded (){
        readed++;
    }
}
