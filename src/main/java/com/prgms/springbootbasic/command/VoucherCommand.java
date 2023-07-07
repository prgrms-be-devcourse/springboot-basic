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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@Transactional(readOnly = true)
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
            return;
        }
        if (voucherMenu == VoucherMenu.UPDATE) {
            update();
            return;
        }
        if (voucherMenu == VoucherMenu.DELETE) {
            delete();
        }
    }

    @Transactional
    public void createVoucher() {
        Voucher voucher = inputVoucher();
        vouchersStorage.save(voucher);
        logger.info("바우처 저장에 성공했습니다. voucherType : {} voucherId : {} number : {}", voucher.getVoucherType(), voucher.getVoucherId(), voucher.getNumber());
    }

    public void findVouchers(){
        List<Voucher> vouchers = vouchersStorage.findAll();
        console.showVoucherList(vouchers);
        logger.info("저장된 바우처를 불러오는데 성공했습니다. size : {}", vouchers.size());
    }

    @Transactional
    public void update() {
        UUID voucherId = UUID.fromString(console.inputUUID());
        Voucher voucher = vouchersStorage.findById(voucherId);
        Long amount = console.inputVoucherNumber();
        voucher.changeAmount(amount);
        vouchersStorage.update(voucher);
    }

    @Transactional
    public void delete() {
        UUID voucherId = UUID.fromString(console.inputUUID());
        vouchersStorage.deleteOne(voucherId);
    }

    private Voucher inputVoucher() {
        String voucherTypeOfString = console.inputVoucherType();
        VoucherType voucherType = VoucherType.of(voucherTypeOfString);
        Long number = console.inputVoucherNumber();
        return voucherType.createVoucher(number);
    }

}
