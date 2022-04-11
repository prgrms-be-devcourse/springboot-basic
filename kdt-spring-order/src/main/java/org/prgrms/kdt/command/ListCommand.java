package org.prgrms.kdt.command;

import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class ListCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(CreateCommand.class);

    @Override
    public boolean execute(Input input, Output output, VoucherService voucherService) {
        HashMap<String, Voucher> map = voucherService.getVoucherRepository().voucherHashmap;
        if(map.isEmpty()){
            output.emptyVoucherList();
        } else output.printVoucherList(voucherService.getVoucherRepository().voucherHashmap);

        return true;
    }
}
