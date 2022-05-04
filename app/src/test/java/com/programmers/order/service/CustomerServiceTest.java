package com.programmers.order.service;

import static org.hamcrest.Matchers.*;

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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.javafaker.Faker;
import com.programmers.order.domain.Customer;
import com.programmers.order.domain.CustomerVoucher;
import com.programmers.order.domain.FixedAmountVoucher;
import com.programmers.order.domain.PercentDiscountVoucher;
import com.programmers.order.domain.Voucher;
import com.programmers.order.dto.CustomerDto;
import com.programmers.order.dto.VoucherDto;
import com.programmers.order.repository.customer.CustomerRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

	private CustomerService customerService;

	private CustomerRepository customerRepository;

	private CustomerVoucherService customerVoucherService;

	private Customer demoCustomer;
	private Voucher demoVoucher;

	@BeforeEach
	void init() {
		customerRepository = Mockito.mock(CustomerRepository.class);
		customerVoucherService = Mockito.mock(CustomerVoucherService.class);
		customerService = new CustomerService(customerRepository, customerVoucherService);
		demoCustomer = new Customer(UUID.randomUUID(), "Museok", "MM@programmers.co.kr", LocalDateTime.now());
		demoVoucher = FixedAmountVoucher.create(1000);
	}

	@DisplayName("회원 생성")
	@Test
	void testSave() {
		// given
		CustomerDto.SaveRequestDto saveRequestDto = new CustomerDto.SaveRequestDto(
				List.of(demoCustomer.getName(), demoCustomer.getEmail())
		);
		BDDMockito.given(customerRepository.insert(Mockito.any())).willReturn(demoCustomer);
		// when
		Optional<Customer> savedCustomer = customerService.save(saveRequestDto);
		// then
		Assertions.assertNotNull(savedCustomer);
		MatcherAssert.assertThat(savedCustomer.get(), is(demoCustomer));

	}

	@DisplayName("고객 이름 이름 업데이트")
	@Test
	void testUpdate() {

		// given
		Faker faker = new Faker();

		Customer givenCustomer = new Customer(UUID.randomUUID(), faker.name().username(),
				faker.name().lastName() + "@programmers.co.kr",
				LocalDateTime.now());

		String updatingName = faker.name().username();
		CustomerDto.UpdateCustomer givenUpdateCustomerDto = new CustomerDto.UpdateCustomer(givenCustomer.getEmail(),
				updatingName);

		BDDMockito.given(customerRepository.findByEmail(givenUpdateCustomerDto.getEmail()))
				.willReturn(Optional.of(givenCustomer));
		BDDMockito.given(customerRepository.update(givenCustomer))
				.willReturn(givenCustomer.changeName(givenUpdateCustomerDto));

		Optional<Customer> expectedCustomer = customerService.update(givenUpdateCustomerDto);

		Assertions.assertNotNull(expectedCustomer);
		MatcherAssert.assertThat(expectedCustomer.get(), samePropertyValuesAs(givenCustomer));
	}

	@DisplayName("고객 다건 조회")
	@Test
	void testFindAll() {
		//given
		Faker faker = new Faker();
		List<Customer> customers = makeCustomers(faker);
		BDDMockito.given(customerRepository.findAll()).willReturn(customers);

		//when
		List<Customer> findingCustomers = customerService.findAll();

		//then
		Assertions.assertNotNull(findingCustomers);
		AtomicInteger index = new AtomicInteger();
		findingCustomers.forEach(findCustomer -> {
			MatcherAssert.assertThat(findCustomer,
					samePropertyValuesAs(customers.get(index.getAndIncrement())));
		});
	}

	@DisplayName("고객 이메일 조회")
	@Test
	void testFindByEmail() {
		// given
		BDDMockito.given(customerRepository.findByEmail(demoCustomer.getEmail())).willReturn(Optional.of(demoCustomer));

		// when
		Optional<Customer> findCustomerByEmail = customerService.findByEmail(demoCustomer.getEmail());
		// then
		Assertions.assertNotNull(findCustomerByEmail);

		MatcherAssert.assertThat(findCustomerByEmail.get(), samePropertyValuesAs(demoCustomer));

	}

	@Test
	void testFindById() {

		// given
		BDDMockito.given(customerRepository.findById(demoCustomer.getCustomerId()))
				.willReturn(Optional.of(demoCustomer));

		// when
		Optional<Customer> findCustomerByEmail = customerService.findById(demoCustomer.getCustomerId().toString());
		// then
		Assertions.assertNotNull(findCustomerByEmail);

		MatcherAssert.assertThat(findCustomerByEmail.get(), samePropertyValuesAs(demoCustomer));
	}

	@DisplayName("특정 고객 나만의 바우처 등록")
	@Test
	void testRegisterVoucher() {
		CustomerVoucher customerVoucher = new CustomerVoucher(UUID.randomUUID(), demoCustomer.getCustomerId(),
				demoVoucher.getVoucherId(), LocalDateTime.now());
		BDDMockito.given(customerVoucherService.findVoucherById(demoVoucher.getVoucherId()))
				.willReturn(Optional.of(demoVoucher));
		BDDMockito.given(customerRepository.findByEmail(demoCustomer.getEmail())).willReturn(Optional.of(demoCustomer));
		BDDMockito.given(
						customerVoucherService.isDuplicatePublish(demoCustomer.getCustomerId(), demoVoucher.getVoucherId()))
				.willReturn(false);
		BDDMockito.given(customerVoucherService.save(demoCustomer.getCustomerId(), demoVoucher.getVoucherId()))
				.willReturn(Optional.of(customerVoucher.getId()));

		CustomerDto.RegisterVoucherDto registerVoucherDto = new CustomerDto.RegisterVoucherDto(
				List.of(
						demoCustomer.getEmail(), demoVoucher.getVoucherId().toString()
				)
		);
		Optional<UUID> uuid = customerService.registerVoucher(registerVoucherDto);

		Assertions.assertNotNull(uuid);
		Assertions.assertEquals(uuid.get(), customerVoucher.getId());

	}

	@DisplayName("고객이 가지고 있는 바우처 조회")
	@Test
	void testLookUpWithVouchers() {
		Faker faker = new Faker();

		List<Voucher> vouchers = Stream.generate(() -> {
			return (Voucher)PercentDiscountVoucher.create(
					faker.number().numberBetween(1, 100));
		}).limit(5).toList();

		BDDMockito.given(customerRepository.findByEmail(demoCustomer.getEmail())).willReturn(Optional.of(demoCustomer));
		BDDMockito.given(customerVoucherService.getVouchersForCustomer(demoCustomer.getCustomerId()))
				.willReturn(vouchers);

		List<VoucherDto.Response> responses = customerService.lookUpWithVouchers(demoCustomer.getEmail());

		AtomicInteger index = new AtomicInteger();
		responses.forEach(response -> {
			MatcherAssert.assertThat(response.getId(),
					Matchers.is(vouchers.get(index.getAndIncrement()).getVoucherId()));
		});

	}

	@DisplayName("고객이 가지고 있는 쿠폰 삭제")
	@Test
	void testUnMappingVoucher() {
		BDDMockito.given(customerRepository.findByEmail(demoCustomer.getEmail())).willReturn(Optional.of(demoCustomer));
		customerService.unMappingVoucher(demoCustomer.getEmail(), demoVoucher.getVoucherId());
	}

	private List<Customer> makeCustomers(Faker faker) {
		List<Customer> customers = Stream.generate(() -> {
					return new Customer(UUID.randomUUID(), faker.name().username(),
							faker.name().lastName() + "@programmers.co.kr", LocalDateTime.now());
				}).limit(10)
				.toList();
		return customers;
	}
}