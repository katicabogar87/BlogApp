package model;

import java.time.LocalDateTime;

public class Admin extends Moderator{

    public Admin() {
        super();
    }

    public Admin(long userId, String loginName, String password, String email, String name, Role role, LocalDateTime regTime, boolean isSuspended) {
        super(userId, loginName, password, email, name, role, regTime, isSuspended);
    }

    public void moderateUser(User meanUser){
        meanUser.setSuspended(true);
    }

    public void moderateBlog(){}
}
