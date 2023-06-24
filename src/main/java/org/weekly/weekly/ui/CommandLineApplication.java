package org.weekly.weekly.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.weekly.weekly.ui.exception.ReadException;
import org.weekly.weekly.ui.reader.CommandReader;
import org.weekly.weekly.ui.writer.CommandWriter;
import org.weekly.weekly.util.DiscountMap;
import org.weekly.weekly.util.ExceptionMsg;
import org.weekly.weekly.util.VoucherMenu;
import org.weekly.weekly.voucher.domain.Discount;
import org.weekly.weekly.voucher.dto.VoucherDto;
import org.weekly.weekly.voucher.model.ListResponse;
import org.weekly.weekly.voucher.model.Response;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public Discount readDiscount() {
        while(true) {
            try {
                this.commandWriter.printSelectDiscount();
                return DiscountMap.getDiscountMap(this.commandReader.readLine()).getCls().getDeclaredConstructor().newInstance();
            } catch (Exception exception) {
                printErrorMsg(exception.getMessage());
            }
        }
    }

    public VoucherDto readVoucher(Discount discount) {
        while(true) {
            try {
                this.commandWriter.printCreateVoucher();
                String[] inputs = this.commandReader.readLine().split(",");
                checkReadVoucherException(inputs);
                return VoucherDto.parseDto(UUID.randomUUID(), inputs[0].trim(), LocalDate.now(), inputs[1].trim(), discount);
            } catch (Exception exception) {
                printErrorMsg(exception.getMessage());
            }
        }
    }

    public void printErrorMsg(String errorMsg) {
        this.commandWriter.printErrorMsg(errorMsg);
    }

    public void printResult(Response response) {
        this.commandWriter.printReuslt(response.getResult());
    }

    private void checkReadVoucherException(String[] inputs) {
        ReadException.notVoucherInputSize(inputs);
        ReadException.notVoucherInputFormat(inputs);
    }
}
