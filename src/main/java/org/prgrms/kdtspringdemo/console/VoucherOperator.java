package org.prgrms.kdtspringdemo.console;

import org.prgrms.kdtspringdemo.VoucherType;
import org.prgrms.kdtspringdemo.voucher.Voucher;
import org.prgrms.kdtspringdemo.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Stream;

@Component
public class VoucherOperator implements CommandOperator<Voucher> {
    private final static Logger logger = LoggerFactory.getLogger(VoucherOperator.class);
    private final VoucherService voucherService;

    public VoucherOperator(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    // todo : voucher 생성 코드를 service 내부에서 처리하기
    @Override
    public boolean create(String[] splitList) {
        if (!validationCheck(splitList)) return false;
        Voucher voucher = VoucherType.createVoucher(splitList[0], Long.parseLong(splitList[1]));
        if (voucher != null) {
            voucherService.saveVoucher(voucher);
            System.out.println("This is create : " + voucher);
        }
        else {
            logger.error(MessageFormat.format("Invalid create command. Your input -> {0}, {1}", splitList[0], splitList[1]));
            System.out.println("[ERROR]Invalid create command");
            System.out.println(MessageFormat.format("Your input : {0}, {1}", splitList[0], splitList[1]));
        }
        return voucher != null;
    }

    @Override
    public List<Voucher> getAllitems() {
        return voucherService.getAllVouchers();
    }

    public boolean validationCheck(String[] splitList) {
        if (splitList.length != 2) return false;
        if (!VoucherType.isInVoucherType(splitList[0])) return false;
        if (!splitList[1].matches("^[0-9]*$")) return false;

        return true;
    }
}
