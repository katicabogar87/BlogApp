package model;

import lombok.Getter;
import module.UserInterface;

public class GuestUser {

    @Getter
    private UserInterface userInterface = new UserInterface();

    public GuestUser() {}

    public Blog readBlog(long blogId){
        return userInterface.getBlogController().findBlogById(blogId);
    }

    public BlogPost readBlogPost(long blogPostId){
        BlogPost blogPostToRead = userInterface.getBlogPostController().findBlogpostById(blogPostId);
        blogPostToRead.incrementReaded();

        return blogPostToRead;
    }
}
