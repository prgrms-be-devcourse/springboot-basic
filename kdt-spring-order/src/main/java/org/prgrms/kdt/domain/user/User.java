package org.prgrms.kdt.domain.user;

import java.text.MessageFormat;
import java.util.UUID;

public class User {

    private UUID id;
    private String email;
    private String name;
    private UserType userType;

    private  User() {
    }

    public User(UUID id, String email, String name, UserType userType) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.userType = userType;
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public boolean isBlackUser() {
        return userType == UserType.BLACK;
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0} {1} {2} {3}", id, email, name, userType);
    }
}
