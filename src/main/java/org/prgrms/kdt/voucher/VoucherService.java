package org.prgrms.kdt.voucher;

import org.prgrms.kdt.exception.NotPresentInRepositoryException;
import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.VoucherBuilder;
import org.prgrms.kdt.model.voucher.VoucherType;
import org.prgrms.kdt.util.ConvertUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherBuilder voucherBuilder;

    public VoucherService(VoucherRepository voucherRepository, VoucherBuilder voucherBuilder) {
        this.voucherRepository = voucherRepository;
        this.voucherBuilder = voucherBuilder;
    }

    public void clear() {
        voucherRepository.clear();
    }

    public List<Voucher> getAllVouchers() {
        return voucherRepository.getAllStoredVoucher();
    }

    public Voucher create(String voucherTypeNumber, String discountValue) {
        VoucherType voucherType = VoucherType.getStringClassName(voucherTypeNumber);
        Voucher voucher = voucherBuilder.create()
                .setDiscountAmount(discountValue)
                .setVoucherType(voucherType)
                .build();

        voucherRepository.insert(voucher);
        return voucher;
    }

    public Voucher findVoucherById(String voucherId) {
        return voucherRepository.findById(ConvertUtil.toUUID(voucherId))
                .orElseThrow(() -> new NotPresentInRepositoryException("입력된 voucher ID가 존재하지 않습니다."));
    }

    public Voucher assignVoucher(Voucher voucher, Customer ownedCustomer) {
        voucher.setOwnedCustomerId(ownedCustomer.getCustomerId());
        return voucherRepository.update(voucher);
    }

    public List<Voucher> getOwnedVouchers(String customerId) {
        return voucherRepository.getAllStoredVoucher().stream()
                .filter(voucher -> voucher.getOwnedCustomerId().isPresent() && voucher.getOwnedCustomerId().get().equals(ConvertUtil.toUUID(customerId)))
                .toList();

    }

    public Voucher removeAssignment(String voucherId) {
        Voucher voucher = findVoucherById(voucherId);
        voucher.removeOwnedCustomer();
        return voucherRepository.update(voucher);
    }
}
