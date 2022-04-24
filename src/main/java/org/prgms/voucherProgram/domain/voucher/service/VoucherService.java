package org.prgms.voucherProgram.domain.voucher.service;

import java.util.List;
import java.util.UUID;

import org.prgms.voucherProgram.domain.customer.domain.Customer;
import org.prgms.voucherProgram.domain.customer.domain.Email;
import org.prgms.voucherProgram.domain.customer.exception.CustomerIsNotExistsException;
import org.prgms.voucherProgram.domain.customer.service.CustomerService;
import org.prgms.voucherProgram.domain.voucher.domain.Voucher;
import org.prgms.voucherProgram.domain.voucher.domain.VoucherType;
import org.prgms.voucherProgram.domain.voucher.dto.VoucherRequest;
import org.prgms.voucherProgram.domain.voucher.exception.VoucherIsNotExistsException;
import org.prgms.voucherProgram.domain.voucher.repository.VoucherRepository;
import org.prgms.voucherProgram.domain.wallet.dto.WalletRequest;
import org.prgms.voucherProgram.domain.wallet.exception.AlreadyAssignException;
import org.prgms.voucherProgram.domain.wallet.exception.NotFoundVoucherException;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    public static final String ERROR_VOUCHER_IS_NOT_ASSIGN = "[ERROR] 해당 바우처는 아직 할당전 입니다.";

    private final VoucherRepository voucherRepository;
    private final CustomerService customerService;

    public VoucherService(VoucherRepository voucherRepository, CustomerService customerService) {
        this.voucherRepository = voucherRepository;
        this.customerService = customerService;
    }

    public Voucher create(VoucherRequest voucherRequest) {
        Voucher voucher = toEntity(UUID.randomUUID(), voucherRequest);
        return voucherRepository.save(voucher);
    }

    private Voucher toEntity(UUID voucherId, VoucherRequest voucherRequest) {
        return VoucherType.findByNumber(voucherRequest.getType())
            .constructor(voucherId, null, voucherRequest.getDiscountValue());
    }

    public Voucher modify(UUID voucherId, VoucherRequest voucherRequest) {
        findVoucher(voucherId);

        Voucher voucher = toEntity(voucherId, voucherRequest);
        return voucherRepository.update(voucher);
    }

    public Voucher findVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId).orElseThrow(() -> {
            throw new VoucherIsNotExistsException();
        });
    }

    public List<Voucher> findAllVoucher() {
        return voucherRepository.findAll();
    }

    public void delete(UUID voucherId) {
        voucherRepository.findById(voucherId)
            .ifPresentOrElse(voucher -> voucherRepository.deleteById(voucherId), () -> {
                throw new VoucherIsNotExistsException();
            });
    }

    public Voucher assignVoucher(WalletRequest walletRequest) {
        Voucher voucher = findVoucher(walletRequest.getVoucherId());
        validateAssign(voucher);
        Customer customer = findCustomer(walletRequest.getCustomerEmail());

        voucher.assignCustomer(customer.getCustomerId());
        return voucherRepository.assignCustomer(voucher);
    }

    public List<Voucher> findAssignVouchers(String customerEmail) {
        Customer customer = findCustomer(customerEmail);
        return voucherRepository.findByCustomerEmail(customer.getEmail());
    }

    public void deleteAssignVoucher(WalletRequest walletRequest) {
        Customer customer = findCustomer(walletRequest.getCustomerEmail());

        Voucher voucher = voucherRepository.findByCustomerId(customer.getCustomerId()).stream()
            .filter(findVoucher -> findVoucher.isSameVoucher(walletRequest.getVoucherId()))
            .findFirst()
            .orElseThrow(NotFoundVoucherException::new);

        voucherRepository.deleteById(voucher.getVoucherId());
    }

    public Customer findCustomer(UUID voucherId) {
        Voucher voucher = findVoucher(voucherId);
        validateNotAssign(voucher);
        return customerService.findByVoucherId(voucherId);
    }

    private void validateNotAssign(Voucher voucher) {
        if (voucher.isNotAssign()) {
            throw new CustomerIsNotExistsException(ERROR_VOUCHER_IS_NOT_ASSIGN);
        }
    }

    private void validateAssign(Voucher voucher) {
        if (voucher.isAssign()) {
            throw new AlreadyAssignException();
        }
    }

    private Customer findCustomer(String requestEmail) {
        Email email = new Email(requestEmail);
        return customerService.findByEmail(email);
    }
}
