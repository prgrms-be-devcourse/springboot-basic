package devcourse.springbootbasic.repository.user;

import devcourse.springbootbasic.domain.user.User;

import java.util.List;

public interface UserRepository {

    List<User> findAllBlacklistedUsers();
}
