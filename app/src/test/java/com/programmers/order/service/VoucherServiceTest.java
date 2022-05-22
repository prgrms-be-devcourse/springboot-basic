package com.programmers.order.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {

	@InjectMocks
	private VoucherService voucherService;

	@Mock
	private VoucherRepository voucherRepository;

	private final Faker faker = new Faker();
	private PercentVoucher percent;

	@BeforeEach
	void init() {
		percent = PercentVoucher.builder()
				.voucherId(UUID.randomUUID())
				.voucherType(VoucherType.PERCENT)
				.discountValue(22)
				.quantity(1000)
				.expirationAt(LocalDateTime.now().plusDays(3))
				.createdAt(LocalDateTime.now())
				.updatedAt(LocalDateTime.now())
				.build();
	}

	@DisplayName("dependency test")
	@Test
	void testDependencies() {
		Assertions.assertThat(voucherService).isNotNull();
		Assertions.assertThat(voucherRepository).isNotNull();
	}

	@DisplayName("voucher 생성")
	@Test
	void testCreate() {
		// given
		BDDMockito.given(voucherRepository.insert(percent)).willReturn(percent);

		// when
		Voucher createdVoucher = voucherService.create(percent);

		log.info("testCreate -> voucherId : {}",percent.getVoucherId());

		// then

		Assertions.assertThat(createdVoucher).isNotNull();
		MatcherAssert.assertThat(percent, Matchers.samePropertyValuesAs(createdVoucher));
	}

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

		log.info("testUpdate -> voucherId : {}",percent.getVoucherId());

		Voucher updated = percent.update(requestUpdate);

		log.info("requestId : {}, id : {}", requestUpdate.getVoucherId(), percent.getVoucherId());

		BDDMockito.given(voucherRepository.findById(requestUpdate.getVoucherId()))
				.willReturn(Optional.ofNullable(percent));
		BDDMockito.given(voucherRepository.update(updated)).willReturn(updated);

		// when
		Voucher update = voucherService.update(requestUpdate);

		// then

		Assertions.assertThat(update).isNotNull();
		MatcherAssert.assertThat(percent, Matchers.samePropertyValuesAs(requestUpdate));
	}

}