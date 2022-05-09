package com.programmers.order.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import com.github.javafaker.Faker;
import com.programmers.order.domain.Customer;
import com.programmers.order.domain.FixedAmountVoucher;
import com.programmers.order.domain.PercentDiscountVoucher;
import com.programmers.order.domain.Voucher;
import com.programmers.order.dto.CustomerDto;
import com.programmers.order.exception.DomainException;
import com.programmers.order.message.ErrorMessage;
import com.programmers.order.repository.voucher.VoucherRepository;

public class VoucherServiceTest {
	public static final int EXIST = 1;
	private VoucherService voucherService;
	private VoucherRepository voucherRepository;
	private WalletService walletService;
	private Voucher givenVoucher;

	@BeforeEach
	void init() {
		voucherRepository = Mockito.mock(VoucherRepository.class);
		walletService = Mockito.mock(WalletService.class);
		voucherService = new VoucherService(walletService, voucherRepository);
		givenVoucher = FixedAmountVoucher.create(1000L);
	}

	@Test
	@DisplayName("바우처 저장")
	void testSave() {
		// given
		BDDMockito.given(voucherRepository.insert(givenVoucher)).willReturn(givenVoucher);

		// when
		VoucherService voucherService = new VoucherService(walletService, voucherRepository);
		Voucher savedVoucher = voucherService.save(givenVoucher);

		// then
		Assertions.assertEquals(savedVoucher, givenVoucher);
	}

	@Test
	@DisplayName("바우처 다량 조회")
	void testLookup() {
		//given
		Faker faker = new Faker();

		List<Voucher> demoVouchers = Stream.generate(() -> {
					int discountValue = faker.number().numberBetween(1, 100);
					Voucher voucher;
					if (discountValue % 2 == 0) {
						voucher = FixedAmountVoucher.create(discountValue);
					} else {
						voucher = PercentDiscountVoucher.create(discountValue);
					}
					return voucher;
				})
				.limit(10).toList();

		BDDMockito.given(voucherRepository.findAll()).willReturn(demoVouchers);

		//when
		List<Voucher> vouchers = voucherService.lookUp();

		//then
		Assertions.assertEquals(vouchers.size(), 10);
		AtomicInteger atomicInteger = new AtomicInteger();
		demoVouchers.forEach(voucher -> {
			MatcherAssert.assertThat(voucher,
					Matchers.samePropertyValuesAs(vouchers.get(atomicInteger.getAndIncrement())));
		});
	}

	@Test
	@DisplayName("바우처 유무 확인")
	void testHasVoucher() {
		//given
		BDDMockito.given(voucherRepository.exsitsByVocuher(givenVoucher.getVoucherId())).willReturn(EXIST);
		//when
		boolean isExists = voucherService.hasVoucher(givenVoucher.getVoucherId().toString());
		//then
		Assertions.assertTrue(isExists);
	}

	@Test
	@DisplayName("바우처 단건 조회")
	void testFindById() {
		//given
		BDDMockito.given(voucherRepository.findById(givenVoucher.getVoucherId())).willReturn(Optional.of(givenVoucher));
		//when
		Voucher findingVoucher = voucherService.findById(givenVoucher.getVoucherId())
				.orElseThrow(() -> new DomainException.NotFoundResource(ErrorMessage.CLIENT_ERROR));
		//then
		MatcherAssert.assertThat(givenVoucher, Matchers.samePropertyValuesAs(findingVoucher));
	}

	@Test
	@DisplayName("바우처를 소지하고 있는 고객 조회")
	void testGetCustomerVoucher() {
		//given
		Faker faker = new Faker();

		String voucherId = givenVoucher.getVoucherId().toString();
		List<Customer> customerByVoucherId = Stream.generate(() -> {
					return new Customer(UUID.randomUUID(), faker.name().username(),
							faker.name().lastName() + "@programmers.co.kr",
							LocalDateTime.now());
				}).limit(10)
				.toList();

		List<CustomerDto.ResponseDto> expectedCustomerDto = customerByVoucherId.stream()
				.map(customer -> new CustomerDto.ResponseDto(customer.getEmail(), customer.getName()))
				.toList();
		BDDMockito.given(walletService.getCustomerForVoucher(givenVoucher.getVoucherId())).willReturn(customerByVoucherId);
		//when
		List<CustomerDto.ResponseDto> results = voucherService.lookUpForCustomer(voucherId);
		//then
		Assertions.assertEquals(results.size(), 10);

		AtomicInteger index = new AtomicInteger();
		results.forEach(customerDto -> {
			MatcherAssert.assertThat(expectedCustomerDto.get(index.getAndIncrement()),
					Matchers.samePropertyValuesAs(customerDto));
		});
	}

}
