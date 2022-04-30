package com.example.voucher.domain.voucher.repository;

import com.example.voucher.domain.voucher.Voucher;
import com.example.voucher.domain.voucher.VoucherType;
import com.example.voucher.util.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import static com.example.voucher.domain.voucher.VoucherType.EMPTY;
import static com.example.voucher.exception.ErrorMessage.*;

@Repository
public class VoucherFileRepository implements VoucherRepository {

	private final String path;

	public VoucherFileRepository(@Value("${voucher-file-path}") String path) {
		this.path = path;
	}

	@PostConstruct
	public void init() {
		FileUtils.deleteFile(path);
	}

	@Override
	public Voucher save(Voucher voucher) {
		if (voucher == null) {
			// TODO: 로그 남기기
			throw new IllegalArgumentException(SERVER_ERROR.name());
		}

		VoucherType voucherType = voucher.getVoucherType();
		if(voucherType == EMPTY) {
			// TODO: 로그 남기기
			throw new IllegalArgumentException(SERVER_ERROR.name());
		}

		if (voucher.getVoucherId() == null) {
			Voucher createdVoucher = voucherType.create(VoucherIdGenerator.generateVoucherId(), voucher.getDiscountAmount());
			FileUtils.writeFile(path, createdVoucher+"\n");
			return createdVoucher;
		}

		FileUtils.writeFile(path, voucher+"\n");
		return voucher;
	}

	@Override
	public List<Voucher> findAll() {
		return FileUtils.readFile(path).stream()
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
			Long convertedVoucherId = Long.valueOf(voucherId);
			int convertedDiscountAmount = Integer.valueOf(discountAmount);
			return voucherType.create(convertedVoucherId, convertedDiscountAmount);
		} catch (NumberFormatException e) {
			// TODO: 로그 남기기
			throw new IllegalArgumentException(FILE_CONTENT_ERROR.name());
		}
	}

	@Override
	public void deleteAll() {
	}
}