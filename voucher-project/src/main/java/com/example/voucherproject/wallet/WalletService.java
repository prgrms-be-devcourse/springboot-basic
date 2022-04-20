package com.example.voucherproject.wallet;

import com.example.voucherproject.common.console.Input;
import com.example.voucherproject.common.console.Output;
import com.example.voucherproject.user.repository.UserRepository;
import com.example.voucherproject.voucher.repository.VoucherRepository;
import com.example.voucherproject.wallet.domain.WalletFactory;
import com.example.voucherproject.wallet.enums.WalletMenu;
import com.example.voucherproject.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.example.voucherproject.common.enums.ServiceType.WALLET_SERVICE;
import static com.example.voucherproject.wallet.enums.WalletMenu.*;

@Slf4j
@RequiredArgsConstructor
public class WalletService implements Runnable {
    private final Input input;
    private final Output output;
    private final UserRepository userRepository;
    private final VoucherRepository voucherRepository;
    private final WalletRepository walletRepository;

    @Override
    public void run() {
        while (true) {
            switch (input.selectWalletMenu(WALLET_SERVICE)) {
                case ASSIGN:
                    createWallet();
                    break;
                case DELETE:
                    deleteWallet();
                    break;
                case LIST:
                    printWallets();
                    break;
                case HOME:
                    output.home();
                    return;
                default:
                    output.error();
                    break;
            }
        }
    }

    private void printWallets() {
        output.printWallets(walletRepository.findAll());
    }

    private void deleteWallet() {
        var users = userRepository.findAll();
        output.printUsers(users);
        var user = users.get(input.selectUser(users.size()));
        output.selectedUser(user);

        var vouchers = voucherRepository.findAll();
        output.printVouchers(vouchers);
        var voucher = vouchers.get(input.selectVoucher(vouchers.size()));
        output.selectedVoucher(voucher);

        var wallet = walletRepository.findByIds(user.getId(), voucher.getId());
        wallet.ifPresent(value -> {
            output.deleteSuccess(value);
            walletRepository.deleteById(value.getId());
        });
    }

    private void createWallet() {
        var users = userRepository.findAll();
        output.printUsers(users);
        var user = users.get(input.selectUser(users.size()));

        var vouchers = voucherRepository.findAll();
        output.printVouchers(vouchers);
        var voucher = vouchers.get(input.selectVoucher(vouchers.size()));

        var wallet = WalletFactory.create(user.getId(), voucher.getId());

        walletRepository.insert(wallet);
    }

}