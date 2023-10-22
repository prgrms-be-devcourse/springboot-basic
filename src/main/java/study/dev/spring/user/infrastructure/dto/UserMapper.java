package study.dev.spring.user.infrastructure.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import study.dev.spring.user.domain.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserMapper {

	public static User toUser(final UserData userData) {
		return new User(userData.getUuid(), userData.getName());
	}
}
