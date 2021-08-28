package org.prgrms.kdt.command;

import java.util.EnumMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.prgrms.kdt.voucher.VoucherService;
import org.springframework.stereotype.Component;

/**
 * Created by yhh1056
 * Date: 2021/08/28 Time: 2:02 오후
 */

@Component
public class CommandCollection {

    private final Map<CommandType, CommandOperator> categories = new EnumMap<>(CommandType.class);

    private final VoucherService voucherService;

    public CommandCollection(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostConstruct
    void initCommandType() {
        categories.put(CommandType.CREATE, new VoucherCreateCommand(voucherService));
        categories.put(CommandType.LIST, new VoucherListCommand(voucherService));
        categories.put(CommandType.EXIT, new ExitCommand());
    }

    public CommandOperator findByCommandType(CommandType commandType) {
        return categories.get(commandType);
    }
}
