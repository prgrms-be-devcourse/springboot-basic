package com.programmers.order.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.programmers.order.domain.Customer;
import com.programmers.order.dto.CustomerDto;
import com.programmers.order.dto.VoucherDto;
import com.programmers.order.io.Input;
import com.programmers.order.io.Output;
import com.programmers.order.message.BasicMessage;
import com.programmers.order.message.ErrorMessage;
import com.programmers.order.service.CustomerService;
import com.programmers.order.type.DomainMenu.CustomerMenuType;
import com.programmers.order.type.ProgramType;
import com.programmers.order.utils.TranslatorUtils;

@Component("Customer")
public class CustomerController implements Controller {

	private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
	private static final String DEFAULT_DELIMITER = ",";
	private static final String EMPTY_STRING = "";
	private static final String LINE = "\n";
	private final Input input;
	private final Output output;
	private final CustomerService customerService;

	public CustomerController(Input input, Output output, CustomerService customerService) {
		this.input = input;
		this.output = output;
		this.customerService = customerService;
	}

	@Override
	public void run() {
		CustomerMenuType menuType = CustomerMenuType.NONE;

		while (menuType.isReEnter()) {
			String menu = input.read(BasicMessage.Customer.CUSTOMER_INIT);
			menuType = CustomerMenuType.of(menu);

			switch (menuType) {
				case CREATE -> {
					create();
				}
				case LIST_UP_WITH_VOUCHER -> {
					listUpWithVoucher();
				}
				case REGISTER -> {
					registerVoucher();
				}
				case UNMAPPING -> {
					unMappingVoucher();
				}
				case NONE -> {
					output.write(ErrorMessage.CLIENT_ERROR);
					log.error("error : {}", ErrorMessage.CLIENT_ERROR);
					continue;
				}
			}

			output.write(BasicMessage.CommonMessage.NEW_LINE);
		}

		output.write(BasicMessage.CommonMessage.EXIT);
	}

	private void unMappingVoucher() {
		boolean isRenter = true;
		String email = EMPTY_STRING;

		do {
			email = input.read(BasicMessage.Customer.CUSTOMER_UN_MAPPING_EMAIL);
			isRenter = customerService.notExistByEmail(email);
		} while (isRenter);

		List<VoucherDto.Response> responses = customerService.lookUpWithVouchers(email);
		String voucherBundles = responses
				.stream()
				.map(VoucherDto.Response::show)
				.collect(Collectors.joining(LINE));

		output.write(voucherBundles);

		Set<UUID> validVoucherIds = responses.stream()
				.map(VoucherDto.Response::getId)
				.collect(Collectors.toSet());
		String voucher = EMPTY_STRING;
		UUID voucherIdentity = new UUID(0, 0);

		do {
			voucher = input.read(BasicMessage.Customer.CUSTOMER_UN_MAPPING_VOUCHER);
			voucherIdentity = TranslatorUtils.toUUID(voucher.getBytes());
			isRenter = !validVoucherIds.contains(voucherIdentity);
		} while (isRenter);

		customerService.unMappingVoucher(email, voucherIdentity);

	}

	private void registerVoucher() {
		boolean isRenter = true;

		do {
			String[] informationBundles = input.read(BasicMessage.Customer.CUSTOMER_REGISTER_COUPON).split(DEFAULT_DELIMITER);
			CustomerDto.RegisterVoucherDto registerVoucherDto = getCustomerDtoConverter().convert(informationBundles);
			Optional<UUID> customerVoucherDto = customerService.registerVoucher(registerVoucherDto);

			isRenter = customerVoucherDto.isEmpty();

			if (isRenter) {
				output.write(ErrorMessage.INTERNAL_PROGRAM_ERROR);
				continue;
			}
		} while (isRenter);

		output.write(BasicMessage.Customer.CUSTOMER_REGISTER_COMPLETE);
	}

	private void listUpWithVoucher() {
		boolean isRenter = true;
		String email = EMPTY_STRING;

		do {
			email = input.read(BasicMessage.Customer.CUSTOMER_LIST_UP_WITH_VOUCHER);
			isRenter = customerService.notExistByEmail(email);

			if (isRenter) {
				output.write(BasicMessage.Customer.CUSTOMER_NOT_EXIST_EMAIL);
			}

		} while (isRenter);

		List<VoucherDto.Response> responses = customerService.lookUpWithVouchers(email);

		if (responses.isEmpty()) {
			output.write(BasicMessage.CommonMessage.NOT_EXIST_DATE);
			return;
		}

		String voucherBundles = responses.stream()
				.map(VoucherDto.Response::show)
				.collect(Collectors.joining(LINE, LINE, LINE));

		output.write(voucherBundles);

	}

	//todo : validation check
	private void create() {
		boolean isRenter = true;

		do {
			String customerInformation = input.read(BasicMessage.Customer.CUSTOMER_CREATE);
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
