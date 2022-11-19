package org.prgrms.kdt.service;

import org.prgrms.kdt.dao.entity.customer.Customer;
import org.prgrms.kdt.dao.entity.voucher.Voucher;
import org.prgrms.kdt.dao.entity.voucher.VoucherBuilder;
import org.prgrms.kdt.dao.entity.voucher.VoucherType;
import org.prgrms.kdt.dao.repository.voucher.VoucherRepository;
import org.prgrms.kdt.exception.repository.NotPresentInRepositoryException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        String voucherType = VoucherType.getStringClassName(voucherTypeNumber);
        Voucher voucher = voucherBuilder.create()
                .setDiscountAmount(discountValue)
                .setVoucherType(voucherType)
                .build();

        voucherRepository.insert(voucher);
        return voucher;
    }

    public Voucher findVoucherById(String voucherId) {
        try {
            return voucherRepository.findById(UUID.fromString(voucherId))
                    .orElseThrow(() -> new NotPresentInRepositoryException("입력된 voucher ID가 존재하지 않습니다."));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("입력된 voucher ID가 UUID 형식이 아닙니다.", e);
        }
    }

    public Voucher assignVoucher(Voucher voucher, Customer ownedCustomer) {
        voucher.setOwnedCustomerId(ownedCustomer.getCustomerId());
        return voucherRepository.update(voucher);
    }

    public List<Voucher> getOwnedVouchers(String customerId) {
        try {
            return voucherRepository.getAllStoredVoucher().stream()
                    .filter(voucher -> voucher.getOwnedCustomerId().isPresent() && voucher.getOwnedCustomerId().get().equals(UUID.fromString(customerId)))
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("입력된 voucher ID가 UUID 형식이 아닙니다.", e);
        }
    }

    public Voucher removeAssignment(String voucherId) {
        try {
            Voucher voucherById = findVoucherById(voucherId);
            voucherById.setOwnedCustomerId(null);
            return voucherRepository.update(voucherById);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("입력된 voucher ID가 UUID 형식이 아닙니다.", e);
        }
    }
}
