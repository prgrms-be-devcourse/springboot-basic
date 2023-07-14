package co.programmers.voucher_management.voucher.service;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

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

	public VoucherResponseDTO create(VoucherRequestDTO voucherRequestDTO) {
		Voucher voucher = voucherRequestDTO.mapToVoucher();
		Voucher created = voucherRepository.create(voucher);
		return new VoucherResponseDTO(created);
	}
	public VoucherResponseDTO findById(long voucherId){
		Voucher voucher = voucherRepository.findById(voucherId)
				.orElseThrow(() -> new NoSuchDataException(MessageFormat.format("No such voucher of id {0}", voucherId)));
		return new VoucherResponseDTO(voucher);
	}


	public List<VoucherResponseDTO> inquiryVoucherOf() {
		List<Voucher> inquiredData = voucherRepository.findAll();
		return inquiredData.stream()
				.map(VoucherResponseDTO::new)
				.collect(Collectors.toList());
	}

	public List<VoucherResponseDTO> inquiryVoucherOf(long customerId) {
		List<Voucher> inquiredData = voucherRepository.findByCustomerId(customerId);
		return inquiredData.stream()
				.map(VoucherResponseDTO::new)
				.collect(Collectors.toList());
	}

	public VoucherResponseDTO update(VoucherUpdateDTO voucherUpdateDTO) {
		Voucher voucher = voucherUpdateDTO.mapToVoucher();
		Voucher updated = voucherRepository.update(voucher);
		return new VoucherResponseDTO(updated);
	}

	public void deleteById(Long id) {
		voucherRepository.deleteById(id);
	}

	public VoucherResponseDTO assignVoucher(VoucherAssignDTO voucherAssignDTO) {
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
		return new VoucherResponseDTO(assigned);
	}

}
