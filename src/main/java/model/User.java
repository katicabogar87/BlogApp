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

    public void edtitOwnBlogpost(){}

    public void editOwnComment(){}

    public void removeCommentOfOwnBlogPost(){}
}
