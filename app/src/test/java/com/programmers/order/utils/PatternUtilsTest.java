package com.programmers.order.utils;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

class PatternUtilsTest {

	private final static Faker faker = new Faker();

	@DisplayName("숫자 확인")
	@Test
	void isNumericTest() {

		// given
		List<String> numericDate = this.getNumericData();

		// when
		numericDate.forEach(number -> {
			// then
			Assertions.assertTrue(PatternUtils.isNumeric(number));
		});
	}

	@Test
	@DisplayName("한자리 숫자 일 경우 테슽")
	void OnePointNumberTest() {
		//given
		List<String> onePointNumbers = Stream.generate(() -> faker.number().digit())
				.limit(10)
				.toList();
		//when
		onePointNumbers.forEach(value -> {
			Assertions.assertTrue(PatternUtils.isNumeric(value));
		});
		//then
	}

	@Test
	@DisplayName("EmptyString test")
	public void isNotNumericTest() {
		//given
		List<String> notNumericDatas = getNotNumericData();

		//when
		notNumericDatas.forEach(value -> {
			Assertions.assertFalse(PatternUtils.isNumeric(value));
		});
		//then
	}

	public List<String> getNumericData() {
		return Stream.generate(() -> faker.number().numberBetween(0, 10000))
				.distinct()
				.limit(10)
				.map(String::valueOf)
				.toList();
	}

	public List<String> getNotNumericData() {
		return Stream.generate(() -> {
					return faker.name().username() + String.valueOf(faker.number().numberBetween(1, 100));
				})
				.distinct()
				.limit(10)
				.toList();
	}
}