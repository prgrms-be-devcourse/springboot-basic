package com.programmers.order.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.order.domain.Voucher;
import com.programmers.order.dto.CustomerDto;
import com.programmers.order.exception.DomainException;
import com.programmers.order.message.ErrorMessage;
import com.programmers.order.repository.voucher.VoucherRepository;
import com.programmers.order.utils.TranslatorUtils;

@Transactional(readOnly = true)
@Service
public class VoucherService {

	public static final int NOT_EXIST = -1;
	public static final int EXIST = 1;
	private final WalletService walletService;
	private final VoucherRepository voucherRepository;

	public VoucherService(WalletService walletService, VoucherRepository voucherRepository) {
		this.walletService = walletService;
		this.voucherRepository = voucherRepository;
	}

	@Transactional
	public Voucher save(Voucher voucher) {
		return voucherRepository.insert(voucher);
	}

	public List<Voucher> lookUp() {
		return voucherRepository.findAll();
	}

	public boolean hasNotVoucher(String voucherId) {
		UUID id = UUID.fromString(voucherId);
		Optional<Voucher> voucher = voucherRepository.findById(id);

		return voucherRepository.exsitsByVocuher(id) == NOT_EXIST;
	}

	public boolean hasVoucher(String voucherId) {
		UUID id = UUID.fromString(voucherId);
		boolean isExist = true;
		return voucherRepository.exsitsByVocuher(id) == EXIST;
	}

	public Optional<Voucher> findById(UUID voucherId) {
		return voucherRepository.findById(voucherId);
	}

	public List<CustomerDto.ResponseDto> lookUpForCustomer(String voucherId) {
		return walletService.getCustomerForVoucher(UUID.fromString(voucherId))
				.stream()
				.map(customer -> new CustomerDto.ResponseDto(customer.getEmail(), customer.getName()))
				.toList();
	}

	public void delete(String voucherId) {
		UUID voucherIdentity = TranslatorUtils.toUUID(voucherId.getBytes());
		Optional<Voucher> voucher = findById(voucherIdentity);

		if (voucher.isEmpty()) {
			throw new DomainException.NotFoundResource(ErrorMessage.CLIENT_ERROR);
		}

		voucherRepository.delete(voucherIdentity);
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
