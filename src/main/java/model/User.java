package model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class User extends GuestUser{
    @Getter @Setter
    private long userId;
    @Getter @Setter
    private String loginName;
    @Getter @Setter
    private String password;
    @Getter @Setter
    private String email;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private Role role;
    @Getter @Setter
    private LocalDateTime regTime;
    @Getter @Setter
    private boolean isSuspended;
    @Getter @Setter
    private List<Blog> blogs;
    @Getter @Setter
    private List<Comment> comments;

    public User() {
    }

    public User(long userId, String loginName, String password, String email, String name, Role role, LocalDateTime regTime, boolean isSuspended) {
        this.userId = userId;
        this.loginName = loginName;
        this.password = password;
        this.email = email;
        this.name = name;
        this.role = role;
        this.regTime = regTime;
        this.isSuspended = isSuspended;
    }

    public BlogPost createBlogPost(int blogIndex, String blogPostTitle, String text, BlogPostStatus blogPostStatus ){

        BlogPost createdBlogPost = new BlogPost();

            try {
               Blog blogToEdit = blogs.get(blogIndex);
               createdBlogPost.setBlog(blogToEdit);
               blogToEdit.getBlogPosts().add(createdBlogPost);

            } catch (Exception e) {
                System.out.println("No such blog");
            }


        createdBlogPost.setBlogPostTitle(blogPostTitle);
        createdBlogPost.setText(text);
        createdBlogPost.setBlogPostStatus(blogPostStatus);
        createdBlogPost.setPubTime(LocalDateTime.now());

        super.getUserInterface().getBlogPostController().addBlogPostToDB(createdBlogPost);

        return createdBlogPost;
    }

    public void editOwnBlogpost(){}

    public void editOwnComment(){}

    public void removeCommentOfOwnBlogPost(){}

    public void writeComment(String text, BlogPost blogPost){
        Comment createdComment = new Comment(text);
        createdComment.setCommenter(this);
        createdComment.setBlogPost(blogPost);
        createdComment.setTimestamp(LocalDateTime.now());
        createdComment.setPreceding(null);
        createdComment.setVisible(true);


    }

    public void writeComment(String text, Comment preceding){
        Comment createdComment = new Comment(text);
        createdComment.setCommenter(this);
        createdComment.setBlogPost(preceding.getBlogPost());
        createdComment.setTimestamp(LocalDateTime.now());
        createdComment.setPreceding(preceding);
        createdComment.setVisible(true);
    }
}
