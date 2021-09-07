package org.prgrms.kdt.wallet;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.prgrms.kdt.customer.CustomerDto;
import org.prgrms.kdt.exception.IllegalRowUpdateException;
import org.prgrms.kdt.mapper.CustomerMapper;
import org.prgrms.kdt.mapper.VoucherMapper;
import org.prgrms.kdt.voucher.VoucherDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by yhh1056
 * Date: 2021/09/04 Time: 12:24 오후
 */
@Service
public class WalletService {
    private static final Logger logger = LoggerFactory.getLogger(WalletService.class);

    private final WalletJdbcRepository walletJdbcRepository;

    public WalletService(WalletJdbcRepository walletJdbcRepository) {
        this.walletJdbcRepository = walletJdbcRepository;
    }

    @Transactional
    public void addWallet(WalletDto walletDto) {
        Wallet wallet = new Wallet(
                UUID.randomUUID(),
                UUID.fromString(walletDto.getCustomerId()),
                UUID.fromString(walletDto.getVoucherId()));
        int insert = walletJdbcRepository.insert(wallet);

        if (insert != 1) {
            logger.error("invalid update query");
            throw new IllegalRowUpdateException("wrong access");
        }
    }

    @Transactional
    public void removeWallet(WalletDto walletDto) {
        walletJdbcRepository.deleteBy(UUID.fromString(walletDto.getCustomerId()), UUID.fromString(walletDto.getVoucherId()));
    }
}
