package com.prgmrs.voucher.service;

import com.prgmrs.voucher.dto.request.AssignVoucherRequest;
import com.prgmrs.voucher.dto.request.RemoveVoucherRequest;
import com.prgmrs.voucher.dto.response.WalletResponse;
import com.prgmrs.voucher.model.User;
import com.prgmrs.voucher.model.Wallet;
import com.prgmrs.voucher.model.wrapper.Username;
import com.prgmrs.voucher.repository.UserRepository;
import com.prgmrs.voucher.repository.WalletRepository;
import com.prgmrs.voucher.util.UUIDConverter;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WalletService {
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    public WalletService(UserRepository userRepository, WalletRepository walletRepository) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
    }

    public WalletResponse assignVoucher(AssignVoucherRequest assignVoucherRequest) {
        User user = userRepository.findByUsername(new Username(assignVoucherRequest.username()));
        Wallet wallet = new Wallet(user.userId(), UUIDConverter.fromString(assignVoucherRequest.voucherUuid()));
        walletRepository.save(wallet);

        return new WalletResponse(wallet.voucherId(), user.username().value());
    }

    public WalletResponse removeVoucher(RemoveVoucherRequest removeVoucherRequest) {
        UUID convertedUuid = UUIDConverter.fromString(removeVoucherRequest.voucherUuid());
        User user = userRepository.getUserByVoucherId(convertedUuid);
        Wallet wallet = new Wallet(user.userId(), convertedUuid);
        walletRepository.remove(wallet);

        return new WalletResponse(wallet.voucherId(), user.username().value());
    }
}
