package org.prgrms.kdt.manager;

import org.prgrms.kdt.exceptions.AmountException;
import org.prgrms.kdt.exceptions.InvalidDBAccessException;
import org.prgrms.kdt.exceptions.InvalidITypeInputException;
import org.prgrms.kdt.io.IOManager;
import org.prgrms.kdt.utils.Power;
import org.prgrms.kdt.utils.VoucherType;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VoucherProviderManager {

    private static final String GO_BACK = "g";
    private static final String NO_VOUCHERS = "생성된 바우처가 없습니다.";
    private static final String CREATE_VOUCHER = "성공적으로 바우처가 형성되었습니다.";

    private static final Logger logger = LoggerFactory.getLogger(VoucherProviderManager.class);

    private final IOManager ioManager;
    private final VoucherProvider voucherProvider;
    private Power power;

    public VoucherProviderManager(IOManager ioManager, VoucherProvider voucherProvider) {
        this.ioManager = ioManager;
        this.voucherProvider = voucherProvider;
    }

    public void runGetList(Power AppPower) {
        try {
            List<Voucher> vouchers = voucherProvider.list();
            if (vouchers.isEmpty()) {
                ioManager.writeMessage(NO_VOUCHERS);
                return;
            }
            vouchers.forEach(ioManager::writeVoucherInfo);
            logger.info("생성되었던 바우처 목록이 성공적으로 실행됩니다.");

        } catch (InvalidDBAccessException dbAccessException) {
            logger.error(dbAccessException.getMessage());
            AppPower.stop();
            AppPower.stopByException();
        }
    }

    public void runCreateCycle() {
        power = new Power();
        while (power.getIsRunning()) {
            String createTypeInput = ioManager.getCreateTypeInput();
            if (isGoBack(createTypeInput)) {
                logger.info("사용자가 바우처 생성을 취소하였습니다.");
                continue;
            }
            try {
                VoucherType voucherType = VoucherType.findVoucherTypeByInput(createTypeInput);
                int amount = ioManager.getAmountInput();
                voucherProvider.create(voucherType, amount);
                ioManager.writeMessage(CREATE_VOUCHER);
                logger.info("바우처가 성공적으로 생성되었습니다.");
                power.stop();
            } catch (InvalidITypeInputException | AmountException e) {
                logger.error("사용자가 유효하지 않은 값을 입력하였습니다.");
                ioManager.writeExceptionMessage(e.getMessage());
            }
        }
    }

    private boolean isGoBack(String createTypeInput) {
        if (createTypeInput.equals(GO_BACK)) {
            power.stop();
            return true;
        }
        return false;
    }
}
