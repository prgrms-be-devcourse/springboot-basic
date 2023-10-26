package study.dev.spring.voucher.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import study.dev.spring.voucher.application.dto.CreateVoucherRequest;
import study.dev.spring.voucher.application.dto.VoucherInfo;
import study.dev.spring.voucher.application.dto.VoucherMapper;
import study.dev.spring.voucher.domain.Voucher;
import study.dev.spring.voucher.domain.VoucherRepository;
import study.dev.spring.voucher.domain.VoucherType;
import study.dev.spring.wallet.domain.Wallet;
import study.dev.spring.wallet.domain.WalletRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VoucherService {

	private final VoucherRepository voucherRepository;
	private final WalletRepository walletRepository;

	@Transactional
	public void createVoucher(final CreateVoucherRequest request) {
		Voucher newVoucher = Voucher.of(
			VoucherType.valueOf(request.voucherType()),
			request.name(),
			request.discountAmount()
		);

		voucherRepository.save(newVoucher);
	}

	public List<VoucherInfo> getAllVouchers() {
		return voucherRepository.findAll()
			.stream()
			.map(VoucherMapper::toVoucherInfo)
			.toList();
	}

	public List<VoucherInfo> getVouchersByCustomer(String customerId) {
		List<String> voucherIds = walletRepository.findByCustomerId(customerId)
			.stream()
			.map(Wallet::getVoucherId)
			.toList();

		return voucherRepository.findByIds(voucherIds)
			.stream()
			.map(VoucherMapper::toVoucherInfo)
			.toList();
	}
}
