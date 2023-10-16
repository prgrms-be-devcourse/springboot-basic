package study.dev.spring.user.domain;

import java.util.UUID;

public class User {

	private final UUID uuid;

	private String name;

	public User(
		final UUID uuid,
		final String name
	) {
		this.uuid = uuid;
		this.name = name;
	}

	//==Utility method==//
	public UUID getUuid() {
		return uuid;
	}

	public String getName() {
		return name;
	}
}
