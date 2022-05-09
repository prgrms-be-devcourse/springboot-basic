package org.prgrms.kdt.shop.service;

import org.prgrms.kdt.shop.domain.FixedAmountVoucher;
import org.prgrms.kdt.shop.domain.PercentDiscountVoucher;
import org.prgrms.kdt.shop.domain.Voucher;
import org.prgrms.kdt.shop.dto.VoucherCreateServiceDto;
import org.prgrms.kdt.shop.dto.VoucherGetByTypeServiceDto;
import org.prgrms.kdt.shop.enums.VoucherType;
import org.prgrms.kdt.shop.repository.VoucherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher findVoucherById(UUID voucherId) {
        return voucherRepository.findById(voucherId).orElseThrow(
            () -> new IllegalArgumentException(
                "Can not find a voucher for%s".formatted(voucherId)));
    }

    public List<Voucher> findVoucherByType(VoucherGetByTypeServiceDto voucherGetByTypeServiceDto) {
        return voucherRepository.findByType(voucherGetByTypeServiceDto.getType());
    }

    public Voucher addVoucher(VoucherCreateServiceDto voucherCreateServiceDto) {
        if (voucherCreateServiceDto.getType() == VoucherType.FIXED_AMOUNT) {
            return voucherRepository.insert(
                new FixedAmountVoucher(voucherCreateServiceDto.getVoucherId(),
                    voucherCreateServiceDto.getAmount(), voucherCreateServiceDto.getCreatedAt()));
        }
        return voucherRepository.insert(
            new PercentDiscountVoucher(voucherCreateServiceDto.getVoucherId(),
                voucherCreateServiceDto.getAmount(), voucherCreateServiceDto.getCreatedAt()));
    }

    public void deleteVoucherById(UUID uuid) {
        voucherRepository.delete(uuid);
    }

    public void deleteAllVoucher() {
        voucherRepository.deleteAll();
    }

    public List<Voucher> findAllVoucher() {
        return voucherRepository.findAll();
    }

    public void printAll() {
        List<Voucher> voucherList = voucherRepository.findAll();
        if (voucherList.isEmpty()) {
            System.out.println("바우처가 없습니다.");
        } else {
            voucherList.forEach(s -> System.out.println(
                "Voucher Id : " + s.getVoucherId() + "  Discount Info : " + s.getAmount()
                    + " VoucherType : " + s.getVoucherType()));
        }
    }
}
