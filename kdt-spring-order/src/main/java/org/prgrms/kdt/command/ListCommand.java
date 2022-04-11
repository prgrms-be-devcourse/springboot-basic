package org.prgrms.kdt.command;

import org.prgrms.kdt.blacklist.service.BlacklistService;
import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.service.MemoryVoucherService;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.context.annotation.Profile;

import java.util.HashMap;
import java.util.UUID;

public class ListCommand implements Command {

    @Override
    @Profile("local")
    public boolean execute(Input input, Output output, MemoryVoucherService memoryVoucherService, BlacklistService blacklistService) {
        HashMap<UUID, Voucher> map = memoryVoucherService.getVoucherRepository().voucherHashMap;
        if(map.isEmpty()){
            output.emptyVoucherList();
        } else output.printVoucherList(map);

        return true;
    }
}
