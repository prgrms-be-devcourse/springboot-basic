package org.prgrms.kdt.command;

import org.prgrms.kdt.voucher.utils.VoucherMapper;
import org.prgrms.kdt.voucher.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandExecutor {

    private final VoucherManager voucherManager;
    private final VoucherMapper voucherMapper;
    private static Logger logger = LoggerFactory.getLogger(CommandExecutor.class);

    public CommandExecutor(VoucherManager voucherManager, VoucherMapper voucherMapper) {
        this.voucherManager = voucherManager;
        this.voucherMapper = voucherMapper;
    }

    public void create(String type, String amount) {
        logger.info("바우처 생성: [type] -> {}, [amount] -> {}", type, amount);

        Voucher voucher = voucherMapper.fromMetadata(type, amount);

        voucherManager.save(voucher);
    }

    public List<Voucher> list() {
        logger.info("바우처 리스트 반환");
        return voucherManager.findAll();
    }
}
