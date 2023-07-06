package com.prgms.springbootbasic.command;

import com.prgms.springbootbasic.ui.Console;
import com.prgms.springbootbasic.util.Menu;
import com.prgms.springbootbasic.util.VoucherMenu;
import com.prgms.springbootbasic.domain.Voucher;
import com.prgms.springbootbasic.persistent.VouchersStorage;
import com.prgms.springbootbasic.util.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VoucherCommand implements Command {

    private static final Logger logger = LoggerFactory.getLogger(VoucherCommand.class);
    private final Console console;
    private final VouchersStorage vouchersStorage;

    public VoucherCommand(Console console, VouchersStorage vouchersStorage) {
        this.console = console;
        this.vouchersStorage = vouchersStorage;
    }
    @Override
    public void execute() {
        String menuOfString = console.initApplication(Menu.VOUCHER);
        VoucherMenu voucherMenu = VoucherMenu.of(menuOfString);
        if (voucherMenu == VoucherMenu.LIST) {
            findVouchers();
            return;
        }
        if (voucherMenu == VoucherMenu.CREATE) {
            createVoucher();
        }
    }

    private void createVoucher() {
        Voucher voucher = inputVoucher();
        vouchersStorage.save(voucher);
        logger.info("바우처 저장에 성공했습니다. voucherType : {} voucherId : {} number : {}", voucher.getVoucherType(), voucher.getVoucherId(), voucher.getNumber());
    }

    private void findVouchers(){
        List<Voucher> vouchers = vouchersStorage.findAll();
        console.showVoucherList(vouchers);
        logger.info("저장된 바우처를 불러오는데 성공했습니다. size : {}", vouchers.size());
    }

    private Voucher inputVoucher() {
        String voucherTypeOfString = console.inputVoucherType();
        VoucherType voucherType = VoucherType.of(voucherTypeOfString);
        Long number = console.inputVoucherNumber();
        return voucherType.createVoucher(number);
    }

}
