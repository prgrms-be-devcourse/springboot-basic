package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.prgrms.kdt.wallet.Wallet;
import org.prgrms.kdt.wallet.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.prgrms.kdt.voucher.VoucherMessage.EXCEPTION_FIND_VOUCHER;
import static org.prgrms.kdt.voucher.VoucherMessage.VOUCHER_IS_EMPTY;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final WalletRepository walletRepository;
    private final FixedAmountVoucherService fixedAmountVoucherService;
    private final PercentDiscountVoucherService percentDiscountVoucherService;
    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);

    public VoucherService(
            VoucherRepository voucherRepository,
            WalletRepository walletRepository,
            FixedAmountVoucherService fixedAmountVoucherService,
            PercentDiscountVoucherService percentDiscountVoucherService
    ) {
        this.voucherRepository = voucherRepository;
        this.walletRepository = walletRepository;
        this.fixedAmountVoucherService = fixedAmountVoucherService;
        this.percentDiscountVoucherService = percentDiscountVoucherService;
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

    public void useVoucher(Voucher voucher) {
    }
}
