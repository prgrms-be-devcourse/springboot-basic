package com.example.voucher.domain.voucher.repository;

import com.example.voucher.domain.voucher.Voucher;
import com.example.voucher.domain.voucher.VoucherType;
import com.example.voucher.util.FileUtils;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.stream.Collectors;
import static com.example.voucher.domain.voucher.VoucherType.EMPTY;
import static com.example.voucher.exception.ErrorMessage.*;

@Repository
public class VoucherFileRepository implements VoucherRepository {

	private static final String PATH = "voucherList.csv";

	@Override
	public Voucher save(Voucher voucher) {
		if (voucher == null) {
			// TODO: 로그 남기기
			throw new IllegalArgumentException(SERVER_ERROR.name());
		}
		if (voucher.getVoucherId() == null) {
			VoucherType voucherType = VoucherType.of(voucher.getClass().getSimpleName());
			if (voucherType == EMPTY) {
				// TODO: 로그 남기기
				throw new IllegalArgumentException(SERVER_ERROR.name());
			}
			voucher = voucherType.create(VoucherIdGenerator.generateVoucherId(), voucher.getDiscountAmount());
		}
		FileUtils.writeFile(PATH, voucher.toString()+"\n");
		return voucher;
	}

	@Override
	public List<Voucher> findAll() {
		return FileUtils.readFile(PATH).stream()
				.map((s -> {
					String[] split = s.split(",");
					return convertVoucher(split[0], split[1], split[2]);
				}))
				.collect(Collectors.toList());
	}

	private Voucher convertVoucher(String voucherTypeString, String voucherId, String discountAmount) {
		VoucherType voucherType = VoucherType.of(voucherTypeString);
		if (voucherType == EMPTY) {
			// TODO: 로그 남기기
			throw new IllegalArgumentException(FILE_CONTENT_ERROR.name());
		}

		try {
			return voucherType.create(Long.valueOf(voucherId), Integer.valueOf(discountAmount));
		} catch (NumberFormatException e) {
			// TODO: 로그 남기기
			throw new IllegalArgumentException(FILE_CONTENT_ERROR.name());
		}
	}

	@Override
	public void deleteAll() {
	}
}