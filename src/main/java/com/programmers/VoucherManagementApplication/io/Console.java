package com.programmers.VoucherManagementApplication.io;

import com.programmers.VoucherManagementApplication.controller.VoucherController;
import com.programmers.VoucherManagementApplication.dto.CreateVoucherRequest;
import com.programmers.VoucherManagementApplication.voucher.Voucher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Console implements CommandLineRunner {

    private final Input input;
    private final Output output;
    private final VoucherController voucherController;

    public Console(Input input, Output output, VoucherController voucherController) {
        this.input = input;
        this.output = output;
        this.voucherController = voucherController;
    }

    @Override
    public void run(String... args) throws Exception {
        MenuType menuType = MenuType.DEFAULT;
        while (menuType != MenuType.EXIT) {
            try {
                output.write(Message.MENU_PROMPT.getMessage());
                menuType = MenuType.getMenuType(input.readLine());
                switch (menuType) {
                    case EXIT:
                        output.write(Message.EXIT_MESSAGE.getMessage());
                        break;
                    case CREATE:
                        createVoucher();
                        break;
                    case LIST:
                        writeVoucherMap();
                        break;
                    default:
                        output.write(Message.INVALID_INPUT.getMessage());
                }
            } catch (IllegalArgumentException e) {
                output.write(e.getMessage());
            }
        }
    }

    private void writeVoucherMap() {
        List<Voucher> all = voucherController.findAll();
        if (all.size() == 0) output.write(Message.NO_LIST.getMessage());
        else output.writeAll(all);
    }

    private Voucher createVoucher() {
        output.write(Message.CREATE_MENU.getMessage());
        CreateVoucherRequest request = new CreateVoucherRequest(input.readLine());
//        Voucher voucher = request.getVoucherType().voucherCreator(request);
//        System.out.println(voucher);
        return voucherController.create(request.getVoucherType(), request.getAmount());
    }
}
