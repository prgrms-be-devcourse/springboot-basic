package com.example.voucherproject.voucher.service;

import com.example.voucherproject.common.io.Input;
import com.example.voucherproject.common.io.Output;
import com.example.voucherproject.voucher.domain.VoucherFactory;
import com.example.voucherproject.voucher.repository.VoucherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.example.voucherproject.common.enums.ServiceType.VOUCHER_SERVICE;

@Slf4j
@RequiredArgsConstructor
public class VoucherService implements Runnable{

    private final Input input;
    private final Output output;
    private final VoucherFactory voucherFactory;        // voucher 생성
    private final VoucherRepository voucherRepository;  // voucher 저장

    @Override
    public void run() {
        while(true){
            switch(input.selectMenu(VOUCHER_SERVICE)){
                case CREATE:
                    var voucher = voucherFactory.create(input.createVoucher());
                    output.createVoucher(voucherRepository.save(voucher));
                    break;
                case LIST:
                    output.vouchers(voucherRepository.getList());
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
}
