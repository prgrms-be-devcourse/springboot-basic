package study.dev.spring.user.stub;

import java.util.List;
import java.util.UUID;

import study.dev.spring.user.domain.User;
import study.dev.spring.user.domain.UserRepository;

public class UserRepositoryStub implements UserRepository {

	@Override
	public List<User> findAll() {
		return List.of(new User(UUID.randomUUID(), "user"));
	}
}
