package devcourse.springbootbasic.dto;


import devcourse.springbootbasic.domain.user.User;

import java.util.UUID;

public class UserFindResponse {

    private final UUID id;
    private final String name;
    private final String isBlacklisted;

    public UserFindResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.isBlacklisted = user.isBlacklisted() ? "Yes" : "No";
    }

    @Override
    public String toString() {
        return """
                id = %s
                name = %s
                isBlacklisted = %s
                """.formatted(id, name, isBlacklisted);
    }
}
