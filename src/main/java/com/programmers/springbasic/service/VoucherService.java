package com.programmers.springbasic.service;

import static com.programmers.springbasic.constants.ErrorCode.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.programmers.springbasic.dto.CustomerDto;
import com.programmers.springbasic.dto.VoucherDto;
import com.programmers.springbasic.entity.customer.Customer;
import com.programmers.springbasic.entity.voucher.FixedAmountVoucher;
import com.programmers.springbasic.entity.voucher.PercentDiscountVoucher;
import com.programmers.springbasic.entity.voucher.Voucher;
import com.programmers.springbasic.entity.voucher.VoucherType;
import com.programmers.springbasic.repository.customer.CustomerRepository;
import com.programmers.springbasic.repository.voucher.VoucherRepository;
import com.programmers.springbasic.repository.wallet.WalletRepository;

@Service
public class VoucherService {

	private final VoucherRepository voucherRepository;
	private final CustomerRepository customerRepository;
	private final WalletRepository walletRepository;

	public VoucherService(VoucherRepository voucherRepository, CustomerRepository customerRepository,
		WalletRepository walletRepository) {
		this.voucherRepository = voucherRepository;
		this.customerRepository = customerRepository;
		this.walletRepository = walletRepository;
	}

	public List<VoucherDto> getVouchers() {
		List<Voucher> vouchers = voucherRepository.findAll();
		return vouchers.stream()
			.map(VoucherDto::from)
			.toList();
	}

	public VoucherDto createVoucher(VoucherType voucherType, long discountValue) {
		Voucher voucher = switch (voucherType) {
			case FIXED_AMOUNT -> new FixedAmountVoucher(UUID.randomUUID(), discountValue);
			case PERCENT_DISCOUNT -> new PercentDiscountVoucher(UUID.randomUUID(), discountValue);
		};
		voucherRepository.insert(voucher);
		return VoucherDto.from(voucher);
	}

	public VoucherDto getVoucherDetail(UUID voucherId) {
		Voucher voucher = voucherRepository.findById(voucherId)
			.orElseThrow(() -> new NoSuchElementException(VOUCHER_NOT_FOUND.getMessage()));
		return VoucherDto.from(voucher);
	}

	public VoucherDto updateVoucher(UUID uuid, long newDiscountValue) {
		Voucher voucher = voucherRepository.findById(uuid)
			.orElseThrow(() -> new NoSuchElementException(VOUCHER_NOT_FOUND.getMessage()));
		voucher.changeDiscountValue(newDiscountValue);
		voucherRepository.update(voucher);
		return VoucherDto.from(voucher);
	}

	public void deleteVoucher(UUID voucherId) {
		Voucher voucher = voucherRepository.findById(voucherId)
			.orElseThrow(() -> new NoSuchElementException(VOUCHER_NOT_FOUND.getMessage()));
		voucherRepository.deleteById(voucher.getVoucherId());
	}

	public List<CustomerDto> getCustomersByVoucher(UUID voucherId) {
		Voucher voucher = voucherRepository.findById(voucherId)
			.orElseThrow(() -> new NoSuchElementException(VOUCHER_NOT_FOUND.getMessage()));

		List<UUID> customerIds = walletRepository.findCustomerIdsByVoucherId(voucher.getVoucherId());

		List<Customer> customers = customerRepository.findAllById(customerIds);
		return customers.stream()
			.map(CustomerDto::from)
			.toList();
	}

}
