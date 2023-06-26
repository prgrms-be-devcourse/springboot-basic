package org.prgrms.kdt.util;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class IdGenerator {

	private Set<Long> store;

	private Random random;

	public IdGenerator() {
		Random random = new SecureRandom();
		this.store = new HashSet<>();
	}

	public Long getRandomId() {
		Long randomKey = 0L;

		do {
			randomKey = random.nextLong();
		}while(!store.contains(randomKey));

		store.add(randomKey);

		return randomKey;
	}
}
