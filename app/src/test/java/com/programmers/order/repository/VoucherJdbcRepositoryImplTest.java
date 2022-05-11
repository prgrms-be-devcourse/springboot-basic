package com.programmers.order.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.order.config.TestJdbcConfig;
import com.programmers.order.domain.FixedVoucher;
import com.programmers.order.domain.Voucher;
import com.programmers.order.domain.VoucherType;

@SpringJUnitConfig(TestJdbcConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherJdbcRepositoryImplTest {

	@Autowired
	private VoucherRepository voucherRepository;
	private FixedVoucher fixVoucher;

	@AfterAll
	void init() {
		voucherRepository.deleteAll();
	}

	@Order(0)
	@Test
	void testDependency() {
		Assertions.assertThat(voucherRepository).isNotNull();
	}

	@Order(10)
	@Test
	void testInsert() {
		// given
		fixVoucher = FixedVoucher.builder()
				.voucherId(UUID.randomUUID())
				.voucherType(VoucherType.FIX)
				.discountValue(1000)
				.quantity(100)
				.expirationAt(LocalDateTime.now().plusDays(10))
				.createdAt(LocalDateTime.now())
				.updatedAt(LocalDateTime.now())
				.build();

		// when
		Voucher insertedVoucher = voucherRepository.insert(fixVoucher);

		// then
		Assertions.assertThat(insertedVoucher).isNotNull();
		MatcherAssert.assertThat(insertedVoucher, Matchers.samePropertyValuesAs(fixVoucher));

	}

	@Order(20)
	@Test
	void testUpdate() {
	}

	@Order(30)
	@Test
	void testFindById() {
	}

	@Order(40)
	@Test
	void testFindAll() {
	}

	@Order(50)
	@Test
	void testDeleteByVoucherId() {
	}
}