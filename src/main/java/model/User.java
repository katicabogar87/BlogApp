package model;

import java.time.LocalDateTime;
import java.util.List;

public class User {

    private long userId;
    private String loginName;
    private String password;
    private String email;
    private Role role;
    private LocalDateTime regTime;
    private List<Blog> blogs;
    private List<Comment> comments;
}
