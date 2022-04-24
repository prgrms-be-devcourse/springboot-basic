package org.prgrms.kdt.service;

import org.prgrms.kdt.io.OutputConsole;
import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.VoucherType;
import org.prgrms.kdt.repository.CustomerJdbcRepository;
import org.prgrms.kdt.repository.JdbcWalletRepository;
import org.prgrms.kdt.repository.voucher.VoucherRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    private final CustomerJdbcRepository customerRepository;

    private final JdbcWalletRepository jdbcWalletRepository;

    public VoucherService(VoucherRepository voucherRepository, CustomerJdbcRepository customerRepository, JdbcWalletRepository jdbcWalletRepository) {
        this.voucherRepository = voucherRepository;
        this.customerRepository = customerRepository;
        this.jdbcWalletRepository = jdbcWalletRepository;
    }

    public Voucher createVoucher(UUID voucherId, int voucherTypeNumber, int discountAmount) {
        Voucher voucher = getVoucherTypeByNumber(voucherTypeNumber).createNewVoucher(voucherId, discountAmount, LocalDateTime.now());
        voucherRepository.insertVoucher(voucher);
        return voucher;
    }

    public Map getVoucherList() {
        return voucherRepository.getVoucherList();
    }

    public void deleteVoucher(UUID voucherId, String email) {
        try {
            jdbcWalletRepository.getVoucherByVoucherIdAndEmail(voucherId, email);
            voucherRepository.deleteVoucherById(voucherId);
            OutputConsole.printMessage("voucher is deleted");
        } catch (EmptyResultDataAccessException e) {
            OutputConsole.printMessage("WRONG : invalid input");
        }
    }

    public List<Voucher> getOwnableVoucherList() {
        return voucherRepository.getVoucherListOwnerIdIsEmpty();
    }

    public Optional<Voucher> provideVoucherToCustomer(String voucherId, String customerId) {
        try {
            voucherRepository.getVoucherNotProvided(UUID.fromString(voucherId));
            customerRepository.findCustomerById(UUID.fromString(customerId));
        } catch (Exception e) {
            OutputConsole.printMessage("WRONG : check voucherId and customerId again\n");
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
