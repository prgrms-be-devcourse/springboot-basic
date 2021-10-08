package org.prgrms.kdt.command.service.customer;

import org.prgrms.kdt.command.ValueValidation;
import org.prgrms.kdt.command.io.Input;
import org.prgrms.kdt.command.io.Output;
import org.prgrms.kdt.command.service.CommandService;
import org.prgrms.kdt.command.service.voucher.VoucherDeleteService;
import org.prgrms.kdt.customer.service.CustomerService;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerVoucherListService implements CommandService {

    private final CustomerService customerService;
    private final VoucherService voucherService;
    private final VoucherDeleteService deleteService;

    public CustomerVoucherListService(final CustomerService customerService,
                                      final VoucherService voucherService,
                                      final VoucherDeleteService deleteService) {
        this.customerService = customerService;
        this.voucherService = voucherService;
        this.deleteService = deleteService;
    }

    @Override
    public void commandRun() {
        System.out.println("email을 입력해주세요.");
        String email = null;

        boolean emailCheck = true;
        while (emailCheck) {
            email = Input.input();
            if (ValueValidation.emailValidation(email))
                emailCheck = false;
        }

        final Optional<List<Voucher>> voucherList = voucherService.getAllVoucherOfCustomer(email);
        if (voucherList.isPresent()) {
            final List<Voucher> list = voucherList.get();
            for (final Voucher voucher : list) {
                System.out.println(voucher);
            }
        } else {
            System.out.println(MessageFormat.format("email: {0} 에 해당하는 voucher가 존재하지 않습니다.", email));
        }

        boolean deleteCheck = true;
        do {
            Output.voucherDeleteMessage();

            final String commandInput = Input.input();
            switch (commandInput) {
                case "back" -> deleteCheck = false;
                case "delete" -> deleteService.commandRun();
                default -> Output.inputTypeErrorMessage(commandInput);
            }
        } while (deleteCheck);

    }
}
