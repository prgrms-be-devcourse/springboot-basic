package org.prgrms.kdt.shop.service;

import org.prgrms.kdt.shop.domain.Voucher;
import org.prgrms.kdt.shop.enums.VoucherType;
import org.prgrms.kdt.shop.repository.VoucherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Optional<Voucher> findVoucherById(UUID voucherId) {
        return Optional.ofNullable(voucherRepository.findById(voucherId).orElseThrow(( ) -> new RuntimeException("Can not find a voucher for%s".formatted(voucherId))));
    }

    public List<Voucher> findVoucherByType(VoucherType voucherType) {
        return voucherRepository.findByType(voucherType);
    }

    public Voucher addVoucher(Voucher voucher) {
        return voucherRepository.insert(voucher);
    }

    public void deleteVoucherById(UUID uuid) {
        voucherRepository.delete(uuid);
    }

    public Voucher updateVoucher(Voucher voucher) {
        return voucherRepository.update(voucher);
    }

    public void deleteAllVoucher( ) {
        voucherRepository.deleteAll();
    }

    public List<Voucher> findAllVoucher( ) {
        return voucherRepository.findAll();
    }

    public void printAll( ) {
        List<Voucher> voucherList = voucherRepository.findAll();
        if (voucherList.isEmpty()) {
            System.out.println("바우처가 없습니다.");
        } else {
            voucherList.forEach(s -> System.out.println("Voucher Id : " + s.getVoucherId() + "  Discount Info : " + s.getAmount() + " VoucherType : " + s.getVoucherType()));
        }
    }
}
