package org.prgrms.kdt.io;

import org.prgrms.kdt.blacklist.BlackUser;
import org.prgrms.kdt.voucher.model.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandLineInterface implements UserInteraction {
    Logger logger = LoggerFactory.getLogger(CommandLineInterface.class);

    @Override
    public void showInfoMessage() {
        logger.info("===  Voucher Program ===");
        logger.info("Type exit to exit the program.");
        logger.info("Type create to voucher create.");
        logger.info("Type list to list all vouchers (from memory).");
        logger.info("Type blackList to list all blackList Users (from file).");
    }

    @Override
    public void showVoucherTypeSelectMessage() {
        logger.info("discountType 을 입력해주세요. (FixedAmountVoucher or PercentDiscountVoucher)");
    }

    @Override
    public void showVoucherDiscountMessage() {
        logger.info("discount Amount 를 입력해주세요");
    }

    @Override
    public void showExitProgramMessage() {
        logger.info("프로그램을 종료합니다. (exit program bye ~ )");
    }

    @Override
    public void showInvalidMessage() {
        logger.warn("( list | create | exit| blacklist ) 만 입력해주세요. 다시 프로그램을 시작합니다.");
    }

    @Override
    public void showInvalidTypeMessage() {
        logger.warn("타입은 (PercentDiscountVoucher| FixedAmountVoucher) 로 입력해주세요. 처음으로 돌아갑니다. ");
    }

    @Override
    public void showVoucherList(List<Voucher> voucherList) {
        if (voucherList.isEmpty()) {
            logger.warn("현재 저장된 바우처리스트가 없네요! create 로 등록 혹은 exit 로 종료해주세요~");
        }
        for (Voucher voucher : voucherList) {
            logger.info(voucher.toString());
        }
    }

    @Override
    public void showBlackList(List<BlackUser> blackUserList) {
        if(blackUserList.isEmpty()){
            logger.warn("현재 파일에 저장된 블랙리스트 정보가 없어요! create로 등록하거나 exit로 종료해주세요~");
        }
        for (BlackUser blackUser : blackUserList) {
            logger.info(String.valueOf(blackUser));
        }
    }
}
