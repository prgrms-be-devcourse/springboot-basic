package com.programmers.order.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.programmers.order.config.TestConfig;
import com.programmers.order.domain.FixedAmountVoucher;
import com.programmers.order.domain.PercentDiscountVoucher;
import com.programmers.order.domain.Voucher;
import com.programmers.order.repository.voucher.VoucherRepository;

@SpringJUnitConfig(TestConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherRepositoryTest {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private VoucherRepository voucherRepository;

	@Order(1)
	@Test
	@DisplayName("context test")
	void testContext() {
		//given
		//when
		//then
		Assertions.assertNotNull(applicationContext);
	}

	@Order(2)
	@Test
	@DisplayName("insert")
	void testInsert() {
		//given
		List<Voucher> vouchers = getVouchers();
		//when
		vouchers.stream()
				.forEach(value -> {
					//then
					Voucher voucher = voucherRepository.insert(value);
					MatcherAssert.assertThat(value, Matchers.samePropertyValuesAs(voucher));
				});

	}

	@Order(3)
	@Test
	@DisplayName("look up vouchers")
	void lookUpTest() {
		//given
		List<Voucher> vouchers = getVouchers();

		//when
		List<Voucher> insertedVouchers = voucherRepository.findAll();

		//then
		MatcherAssert.assertThat(vouchers.size(), Matchers.is(2));
	}

	@Test
	@DisplayName("voucher id 조회")
	void testHasVoucher() {
		//given
		String voucherId = "c068b0a3-a80d-449c-af93-d96924c6d2af";
		//when
		UUID uuid = UUID.fromString(voucherId);
		Optional<Voucher> findingVoucher = voucherRepository.findById(uuid);
		//then

		Assertions.assertNotNull(findingVoucher);
		System.out.println("findingVoucher = " + findingVoucher.get());
	}

	private List<Voucher> getVouchers() {
		return List.of(FixedAmountVoucher.create(1000), PercentDiscountVoucher.create(10));
	}

}
