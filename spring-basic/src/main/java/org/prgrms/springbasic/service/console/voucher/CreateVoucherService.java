package org.prgrms.springbasic.service.console.voucher;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.springbasic.domain.console.Console;
import org.prgrms.springbasic.domain.voucher.FixedAmountVoucher;
import org.prgrms.springbasic.domain.voucher.PercentDiscountVoucher;
import org.prgrms.springbasic.domain.voucher.Voucher;
import org.prgrms.springbasic.domain.voucher.VoucherType;
import org.prgrms.springbasic.repository.voucher.VoucherRepository;
import org.prgrms.springbasic.service.console.ConsoleService;
import org.prgrms.springbasic.utils.exception.NonExistentCommand;
import org.prgrms.springbasic.utils.exception.NotValidatedType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.UUID.randomUUID;
import static org.prgrms.springbasic.utils.enumm.ErrorMessage.COMMAND_ERROR;
import static org.prgrms.springbasic.utils.enumm.ErrorMessage.PARSING_ERROR;

@Service("create")
@Slf4j
public class CreateVoucherService implements ConsoleService {

    private final VoucherRepository repository;
    private final Console console;
    private Voucher voucher;

    @Autowired
    public CreateVoucherService(VoucherRepository repository, Console console) {
        this.repository = repository;
        this.console = console;
    }

    @Override
    public void execute() {
        console.printVoucherType();
        var voucherType = validateVoucherType(console.takeInput());
        console.printDiscountInput(voucherType);
        var discount = validateDiscountInfo(console.takeInput());
        createVoucher(voucherType, discount);
    }

    private void createVoucher(VoucherType voucherType, long discount) {
        switch(voucherType) {
            case FIXED:
                voucher = new FixedAmountVoucher(randomUUID(), discount);
                break;
            case PERCENT:
                voucher = new PercentDiscountVoucher(randomUUID(), discount);
                break;
            default:
                break;
        }

        repository.save(voucher);
    }

    private VoucherType validateVoucherType(String command) {
        VoucherType voucherType;

        try {
            voucherType = VoucherType.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.error("Got non existent command: {}", command);
            throw new NonExistentCommand(COMMAND_ERROR.getMessage());
        }

        return voucherType;
    }

    private long validateDiscountInfo(String discountInfo) {
        long discount;

        try {
            discount = Long.parseLong(discountInfo);
        } catch (NumberFormatException e) {
            log.error("Got parsing error: {}", discountInfo);
            throw new NotValidatedType(PARSING_ERROR.getMessage());
        }

        return discount;
    }
}
