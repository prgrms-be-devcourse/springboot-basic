package com.programmers.order.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.programmers.order.domain.Customer;
import com.programmers.order.dto.CustomerDto;
import com.programmers.order.dto.IntegrationDto;
import com.programmers.order.dto.VocuherDto;
import com.programmers.order.io.Input;
import com.programmers.order.io.Output;
import com.programmers.order.message.BasicMessage;
import com.programmers.order.message.ErrorMessage;
import com.programmers.order.service.CustomerService;
import com.programmers.order.service.VoucherService;
import com.programmers.order.type.DomainMenu.CustomerMenuType;
import com.programmers.order.type.ProgramType;

@Component("Customer")
public class CustomerController implements Controller {

	private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
	private static final String DEFAULT_DELIMITER = ",";
	private static final String EMPTY_STRING = "";
	private final Input input;
	private final Output output;

	private final CustomerService customerService;
	private final VoucherService voucherService;

	public CustomerController(Input input, Output output, CustomerService customerService,
			VoucherService voucherService) {
		this.input = input;
		this.output = output;
		this.customerService = customerService;
		this.voucherService = voucherService;
	}

	@Override
	public void run() {
		CustomerMenuType menuType = CustomerMenuType.NONE;

		while (menuType.isReEnter()) {
			String menu = input.read(BasicMessage.CUSTOMER_INIT);
			menuType = CustomerMenuType.of(menu);

			switch (menuType) {
				case CREATE -> {
					create();
				}
				case LIST_UP_WITH_VOUCHER -> {
					listUpWithVoucher();
				}
				case LIST_UP_WITH_CUSTOMER -> {
					listUpWithCustomer();
				}
				case REGISTER -> {
					RegisterVoucher();
				}
				case UNMAPPING -> {
					UnMappingVoucher();
				}
				case NONE -> {
					output.write(ErrorMessage.CLIENT_ERROR);
					log.error("error : {}", ErrorMessage.CLIENT_ERROR);
					continue;
				}
			}

			output.write(BasicMessage.NEW_LINE);
		}

		output.write(BasicMessage.EXIT);
	}

	private void listUpWithCustomer() {
		boolean isReEnter = true;
		String voucherId = EMPTY_STRING;
		do {
			voucherId = input.read(BasicMessage.VOUCHER_LIST_UP_WITH_CUSTOMER);
			isReEnter = customerService.isNotExist(voucherId);
		} while (isReEnter);

		customerService.getCustomers(voucherId);
	}

	private void UnMappingVoucher() {
	}

	private void RegisterVoucher() {
		boolean isRenter = true;

		do {
			String registrationInformation = input.read(BasicMessage.CUSTOMER_REGISTER_COUPON);
			String[] informationBundles = registrationInformation.split(DEFAULT_DELIMITER);
			CustomerDto.RegisterVoucherDto registerVoucherDto = getCustomerDtoConverter().convert(informationBundles);
			Optional<IntegrationDto.SaveRequestDto> customerVoucherDto = customerService.registerVoucher(
					registerVoucherDto);
			isRenter = customerVoucherDto.isEmpty();

			if (isRenter) {
				output.write(ErrorMessage.INTERNAL_PROGRAM_ERROR);
				continue;
			}
		} while (isRenter);
	}

	private void listUpWithVoucher() {
		boolean isRenter = true;
		String email = EMPTY_STRING;

		do {
			email = input.read(BasicMessage.CUSTOMER_LIST_UP_WITH_VOUCHER);
			isRenter = customerService.notExistByEmail(email);
		} while (isRenter);

		List<VocuherDto.Response> responses = customerService.lookUpWithVouchers(email);

		if (responses.isEmpty()) {
			output.write(BasicMessage.NOT_EXIST_DATE);
			return;
		}

		String voucherBundles = responses.stream()
				.map(Object::toString)
				.collect(Collectors.joining("\n", "--start--\n", "--end--\n"));

		output.write(voucherBundles);

	}

	// todo : validation check
	private void create() {
		boolean isRenter = true;

		do {
			String customerInformation = input.read(BasicMessage.CUSTOMER_CREATE);
			String[] informationBundles = customerInformation.split(DEFAULT_DELIMITER);
			CustomerDto.SaveRequestDto requestDto = getSaveRequestDtoConverter().convert(informationBundles);
			Optional<Customer> savedCustomer = customerService.save(requestDto);
			isRenter = savedCustomer.isEmpty();

			if (isRenter) {
				output.write(ErrorMessage.INTERNAL_PROGRAM_ERROR);
			}

		} while (isRenter);
	}

	private Converter<String[], CustomerDto.SaveRequestDto> getSaveRequestDtoConverter() {
		return (value) -> {
			List<String> information = Arrays.stream(value)
					.map(String::trim)
					.toList();

			return new CustomerDto.SaveRequestDto(information);
		};
	}

	private Converter<String[], CustomerDto.RegisterVoucherDto> getCustomerDtoConverter() {
		return source -> {
			List<String> information = Arrays.stream(source)
					.map(String::trim)
					.toList();

			return new CustomerDto.RegisterVoucherDto(information);
		};
	}

	@Override
	public ProgramType getType() {
		return ProgramType.CUSTOMER;
	}
}
