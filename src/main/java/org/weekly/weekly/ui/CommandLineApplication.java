package org.weekly.weekly.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.weekly.weekly.ui.reader.CommandReader;
import org.weekly.weekly.ui.writer.CommandWriter;
import org.weekly.weekly.util.DiscountType;
import org.weekly.weekly.util.VoucherMenu;
import org.weekly.weekly.voucher.domain.Discount;
import org.weekly.weekly.voucher.dto.VoucherDto;
import org.weekly.weekly.voucher.model.Response;
import org.weekly.weekly.voucher.model.VoucherInfoRequest;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class CommandLineApplication {
    private final CommandReader commandReader;
    private final CommandWriter commandWriter;

    @Autowired
    public CommandLineApplication(CommandReader commandReader, CommandWriter commandWriter) {
        this.commandReader = commandReader;
        this.commandWriter = commandWriter;
    }

    public VoucherMenu readMenu() {
        while(true) {
            try {
                this.commandWriter.printVoucherProgram();
                return VoucherMenu.getMenu(this.commandReader.readLine());
            } catch (Exception exception) {
                printErrorMsg(exception.getMessage());
            }
        }
    }

    public VoucherDto readVoucher() {
        while(true) {
            try {
                Discount discount = readDiscount();
                VoucherInfoRequest voucherInfoRequest = readVoucherInfo();
                return VoucherDto.parseDto(UUID.randomUUID(), voucherInfoRequest, discount, LocalDate.now());
            } catch (Exception exception) {
                printErrorMsg(exception.getMessage());
            }
        }
    }

    private Discount readDiscount() throws Exception {
        this.commandWriter.printSelectDiscount();
        return DiscountType.getDiscountMap(this.commandReader.readLine()).getNewInstance();
    }

    private VoucherInfoRequest readVoucherInfo() throws Exception {
        this.commandWriter.printCreateVoucher();
        return new VoucherInfoRequest(this.commandReader.readLine());
    }

    public void printErrorMsg(String errorMsg) {
        this.commandWriter.printErrorMsg(errorMsg);
    }

    public void printResult(Response response) {
        this.commandWriter.printReuslt(response.getResult());
    }
}
