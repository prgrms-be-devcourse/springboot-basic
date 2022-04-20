package com.example.voucherproject.voucher.service;

import com.example.voucherproject.common.console.Input;
import com.example.voucherproject.common.console.Output;
import com.example.voucherproject.voucher.repository.VoucherRepository;
import com.example.voucherproject.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.example.voucherproject.common.enums.ServiceType.VOUCHER_SERVICE;
import static com.example.voucherproject.voucher.domain.VoucherFactory.create;

@Slf4j
@RequiredArgsConstructor
public class VoucherService implements Runnable{

    private final Input input;
    private final Output output;
    private final VoucherRepository voucherRepository;

    private final WalletRepository walletRepository;

    @Override
    public void run() {
        while(true){
            switch(input.selectVoucherMenu(VOUCHER_SERVICE)){
                case CREATE:{
                    var voucher = create(input.createVoucher(),input.amount());
                    output.createVoucher(voucherRepository.insert(voucher));
                    break;
                }
                case LIST:
                    output.printVouchers(voucherRepository.findAll());
                    break;
                case USERS:
                    checkVoucherUserList();
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

    private void checkVoucherUserList() {
        var vouchers = voucherRepository.findAll();
        output.printVouchers(vouchers);
        var voucher = vouchers.get(input.selectVoucher(vouchers.size()));
        var wallets = walletRepository.findByVoucherId(voucher.getId());
        output.printWalletUsers(wallets);
    }

}
