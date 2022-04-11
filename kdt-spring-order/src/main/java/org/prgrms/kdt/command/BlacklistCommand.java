package org.prgrms.kdt.command;

import org.prgrms.kdt.blacklist.domain.Blacklist;
import org.prgrms.kdt.blacklist.service.BlacklistService;
import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.voucher.service.VoucherService;

import java.util.HashMap;
import java.util.UUID;

public class BlacklistCommand implements Command{
    public boolean execute(Input input, Output output, VoucherService voucherService, BlacklistService blacklistService) {
        HashMap<String, Blacklist> map = blacklistService.getBlacklistRepository().blacklistHashMap;
        if(map.isEmpty()){
            output.emptyVoucherList();
        } else output.printBlackList(map);

        return true;
    }
}
