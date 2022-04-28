package com.programmers.order.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.order.domain.Voucher;
import com.programmers.order.dto.CustomerDto;
import com.programmers.order.repository.voucher.VoucherRepository;

@Transactional(readOnly = true)
@Service
public class VoucherService {

	private final CustomerVoucherService customerVoucherService;
	private final VoucherRepository voucherRepository;

	public VoucherService(CustomerVoucherService customerVoucherService, VoucherRepository voucherRepository) {
		this.customerVoucherService = customerVoucherService;
		this.voucherRepository = voucherRepository;
	}

	@Transactional
	public Voucher save(Voucher voucher) {
		return voucherRepository.saveVoucher(voucher);
	}

	public List<Voucher> lookUp() {
		return voucherRepository.getVouchers();
	}

	public boolean isNotExist(String voucherId) {
		UUID uuid = UUID.fromString(voucherId);
		Optional<Voucher> voucher = voucherRepository.findById(uuid);

		return voucher.isEmpty();
	}

	public Optional<Voucher> findById(UUID voucherId) {
		return voucherRepository.findById(voucherId);
	}

	public List<CustomerDto.ResponseDto> lookUpForCustomer(String voucherId) {
		return customerVoucherService.getCustomerForVoucher(voucherId)
				.stream()
				.map(customer -> new CustomerDto.ResponseDto(customer.getEmail(), customer.getName()))
				.toList();
	}
}
/**
 * 단일 레포이거나 아예 없거나
 * 다중 레포는 절대 안된다.
 * 왜냐하면 책임이 없다.
 * 치킨 서비스에서 족발레포를 가져온다.
 * 족발 레시피가 필요해서
 * 어떤 값을 가져오거나 기능을 가져와야함.
 * 레포는 하나의 단일 레포
 * 책임!!! 의존이 영향 책임. 평행은 절대 안만나다. 교차 십자가
 */
