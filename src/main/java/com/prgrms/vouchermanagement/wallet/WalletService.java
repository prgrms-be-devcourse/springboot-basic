package com.prgrms.vouchermanagement.wallet;

import java.util.List;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.prgrms.vouchermanagement.commons.exception.AlreadyExistException;
import com.prgrms.vouchermanagement.commons.exception.NotExistException;
import com.prgrms.vouchermanagement.customer.domain.Customer;
import com.prgrms.vouchermanagement.customer.service.CustomerService;
import com.prgrms.vouchermanagement.voucher.VoucherService;
import com.prgrms.vouchermanagement.voucher.domain.Voucher;

public class WalletService {
	private final WalletRepository walletRepository;
	private final CustomerService customerService;
	private final VoucherService voucherService;

	public WalletService(WalletRepository walletRepository,
		CustomerService customerService, VoucherService voucherService) {
		this.walletRepository = walletRepository;
		this.customerService = customerService;
		this.voucherService = voucherService;
	}

	public List<Voucher> getAllVouchersOfCustomer(UUID customerId) {
		checkEmpty(customerId);

		return walletRepository.findAllVouchersOfCustomer(customerId);
	}

	// 해당 커스토머가 이 바우처를 갖고 있지 않으면 예외를 던진다
	@Transactional(readOnly = true)
	protected void checkCustomerHavingThisVoucher(UUID customerId, UUID voucherId) {
		walletRepository.findVoucherOfCustomer(customerId, voucherId)
			.orElseThrow(NotExistException::new);
	}

	// 해당 커스토머가 이 바우처를 이미 갖고 있으면 예외를 던진다
	@Transactional(readOnly = true)
	protected void checkCustomerNotHavingThisVoucher(UUID customerId, UUID voucherId) {
		if (walletRepository.findVoucherOfCustomer(customerId, voucherId)
			.isPresent()) {
			throw new AlreadyExistException();
		}
	}

	private void checkEmpty(UUID id) {
		if (id == null) {
			throw new IllegalArgumentException("id 는 null 이 오면 안됩니다");
		}
	}

	@Transactional
	public void disableVoucherOfCustomer(UUID customerId, UUID voucherId) throws NotExistException {
		checkEmpty(customerId);
		checkEmpty(voucherId);

		checkCustomerHavingThisVoucher(customerId, voucherId);

		walletRepository.deleteRecord(customerId, voucherId);
	}

	@Transactional
	public void publishVoucherToCustomer(UUID customerId, UUID voucherId) {
		checkEmpty(customerId);
		checkEmpty(voucherId);

		checkCustomerNotHavingThisVoucher(customerId, voucherId);
		voucherService.getById(voucherId)
			.orElseThrow(NotExistException::new);

		walletRepository.insertRecord(customerId, voucherId);
	}

	public List<Customer> getAllOwnersOfVoucher(UUID voucherId) {
		checkEmpty(voucherId);

		return walletRepository.findAllCustomersOfVoucher(voucherId);
	}
}
