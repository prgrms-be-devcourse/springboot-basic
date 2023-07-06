package co.programmers.voucher_management.voucher.service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import co.programmers.voucher_management.common.Response;
import co.programmers.voucher_management.customer.dto.CustomerResponseDTO;
import co.programmers.voucher_management.customer.entity.Customer;
import co.programmers.voucher_management.customer.repository.CustomerRepository;
import co.programmers.voucher_management.exception.EmptyAssignerException;
import co.programmers.voucher_management.exception.NoSuchDataException;
import co.programmers.voucher_management.voucher.dto.VoucherAssignDTO;
import co.programmers.voucher_management.voucher.dto.VoucherRequestDTO;
import co.programmers.voucher_management.voucher.dto.VoucherResponseDTO;
import co.programmers.voucher_management.voucher.dto.VoucherUpdateDTO;
import co.programmers.voucher_management.voucher.entity.Voucher;
import co.programmers.voucher_management.voucher.repository.VoucherRepository;

@Service
public class VoucherService {
	private final VoucherRepository voucherRepository;
	private final CustomerRepository customerRepository;

	public VoucherService(VoucherRepository voucherRepository, CustomerRepository customerRepository) {
		this.voucherRepository = voucherRepository;
		this.customerRepository = customerRepository;
	}

	public Response create(VoucherRequestDTO voucherRequestDTO) {
		Voucher voucher = new Voucher(voucherRequestDTO);
		voucherRepository.create(voucher);
		return new Response(Response.State.SUCCESS, List.of());
	}

	public Response inquiryVoucherOf() {
		List<Voucher> inquiredData = voucherRepository.findAll();
		List<VoucherResponseDTO> voucherResponseDTOs = inquiredData.stream()
				.map(VoucherResponseDTO::new)
				.collect(Collectors.toList());
		return new Response(Response.State.SUCCESS, voucherResponseDTOs);
	}

	public Response inquiryVoucherOf(long customerId) {
		List<Voucher> inquiredData = voucherRepository.findByCustomerId(customerId);
		List<VoucherResponseDTO> voucherResponseDTOs = inquiredData.stream()
				.map(VoucherResponseDTO::new)
				.collect(Collectors.toList());
		return new Response(Response.State.SUCCESS, voucherResponseDTOs);
	}

	public Response update(VoucherUpdateDTO voucherUpdateDTO) {
		Voucher voucher = new Voucher(voucherUpdateDTO);
		voucherRepository.update(voucher);
		return new Response(Response.State.SUCCESS, List.of());
	}

	public Response deleteById(Long id) {
		voucherRepository.deleteById(id);
		return new Response(Response.State.SUCCESS, List.of());

	}

	public Response assignVoucher(VoucherAssignDTO voucherAssignDTO) {
		long voucherId = voucherAssignDTO.getVoucherId();
		long customerId = voucherAssignDTO.getCustomerId();
		Optional<Voucher> voucher = voucherRepository.findById(voucherId);
		Optional<Customer> customer = customerRepository.findById(customerId);

		if (voucher.isEmpty() || customer.isEmpty()) {
			throw new NoSuchDataException("Invalid Input");
		}
		voucher.get().assignCustomer(customerId);
		voucherRepository.assignCustomer(voucher.get(), customer.get());
		return new Response(Response.State.SUCCESS, List.of());
	}

	public Response inquiryByVoucher(long voucherId) {
		Voucher voucher = voucherRepository.findById(voucherId)
				.orElseThrow(
						() -> new NoSuchDataException(MessageFormat.format("No such voucher of id {0}", voucherId)));
		long customerId = voucher.getCustomerId();
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new EmptyAssignerException(
						MessageFormat.format("No customer assigned to voucher of id {0}", voucherId)));
		CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO(customer);
		return new Response(Response.State.SUCCESS, customerResponseDTO);
	}
}
