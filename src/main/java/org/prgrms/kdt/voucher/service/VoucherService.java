package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.voucher.domain.VoucherDto;
import org.prgrms.kdt.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.prgrms.kdt.wallet.Wallet;
import org.prgrms.kdt.wallet.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.prgrms.kdt.voucher.VoucherMessage.*;
import static org.prgrms.kdt.voucher.VoucherMessage.EXCEPTION_FIXED_AMOUNT_OVER;
import static org.prgrms.kdt.voucher.domain.VoucherType.FIXED;
import static org.prgrms.kdt.voucher.domain.VoucherType.PERCENT;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final WalletRepository walletRepository;
    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);

    public VoucherService(VoucherRepository voucherRepository, WalletRepository walletRepository) {
        this.voucherRepository = voucherRepository;
        this.walletRepository = walletRepository;
    }

    public void createFixedAmountVoucher(int amount) {
        VoucherDto voucherDto = new VoucherDto(UUID.randomUUID(), amount, FIXED.getType(), LocalDateTime.now());
        voucherRepository.save(voucherDto);
    }

    public void createPercentDiscountVoucher(int amount) {
        VoucherDto voucherDto = new VoucherDto(UUID.randomUUID(), amount, PERCENT.getType(), LocalDateTime.now());
        voucherRepository.save(voucherDto);
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> {
                    String errorMessage = MessageFormat.format( "{0}" + EXCEPTION_FIND_VOUCHER.getMessage(), voucherId);
                    logger.error(errorMessage);
                    return new RuntimeException(errorMessage);
                });
    }

    public List<Voucher> getAllVouchers() {
        var voucherList = voucherRepository.findAll();
        if (voucherList.isEmpty()) {
            System.out.println(VOUCHER_IS_EMPTY.getMessage());
        }
        return voucherList;
    }

    public Optional<Wallet> getOwner(UUID voucherId) {
        return walletRepository.findByVoucherId(voucherId.toString());
    }

    public void removeVoucherById(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }

    public List<Voucher> getVouchersByCriteria(LocalDateTime startDate, LocalDateTime endDate, String voucherType) {
        List<Voucher> vouchersByCriteria = new ArrayList<>();
        List<Voucher> allVouchers = voucherRepository.findAll();

        for (Voucher voucher : allVouchers) {
            if ((voucher.getCreatedAt().isAfter(startDate) || voucher.getCreatedAt().isEqual(startDate))
                    && (voucher.getCreatedAt().isBefore(endDate) || voucher.getCreatedAt().isEqual(endDate))
                    && voucher.getType().equals(voucherType)) {
                vouchersByCriteria.add(voucher);
            }
        }
        return vouchersByCriteria;
    }
}
