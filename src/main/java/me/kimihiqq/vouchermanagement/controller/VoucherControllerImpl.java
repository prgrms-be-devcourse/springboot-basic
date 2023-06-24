package me.kimihiqq.vouchermanagement.controller;


import lombok.RequiredArgsConstructor;
import me.kimihiqq.vouchermanagement.dto.VoucherDto;
import me.kimihiqq.vouchermanagement.service.VoucherService;
import org.springframework.stereotype.Controller;
import me.kimihiqq.vouchermanagement.view.Console;

@Controller
@RequiredArgsConstructor
public class VoucherControllerImpl implements VoucherController {
    private final Console console;
    private final VoucherService voucherService;

    @Override
    public void run() {
        console.printLine("=== Voucher Program ===");
        console.printLine("Type **exit** to exit the program.");
        console.printLine("Type **create** to create a new voucher.");
        console.printLine("Type **list** to list all vouchers.");

        String input;
        while (!(input = console.readLine()).equalsIgnoreCase("exit")) {
            if (input.equalsIgnoreCase("create")) {
                String type = console.readLine();
                long discount = Long.parseLong(console.readLine());
                VoucherDto voucherDto = new VoucherDto(type, discount);
                voucherService.createVoucher(voucherDto);
                console.printLine("Voucher created...");
            } else if (input.equalsIgnoreCase("list")) {
                voucherService.listVouchers().forEach(voucher ->
                        console.printLine(voucher.getVoucherId() + ": " + voucher.getType() + " - " + voucher.discount(100))
                );
            } else {
                console.printLine("잘못된 명령입니다. 다시 시도하세요.");
            }
        }
    }
}