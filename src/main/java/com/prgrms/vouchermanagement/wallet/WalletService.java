package com.prgrms.vouchermanagement.wallet;

import java.util.List;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.prgrms.vouchermanagement.commons.exception.AlreadyExistException;
import com.prgrms.vouchermanagement.commons.exception.NotExistException;
import com.prgrms.vouchermanagement.customer.domain.Customer;
import com.prgrms.vouchermanagement.customer.service.CustomerService;
import com.prgrms.vouchermanagement.voucher.VoucherService;
import com.prgrms.vouchermanagement.voucher.domain.Voucher;

public class WalletService {
	private final WalletJdbcRepository walletRepository;
	private final CustomerService customerService;
	private final VoucherService voucherService;

	public WalletService(WalletJdbcRepository walletRepository,
		CustomerService customerService, VoucherService voucherService) {

		this.walletRepository = walletRepository;
		this.customerService = customerService;
		this.voucherService = voucherService;
	}

	@Transactional
	public List<Voucher> getAllVouchersOfCustomer(UUID customerId) {
		checkEmptyCustomerId(customerId);

		return walletRepository.findAllVouchersOfCustomer(customerId);
	}

	@Transactional
	public void disableVoucherOfCustomer(UUID customerId, UUID voucherId) throws NotExistException {
		checkEmptyCustomerId(customerId);
		checkEmptyVoucherId(voucherId);

		checkCustomerHavingThisVoucher(customerId, voucherId);

		walletRepository.deleteRecord(customerId, voucherId);
	}

	@Transactional
	public void publishVoucherToCustomer(UUID customerId, UUID voucherId) {
		checkEmptyCustomerId(customerId);
		checkEmptyVoucherId(voucherId);

		voucherService.getById(voucherId);
		customerService.getById(customerId);
		checkCustomerNotHavingThisVoucher(customerId, voucherId);

		walletRepository.insertRecord(customerId, voucherId);
	}

	@Transactional
	public List<Customer> getAllOwnersOfVoucher(UUID voucherId) {
		checkEmptyVoucherId(voucherId);

		return walletRepository.findAllCustomersOfVoucher(voucherId);
	}

	// 해당 커스토머가 이 바우처를 갖고 있지 않으면 예외를 던진다
	@Transactional(readOnly = true)
	void checkCustomerHavingThisVoucher(UUID customerId, UUID voucherId) {
		walletRepository.findVoucherOfCustomer(customerId, voucherId)
			.orElseThrow(NotExistException::new);
	}

	// 해당 커스토머가 이 바우처를 이미 갖고 있으면 예외를 던진다
	@Transactional(readOnly = true)
	void checkCustomerNotHavingThisVoucher(UUID customerId, UUID voucherId) {
		if (walletRepository.findVoucherOfCustomer(customerId, voucherId)
			.isPresent()) {
			throw new AlreadyExistException();
		}
	}

	@Transactional(readOnly = true)
	void checkExistingCustomer(UUID customerId) {
		customerService.getById(customerId);
	}

	private void checkEmptyCustomerId(UUID customerId) {
		Assert.notNull(customerId, "customerId 는 null 일 수 없습니다");
	}

	private void checkEmptyVoucherId(UUID voucherId) {
		Assert.notNull(voucherId, "voucherId 는 null 일 수 없습니다");
	}
}
