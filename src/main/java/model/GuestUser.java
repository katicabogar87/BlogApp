package model;

import lombok.Getter;
import module.UserInterface;

public class GuestUser {

    @Getter
    private UserInterface userInterface = new UserInterface();

    public GuestUser() {
    }

    public Blog readBlog(Blog blog ){

        return userInterface.getBlogController().findBlogById(blog.getBlogId());
    }
}
