package org.prgrms.kdt.command;

import org.prgrms.kdt.blacklist.service.BlacklistService;
import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.service.VoucherService;

import java.util.HashMap;
import java.util.UUID;

public class ListCommand implements Command {

    @Override
    public boolean execute(Input input, Output output, VoucherService voucherService, BlacklistService blacklistService) {
        HashMap<UUID, Voucher> map = voucherService.getVoucherRepository().voucherHashMap;
        if(map.isEmpty()){
            output.emptyVoucherList();
        } else output.printVoucherList(map);

        return true;
    }
}
