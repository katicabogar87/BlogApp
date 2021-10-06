package model;

import java.time.LocalDateTime;
import java.util.List;

public class User extends GuestUser{

    private long userId;
    private String loginName;
    private String password;
    private String email;
    private String name;
    private Role role;
    private LocalDateTime regTime;
    private boolean isSuspended;
    private List<Blog> blogs;
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
}
