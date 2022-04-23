package org.prgrms.kdt.service;

import org.prgrms.kdt.io.OutputConsole;
import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.VoucherType;
import org.prgrms.kdt.repository.CustomerRepository;
import org.prgrms.kdt.repository.VoucherRepository;
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

    public VoucherService(VoucherRepository voucherRepository, CustomerRepository customerRepository) {
        this.voucherRepository = voucherRepository;
        this.customerRepository = customerRepository;
    }

    public Voucher createVoucher(UUID voucherId, int voucherTypeNumber, int discountAmount) {
        Voucher voucher = getVoucherTypeByNumber(voucherTypeNumber).newVoucher(voucherId, discountAmount, LocalDateTime.now());
        voucherRepository.insert(voucher);
        return voucher;
    }

    public Map getVoucherList() {
        return voucherRepository.getVoucherList();
    }

    public Voucher deleteVoucher(Voucher voucher) {
        return voucherRepository.delete(voucher);
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
