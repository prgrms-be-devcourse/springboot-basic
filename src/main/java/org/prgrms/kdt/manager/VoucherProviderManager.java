package org.prgrms.kdt.manager;

import org.prgrms.kdt.exceptions.AmountException;
import org.prgrms.kdt.exceptions.WrongVoucherTypeException;
import org.prgrms.kdt.io.IOManager;
import org.prgrms.kdt.utils.Power;
import org.prgrms.kdt.utils.VoucherType;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VoucherProviderManager {

    private static final String CREATE_MESSAGE = """
            Type 'f' to create FixedAmountVoucher.
            Type 'p' to create PercentAmountVoucher.
            Type 'g' to go back to main.""";

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
                ioManager.writeMessage(CREATE_MESSAGE);
                String createTypeInput = ioManager.getCreateTypeInput();
                execute(createTypeInput);
            } catch (WrongVoucherTypeException e) {
                ioManager.writeExceptionMessage(e.getMessage());
            }
        }
    }

    public void list() {
        List<Voucher> vouchers = voucherProvider.list();
        if (vouchers.isEmpty()) {
            ioManager.writeMessage("생성된 바우처가 없습니다.");
        } else {
            vouchers.forEach(ioManager::writeVoucherInfo);
        }
    }

    private void execute(String createTypeInput) {
        if (isGoBack(createTypeInput)) {
            ioManager.writeMessage("취소되었습니다.");
            return;
        }
        try {
            VoucherType voucherType = VoucherType.findVoucherType(createTypeInput);
            ioManager.writeMessage("할인하고자 하는 만큼의 수를 입력해주세요.");
            double amount = ioManager.getAmountInput();
            voucherProvider.create(voucherType, amount);
            ioManager.writeMessage("성공적으로 바우처가 형성되었습니다.");
            power.stop();
        } catch (AmountException e) {
            ioManager.writeExceptionMessage(e.getMessage());
        }
    }

    private boolean isGoBack(String createTypeInput) {
        if (createTypeInput.equals("g")) {
            power.stop();
            return true;
        }
        return false;
    }
}
