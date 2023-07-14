package com.prgmrs.voucher.service;

import com.prgmrs.voucher.dto.request.WalletRequest;
import com.prgmrs.voucher.dto.response.WalletResponse;
import com.prgmrs.voucher.enums.WalletAssignmentSelectionType;
import com.prgmrs.voucher.exception.WrongRangeFormatException;
import com.prgmrs.voucher.model.User;
import com.prgmrs.voucher.model.Voucher;
import com.prgmrs.voucher.model.Wallet;
import com.prgmrs.voucher.model.validator.OrderValidator;
import com.prgmrs.voucher.model.validator.UserValidator;
import com.prgmrs.voucher.repository.UserRepository;
import com.prgmrs.voucher.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletService {
    private final UserValidator userValidator;
    private final OrderValidator orderValidator;
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    public WalletService(UserValidator userValidator, OrderValidator orderValidator, UserRepository userRepository, WalletRepository walletRepository) {
        this.userValidator = userValidator;
        this.orderValidator = orderValidator;
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
    }

    public WalletResponse assignVoucher(WalletRequest walletRequest) throws WrongRangeFormatException {
        return getWalletResponse(walletRequest, WalletAssignmentSelectionType.ASSIGN_VOUCHER);
    }

    public WalletResponse freeVoucher(WalletRequest walletRequest) {
        return getWalletResponse(walletRequest, WalletAssignmentSelectionType.REMOVE_VOUCHER);
    }

    private WalletResponse getWalletResponse(WalletRequest walletRequest, WalletAssignmentSelectionType walletAssignmentSelectionType) {
        String username = walletRequest.username();
        String order = walletRequest.order();
        List<Voucher> voucherList = walletRequest.voucherList();

        if (!userValidator.isValidNameFormat(username)) {
            throw new WrongRangeFormatException("incorrect token format");
        }

        if (!orderValidator.isValidOrder(order, voucherList)) {
            throw new WrongRangeFormatException("possible value out of range");
        }

        int convertedOrder = Integer.parseInt(order);
        Voucher voucher = voucherList.get(convertedOrder - 1);

        User user = userRepository.findByUsername(username);

        Wallet wallet = new Wallet(user.userId(), voucher.voucherId());

        switch (walletAssignmentSelectionType) {
            case ASSIGN_VOUCHER -> walletRepository.save(wallet);
            case REMOVE_VOUCHER -> walletRepository.free(wallet);
        }

        return new WalletResponse(wallet, username);
    }
}
