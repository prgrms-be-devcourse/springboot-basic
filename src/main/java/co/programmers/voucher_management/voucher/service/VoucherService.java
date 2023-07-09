package co.programmers.voucher_management.voucher.service;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import co.programmers.voucher_management.common.Response;
import co.programmers.voucher_management.customer.entity.Customer;
import co.programmers.voucher_management.customer.repository.CustomerRepository;
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
		Voucher created = voucherRepository.create(voucher);
		return new Response(Response.State.SUCCESS, created);
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
		Voucher updated = voucherRepository.update(voucher);
		return new Response(Response.State.SUCCESS, updated);
	}

	public Response deleteById(Long id) {
		voucherRepository.deleteById(id);
		return new Response(Response.State.SUCCESS, List.of());

	}

	public Response assignVoucher(VoucherAssignDTO voucherAssignDTO) {
		long voucherId = voucherAssignDTO.getVoucherId();
		long customerId = voucherAssignDTO.getCustomerId();
		Voucher voucher = voucherRepository.findById(voucherId)
				.orElseThrow(
						() -> new NoSuchDataException(MessageFormat.format("No such voucher of id {0}", voucherId)));
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(
						() -> new NoSuchDataException(MessageFormat.format("No such customer of id {0}", customerId)));
		voucher.assignCustomer(customerId);
		Voucher assigned = voucherRepository.assignCustomer(voucher, customer);
		return new Response(Response.State.SUCCESS, assigned);
	}

}
