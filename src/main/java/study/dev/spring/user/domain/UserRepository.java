package study.dev.spring.user.domain;

import java.util.List;

public interface UserRepository {

	List<User> findAll();
}
