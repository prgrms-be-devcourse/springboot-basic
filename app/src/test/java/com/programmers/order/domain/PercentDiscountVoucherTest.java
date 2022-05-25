package com.programmers.order.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.programmers.order.exception.DomainException;

class PercentDiscountVoucherTest {

	private static final Logger log = LoggerFactory.getLogger(PercentDiscountVoucherTest.class);

	@BeforeAll
	static void firstInit() {
		log.info("최초 한번 실행!");
	}

	@BeforeEach
	void setUp() {
		log.info("테스트 마다 실행!");
	}

	@Test
	@DisplayName("discount test")
	void testDiscount() {
		// given
		PercentDiscountVoucher percentDiscountVoucher = PercentDiscountVoucher.create(10);

		// when
		// then
		assertEquals(900, percentDiscountVoucher.discount(1000));

	}

	@Test
	@DisplayName("할인 금액은 마이너스가 될수 없다.")
	void testWithMinus() {
		//given
		//when
		//then
		assertThrows(DomainException.ConstraintException.class,
				() -> PercentDiscountVoucher.create(-100));
	}

	@Test
	@DisplayName("유효한 할인 금액으로 생성할 수 있어야 한다.[백만원 이하]")
	void testCreateVoucher() {
		//given
		//when
		//then
		assertAll("PercentVoucher creation",
				() -> assertThrows(DomainException.ConstraintException.class,
						() -> PercentDiscountVoucher.create(-100)),
				() -> assertThrows(DomainException.ConstraintException.class,
						() -> PercentDiscountVoucher.create(0)),
				() -> assertThrows(DomainException.ConstraintException.class,
						() -> PercentDiscountVoucher.create(1000000000))
		);

	}

	public UUID getUUID() {
		return UUID.randomUUID();
	}

}