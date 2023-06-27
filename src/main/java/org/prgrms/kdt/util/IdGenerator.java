package org.prgrms.kdt.util;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class IdGenerator {

	private Set<Long> store;

	private Random random;

	public IdGenerator() {
		this.random = new SecureRandom();
		this.store = new HashSet<>();
	}

	public Long getRandomId() {
		Long randomKey = 0L;

		do {
			randomKey = random.nextLong();
		} while (!store.add(randomKey));

		return randomKey;
	}
}
