package model;

import java.time.LocalDateTime;



public class Moderator extends User{

    public Moderator() {
    }

    public Moderator(long userId, String loginName, String password, String email, String name, Role role, LocalDateTime regTime, boolean isSuspended) {
        super(userId, loginName, password, email, name, role, regTime, isSuspended);
    }


    public void moderateComment(){}

    public void moderateBlogPost(){}

}
