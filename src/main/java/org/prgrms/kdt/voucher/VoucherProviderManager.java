package org.prgrms.kdt.voucher;

import org.prgrms.kdt.exceptions.AmountException;
import org.prgrms.kdt.exceptions.NoVoucherException;
import org.prgrms.kdt.exceptions.WrongVoucherTypeException;
import org.prgrms.kdt.io.IOManager;
import org.prgrms.kdt.utils.MessageType;
import org.prgrms.kdt.utils.Power;
import org.prgrms.kdt.utils.VoucherType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VoucherProviderManager {

    private final String createMessage = """
            Type '1' to create FixedAmountVoucher.
            Type '2' to create PercentAmountVoucher.
            Type '3' to go back to main.""";
    private final String amountMessage = "할인하고자 하는 만큼의 수를 입력해주세요.";
    private final String successCreateMessage = "성공적으로 바우처가 형성되었습니다.";

    private final IOManager ioManager;
    private final VoucherProvider voucherProvider;
    private Power power;

    public VoucherProviderManager(IOManager ioManager, VoucherProvider voucherProvider) {
        this.ioManager = ioManager;
        this.voucherProvider = voucherProvider;
    }

    public void create() {
        power = new Power();
        while (power.getIsRunning()) {
            try {
                execute();
            } catch (WrongVoucherTypeException | AmountException e) {
                ioManager.doErrorMessage(e.getMessage());
            }
        }
    }

    public void list() {
        try{
            voucherProvider.list()
                    .forEach(ioManager::doVoucherInfo);
        }catch (NoVoucherException e){
            ioManager.doErrorMessage(e.getMessage());
        }
    }

    private void execute() {
        ioManager.doMessage(createMessage);
        VoucherType voucherType = VoucherType.findVoucherType(ioManager.getCreateTypeInput());
        if (voucherType == VoucherType.GO_BACK) {
            ioManager.doMessage(MessageType.CANCEL.getMessage());
        } else {
            ioManager.doMessage(amountMessage);
            long amount = ioManager.getAmountInput();
            voucherProvider.create(voucherType, amount);
            ioManager.doMessage(successCreateMessage);
        }
        power.stop();
    }
}
