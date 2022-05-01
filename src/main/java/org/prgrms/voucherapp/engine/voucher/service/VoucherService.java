package org.prgrms.voucherapp.engine.voucher.service;

import org.prgrms.voucherapp.engine.voucher.entity.Voucher;
import org.prgrms.voucherapp.engine.voucher.repository.VoucherRepository;
import org.prgrms.voucherapp.engine.wallet.repository.WalletRepository;
import org.prgrms.voucherapp.exception.NullVoucherException;
import org.prgrms.voucherapp.global.enums.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final WalletRepository walletRepository;
    private final static Logger logger = LoggerFactory.getLogger(VoucherService.class);

    public VoucherService(VoucherRepository voucherRepository, WalletRepository walletRepository) {
        this.voucherRepository = voucherRepository;
        this.walletRepository = walletRepository;
    }

    public List<Voucher> getVouchersByFilter(Optional<VoucherType> voucherType, Optional<LocalDateTime> after, Optional<LocalDateTime> before) {
        return voucherRepository.findByFilter(voucherType, before, after);
    }

    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new NullVoucherException(MessageFormat.format("{0}는 존재하지 않는 바우처 id입니다.", voucherId)));
    }

    @Transactional
    public Voucher createVoucher(VoucherType type, UUID uuid, long amount) {
        while (voucherRepository.findById(uuid).isPresent()) {
            uuid = UUID.randomUUID();
        }
        Voucher voucher = type.createVoucher(uuid, amount);
        return voucherRepository.insert(voucher);
    }

    /*
     * getVoucherListByStr : voucherRepository에 있는 모든 voucher들을 string으로 만들어 프린트합니다.
     * */
    public String getVoucherListByStr() {
        StringBuilder sb = new StringBuilder();
        for (Voucher voucher : voucherRepository.findAll()) {
            sb.append(voucher.toString()).append("\n");
        }
        if (sb.isEmpty()) return "Voucher Repository is empty.";
        sb.deleteCharAt(sb.lastIndexOf("\n"));
        return sb.toString();
    }

    @Transactional
    //TODO : Service Layer에서 remove 메소드의 매개변수 일관성 깨짐 어떤거는 Id, 어떤거는 entity
    public void removeVoucher(UUID voucherId) {
        Voucher oldVoucher = this.getVoucher(voucherId);
        voucherRepository.deleteById(voucherId);
        walletRepository.deleteByVoucherId(voucherId);
        logger.info("--- 삭제된 바우처 정보 --- \n%s".formatted(oldVoucher));
    }

    @Transactional
    // TODO : 타입이 기존 바우처와 같은 경우 change 메소드로 속성 변경, 타입이 다른 경우에만 바우처 생성.
    public void updateVoucher(UUID voucherId, VoucherType newVoucherType, long newDiscountAmount) {
        Voucher oldVoucher = this.getVoucher(voucherId);
        Voucher newVoucher = voucherRepository.update(newVoucherType.createVoucher(voucherId, newDiscountAmount));
        logger.info("--- 수정된 바우처 정보 ---\n변경 전 : %s\n변경 후 : %s".formatted(oldVoucher, newVoucher));
    }

}
