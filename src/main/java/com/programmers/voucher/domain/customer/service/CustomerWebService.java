package com.programmers.voucher.domain.customer.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.programmers.voucher.domain.customer.model.Customer;
import com.programmers.voucher.domain.customer.repository.CustomerRepository;
import com.programmers.voucher.web.controller.customer.dto.CustomerRequestDto;
import com.programmers.voucher.web.controller.customer.dto.CustomerResponseDto;

@Service
public class CustomerWebService {

	private final CustomerRepository repository;

	public CustomerWebService(CustomerRepository repository) {
		this.repository = repository;
	}

	public CustomerResponseDto createCustomer(CustomerRequestDto customerRequestDto) {
		Customer customer = convertDtoToCustomer(customerRequestDto);
		repository.save(customer);
		CustomerResponseDto customerResponseDto = convertCustomerToDto(customer);
		return customerResponseDto;
	}

	public CustomerResponseDto findById(UUID customerId) {
		Customer customer = repository.findById(customerId);
		CustomerResponseDto customerResponseDto = convertCustomerToDto(customer);
		return customerResponseDto;
	}

	public List<CustomerResponseDto> getAllCustomer() {
		List<Customer> customers = repository.findAll();
		List<CustomerResponseDto> customerResponseDtoList = new ArrayList<>();
		customers.stream()
			.forEach(customer -> {
				CustomerResponseDto customerResponseDto = convertCustomerToDto(customer);
				customerResponseDtoList.add(customerResponseDto);
			});
		return customerResponseDtoList;
	}

	public List<CustomerResponseDto> getBlacklist() {
		List<Customer> blacklists = repository.findAllBlacklist();
		List<CustomerResponseDto> customerResponseDtoList = new ArrayList<>();
		blacklists.stream()
			.forEach(customer -> {
				CustomerResponseDto customerResponseDto = convertCustomerToDto(customer);
				customerResponseDtoList.add(customerResponseDto);
			});
		return customerResponseDtoList;
	}

	public void modifyCustomer(UUID customerId, CustomerRequestDto customerRequestDto) {
		Customer customer = repository.findById(customerId);
		Customer updatedCustomer = new Customer(customerId, customer.getCreatedAt(),
			customerRequestDto.getCustomerType(),
			LocalDateTime.now());
		repository.update(updatedCustomer);
	}

	public void removeCustomer(UUID customerId) {
		repository.deleteById(customerId);
	}

	private Customer convertDtoToCustomer(CustomerRequestDto customerRequestDto) {
		Customer customer = new Customer(UUID.randomUUID(), LocalDateTime.now(), customerRequestDto.getCustomerType(),
			LocalDateTime.now());
		return customer;
	}

	private CustomerResponseDto convertCustomerToDto(Customer customer) {
		return new CustomerResponseDto(customer.getCustomerId(), customer.getCreatedAt(), customer.getCustomerType(),
			customer.getLastModifiedAt());
	}
}
