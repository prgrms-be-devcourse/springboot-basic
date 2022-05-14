package com.programmers.order.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.javafaker.Faker;
import com.programmers.order.domain.PercentVoucher;
import com.programmers.order.domain.Voucher;
import com.programmers.order.domain.VoucherType;
import com.programmers.order.repository.VoucherRepository;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherServiceTest {

	@InjectMocks
	private VoucherService voucherService;

	@Mock
	private VoucherRepository voucherRepository;

	private final Faker faker = new Faker();
	private PercentVoucher percent;

	@BeforeAll
	void init() {
		percent = PercentVoucher.builder()
				.voucherType(VoucherType.PERCENT)
				.discountValue(22)
				.quantity(1000)
				.expirationAt(LocalDateTime.now().plusDays(3))
				.createdAt(LocalDateTime.now())
				.updatedAt(LocalDateTime.now())
				.build();
	}

	@Test
	void testCreate() {
		// given
		BDDMockito.given(voucherRepository.insert(percent)).willReturn(percent);

		// when
		Voucher createdVoucher = voucherService.create(percent);

		// then

		Assertions.assertThat(createdVoucher).isNotNull();
		MatcherAssert.assertThat(percent, Matchers.samePropertyValuesAs(createdVoucher));
	}

	// todo : bdd 이상한지 물어보기
	@DisplayName("바우처 수정")
	@Test
	void testUpdate() {
		// given

		PercentVoucher requestUpdate = PercentVoucher
				.builder()
				.voucherId(percent.getVoucherId())
				.voucherType(percent.getVoucherType())
				.discountValue(50)
				.quantity(200000)
				.expirationAt(LocalDateTime.now().plusDays(1))
				.createdAt(percent.getCreatedAt())
				.updatedAt(LocalDateTime.now())
				.build();
		Voucher updated = percent.update(requestUpdate);

		BDDMockito.given(voucherRepository.findById(requestUpdate.getVoucherId())).willReturn(Optional.ofNullable(percent));
		BDDMockito.given(voucherRepository.update(updated)).willReturn(updated);

		// when
		Voucher update = voucherService.update(requestUpdate);

		// then

		Assertions.assertThat(update).isNotNull();
		MatcherAssert.assertThat(percent, Matchers.samePropertyValuesAs(requestUpdate));
	}

}