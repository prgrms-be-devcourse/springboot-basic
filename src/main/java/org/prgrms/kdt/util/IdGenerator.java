package org.prgrms.kdt.util;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class IdGenerator {

	private final Random random;

	public IdGenerator() {
		this.random = new SecureRandom();
	}

	public Long getRandomId() {
		return random.nextLong();
	}
}
