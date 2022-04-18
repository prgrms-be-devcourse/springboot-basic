package org.prgms.voucherProgram.service;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.UUID;

import org.prgms.voucherProgram.domain.customer.Customer;
import org.prgms.voucherProgram.domain.customer.Email;
import org.prgms.voucherProgram.domain.voucher.Voucher;
import org.prgms.voucherProgram.dto.CustomerDto;
import org.prgms.voucherProgram.dto.VoucherDto;
import org.prgms.voucherProgram.dto.WalletRequestDto;
import org.prgms.voucherProgram.dto.WalletVoucherDto;
import org.prgms.voucherProgram.exception.AlreadyAssignException;
import org.prgms.voucherProgram.exception.CustomerIsNotExistsException;
import org.prgms.voucherProgram.exception.NotFoundVoucherException;
import org.prgms.voucherProgram.exception.VoucherIsNotExistsException;
import org.prgms.voucherProgram.repository.customer.CustomerRepository;
import org.prgms.voucherProgram.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    public static final String ERROR_VOUCHER_IS_NOT_ASSIGN = "[ERROR] 해당 바우처는 아직 할당전 입니다.";
    private final VoucherRepository voucherRepository;
    private final CustomerRepository customerRepository;

    public VoucherService(VoucherRepository voucherRepository, CustomerRepository customerRepository) {
        this.voucherRepository = voucherRepository;
        this.customerRepository = customerRepository;
    }

    public VoucherDto create(VoucherDto voucherDto) {
        Voucher voucher = voucherDto.toEntity();
        return VoucherDto.from(voucherRepository.save(voucher));
    }

    public VoucherDto update(VoucherDto voucherDto) {
        Voucher voucher = voucherDto.toEntity();
        voucherRepository.findById(voucherDto.getVoucherId()).orElseThrow(() -> {
            throw new VoucherIsNotExistsException();
        });
        return VoucherDto.from(voucherRepository.update(voucher));
    }

    public List<VoucherDto> findAllVoucher() {
        return voucherRepository.findAll().stream()
            .map(VoucherDto::from)
            .collect(toList());
    }

    public void delete(UUID voucherId) {
        voucherRepository.findById(voucherId)
            .ifPresentOrElse(voucher -> voucherRepository.deleteById(voucherId), () -> {
                throw new VoucherIsNotExistsException();
            });
    }

    public WalletVoucherDto assignVoucher(WalletRequestDto walletRequestDto) {
        Voucher voucher = findVoucher(walletRequestDto.getVoucherId());
        validateAssign(voucher);
        Customer customer = findCustomer(walletRequestDto.getCustomerEmail());

        voucher.assignCustomer(customer.getCustomerId());
        return WalletVoucherDto.from(voucherRepository.assignCustomer(voucher));
    }

    public List<WalletVoucherDto> findAssignVouchers(String customerEmail) {
        Customer customer = findCustomer(customerEmail);

        return voucherRepository.findByCustomerId(customer.getCustomerId()).stream()
            .map(WalletVoucherDto::from)
            .collect(toList());
    }

    public void deleteAssignVoucher(WalletRequestDto walletRequestDto) {
        Customer customer = findCustomer(walletRequestDto.getCustomerEmail());

        Voucher voucher = voucherRepository.findByCustomerId(customer.getCustomerId()).stream()
            .filter(findVoucher -> findVoucher.isSameVoucher(walletRequestDto.getVoucherId()))
            .findFirst()
            .orElseThrow(NotFoundVoucherException::new);

        voucherRepository.deleteById(voucher.getVoucherId());
    }

    public CustomerDto findCustomer(UUID voucherId) {
        Voucher voucher = findVoucher(voucherId);
        validateNotAssign(voucher);

        Customer customer = customerRepository.findById(voucher.getCustomerId())
            .orElseThrow(CustomerIsNotExistsException::new);
        return CustomerDto.from(customer);
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
        return customerRepository.findByEmail(email.getEmail()).orElseThrow(() -> {
            throw new CustomerIsNotExistsException();
        });
    }

    private Voucher findVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId).orElseThrow(() -> {
            throw new VoucherIsNotExistsException();
        });
    }
}
