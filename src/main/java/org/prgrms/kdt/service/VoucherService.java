package org.prgrms.kdt.service;

import org.prgrms.kdt.io.OutputConsole;
import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.VoucherType;
import org.prgrms.kdt.repository.CustomerRepository;
import org.prgrms.kdt.repository.JdbcWalletRepository;
import org.prgrms.kdt.repository.VoucherRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {

    VoucherRepository voucherRepository;

    CustomerRepository customerRepository;

    JdbcWalletRepository jdbcWalletRepository;

    public VoucherService(VoucherRepository voucherRepository, CustomerRepository customerRepository, JdbcWalletRepository jdbcWalletRepository) {
        this.voucherRepository = voucherRepository;
        this.customerRepository = customerRepository;
        this.jdbcWalletRepository = jdbcWalletRepository;
    }

    public Voucher createVoucher(UUID voucherId, int voucherTypeNumber, int discountAmount) {
        Voucher voucher = getVoucherTypeByNumber(voucherTypeNumber).newVoucher(voucherId, discountAmount, LocalDateTime.now());
        voucherRepository.insert(voucher);
        return voucher;
    }

    public Map getVoucherList() {
        return voucherRepository.getVoucherList();
    }

    public void deleteVoucher(UUID voucherId, String email) {
        try {
            jdbcWalletRepository.getVoucherByVoucherIdAndEmail(voucherId, email);
            voucherRepository.delete(voucherId);
            new OutputConsole().printMessage("voucher is deleted");
        } catch (EmptyResultDataAccessException e) {
            new OutputConsole().printMessage("WRONG : invalid input");
        }
    }

    public Voucher getVoucherById(Voucher voucher) {
        return voucherRepository.getByVoucherId(voucher.getVoucherId());
    }

    public List<Voucher> getOwnableVoucherList() {
        return voucherRepository.getVoucherListOwnerIdIsEmpty();
    }

    public Optional<Voucher> provideVoucherToCustomer(String voucherId, String customerId) {
        try {
            voucherRepository.getByVoucherNotProvided(UUID.fromString(voucherId));
            customerRepository.findById(UUID.fromString(customerId));
        } catch (Exception e) {
            new OutputConsole().printMessage("WRONG : check voucherId and customerId again\n");
            return Optional.empty();
        }
        return Optional.of(voucherRepository.updateVoucherOwner(UUID.fromString(voucherId), UUID.fromString(customerId)));
    }

    private VoucherType getVoucherTypeByNumber(int voucherTypeNumber) {
        Optional<VoucherType> voucherType = VoucherType.getVoucherTypeByNumber(voucherTypeNumber);
        if (voucherType.isEmpty()) {
            throw new IllegalArgumentException("WRONG : input right voucher type");
        }
        return voucherType.get();
    }
}
