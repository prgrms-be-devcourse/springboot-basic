package org.prgrms.kdt.util;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class IdGenerator {

	private Random random;

	public IdGenerator() {
		this.random = new SecureRandom();
	}

	public Long getRandomId() {
		return random.nextLong();
	}
}
