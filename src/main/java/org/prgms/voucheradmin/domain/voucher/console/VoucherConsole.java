package org.prgms.voucheradmin.domain.voucher.console;

import static org.prgms.voucheradmin.domain.voucher.console.Command.*;
import static org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherTypes.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import org.prgms.voucheradmin.domain.voucher.dto.VoucherInputDto;
import org.prgms.voucheradmin.domain.voucher.entity.Voucher;
import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherTypes;
import org.prgms.voucheradmin.domain.voucher.exception.WrongInputException;
import org.prgms.voucheradmin.domain.voucher.service.VoucherService;
import org.springframework.stereotype.Component;

@Component
public class VoucherConsole {
    private final VoucherService voucherService;

    public VoucherConsole(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public void run() {
        boolean keepRun = true;

        while (keepRun) {
            try {
                showCommandList();
                Command command = selectCommand();

                switch (command) {
                    case CREATE:
                        showVoucherType();
                        VoucherTypes voucherType = selectVoucherType();
                        VoucherInputDto voucherInputDto = inputAmount(voucherType);
                        Voucher createdVoucher = voucherService.createVoucher(voucherInputDto);
                        System.out.println(createdVoucher+" created");
                        break;
                    case LIST:
                        List<Voucher> vouchers = voucherService.getVouchers();
                        for(Voucher voucher : vouchers) {
                            System.out.println(voucher);
                        }
                        break;
                    default:
                        keepRun = false;
                        break;
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void showCommandList() {
        StringBuilder commandListBuilder = new StringBuilder();

        commandListBuilder.append("\n=== Voucher Program ===\n").append("Type exit to exit the program.\n").append("Type create to create a new voucher.\n").append("Type list to list all vouchers.\n");

        System.out.println(commandListBuilder);
        System.out.print("command> ");
    }

    private Command selectCommand() throws IOException, WrongInputException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String selectedCommand = br.readLine().trim();

        Command command = findCommand(selectedCommand);

        return command;
    }

    private void showVoucherType() {
        StringBuilder voucherTypeStrBuilder = new StringBuilder();

        voucherTypeStrBuilder.append("\n");
        Arrays.stream(VoucherTypes.values()).forEach(voucherType -> voucherTypeStrBuilder.append(voucherType.toString()).append("\n"));
        voucherTypeStrBuilder.append("\nvoucher type> ");

        System.out.print(voucherTypeStrBuilder);
    }

    private VoucherTypes selectVoucherType() throws IOException, WrongInputException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String selectedVoucherTypeId = br.readLine().trim();
        System.out.println();

        return findVoucherType(selectedVoucherTypeId).orElseThrow(WrongInputException::new);
    }

    private VoucherInputDto inputAmount(VoucherTypes voucherType) throws IOException, WrongInputException {
        System.out.print(voucherType == FIXED_AMOUNT ? "amount> " : "percent> ");

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            return new VoucherInputDto(voucherType, Long.parseLong(br.readLine().trim()));
        }catch (NumberFormatException numberFormatException) {
            throw new WrongInputException();
        }
    }
}
