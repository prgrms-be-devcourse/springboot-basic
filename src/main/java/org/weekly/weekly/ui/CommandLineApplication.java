package org.weekly.weekly.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.weekly.weekly.ui.reader.CommandReader;
import org.weekly.weekly.ui.writer.CommandWriter;
import org.weekly.weekly.util.DiscountMap;
import org.weekly.weekly.util.ExceptionMsg;
import org.weekly.weekly.util.VoucherMenu;
import org.weekly.weekly.voucher.dto.VoucherDto;

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
                this.commandWriter.printErrorMsg(ExceptionMsg.NOT_MENU.getMsg());
            }
        }
    }

    public VoucherDto readVoucher() {
        while(true) {
            try {
                this.commandWriter.printCreateVoucher();
                String[] inputs = this.commandReader.readLine().split(",");
                return VoucherDto.parseDto(UUID.randomUUID(), inputs[0], LocalDate.now(), inputs[1]);
            } catch (Exception exception) {
                this.commandWriter.printErrorMsg(ExceptionMsg.NOT_INPUT_FORMAT.getMsg());
            }
        }
    }

    public DiscountMap readDiscount() {
        while(true) {
            try {
                this.commandWriter.printCreateVoucher();
                return DiscountMap.getDiscountMap(this.commandReader.readLine());
            } catch (Exception exception) {
                this.commandWriter.printErrorMsg(ExceptionMsg.NOT_INPUT_FORMAT.getMsg());
            }
        }
    }

}
