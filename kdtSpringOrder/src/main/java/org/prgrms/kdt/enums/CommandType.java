package org.prgrms.kdt.enums;

import org.prgrms.kdt.repository.voucher.FileVoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum CommandType {

    UNDEFINED("undefined"),
    EXIT("exit"),
    CREATE("create"),
    ALLVOUCHERLIST("allvoucherlist"),
    VOUCHERLISTBYCUSTOMERID("voucherlistbycustomerid"),
    VOUCHERBYVOUCHERID("voucherbyvoucherid"),
    DELETEVOUCHER("deletevoucher"),
    BLACKLIST("blacklist"),
    REPLAY("replay");

    private final static Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);

    CommandType(String command) {
    }

    static public CommandType getCommandType(String commandType){
        try {
            return CommandType.valueOf(commandType);
        } catch (IllegalArgumentException ex){
            logger.error("정의되지 않은 commandType이 입력되었습니다. commandType : {}", commandType);
            return UNDEFINED;
        }
    }
}