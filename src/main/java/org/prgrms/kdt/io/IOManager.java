package org.prgrms.kdt.io;

import org.prgrms.kdt.utils.MessageType;
import org.prgrms.kdt.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class IOManager {
    private final Console console;

    public IOManager(Console console) {
        this.console = console;
    }

    public String getInput() {
        return console.read().toLowerCase().strip();
    }

    public void doStartMessage(){
        console.write(MessageType.CONSOLE_START.getMessage());
    }

    public void doErrorMessage(String message){
        console.write(message);
    }

    public void doEndMessage(){
        console.write(MessageType.CONSOLE_END.getMessage());
    }

    public String getCreateTypeInput(){
        return console.read().strip();
    }

    public long getAmountInput(){
        return Long.parseLong(console.read().strip());
    }

    public void doMessage(String message){
        console.write(message);
    }

    public void doVoucherInfo(Voucher voucher){
        console.write(MessageFormat.format(
                "- voucherID : {0}, voucherType : {1}, Discount Amount : {2}",
                voucher.getVoucherId(), voucher.getClass().getSimpleName(), voucher.getAmount())
        );
    }
}
