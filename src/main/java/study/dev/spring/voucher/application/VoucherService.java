package study.dev.spring.voucher.application;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import study.dev.spring.voucher.application.dto.CreateVoucherRequest;
import study.dev.spring.voucher.application.dto.VoucherInfo;
import study.dev.spring.voucher.domain.Voucher;
import study.dev.spring.voucher.domain.VoucherRepository;
import study.dev.spring.voucher.domain.VoucherType;

@Service
@RequiredArgsConstructor
public class VoucherService {

	private final VoucherRepository voucherRepository;

	//값을 하나하나빼는 로직을 따로 빼서 사용
	public void createVoucher(final CreateVoucherRequest request) {
		Voucher newVoucher = Voucher.of(
			VoucherType.valueOf(request.voucherType()),
			request.name(),
			request.discountAmount()
		);

		voucherRepository.save(newVoucher);
	}

	public List<VoucherInfo> findAllVouchers() {
		return voucherRepository.findAll()
			.stream()
			.map(voucher ->
				new VoucherInfo(
					voucher.getName(),
					voucher.getTypeDescription(),
					voucher.getDiscountAmount()
				)
			).toList();
	}
}
