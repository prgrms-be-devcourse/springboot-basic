package prgms.spring_week1;

import org.springframework.stereotype.Component;
import prgms.spring_week1.domain.voucher.model.Voucher;
import prgms.spring_week1.domain.voucher.model.impl.FixedAmountVoucher;
import prgms.spring_week1.domain.voucher.model.impl.PercentDiscountVoucher;
import prgms.spring_week1.domain.voucher.repository.VoucherRepository;
import prgms.spring_week1.domain.voucher.repository.impl.MemoryVoucherRepository;
import prgms.spring_week1.io.Input;
import prgms.spring_week1.io.Output;
import prgms.spring_week1.Menu.Menu;

import java.io.IOException;

@Component
public class CommandLine implements Runnable{
    private final Input input;
    private final Output output;
    private final VoucherRepository voucherRepository;

    public CommandLine(Input input, Output output,VoucherRepository voucherRepository) {
        this.input = input;
        this.output = output;
        this.voucherRepository = voucherRepository;
    }

    @Override
    public void run() {
        while (true) {
            String selectOption = null;
            output.printMenuList();
            try {
                selectOption = input.inputTextOption();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            switch (findMenuName(selectOption)) {
                case EXIT -> System.out.println("exit");
                case CREATE -> {
                    try {
                        selectVoucherType();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                case LIST -> printAllVoucher();
                default -> System.out.println("wrong");
            }
        }
    }

    private Menu findMenuName(String inputText){
        return Menu.findMenuType(inputText);
    }

    private void selectVoucherType() throws IOException {
        output.printTypeSelectMessage();
        String select = input.inputVoucherType();

        switch(select){
            case "Percent" -> voucherRepository.insert(new PercentDiscountVoucher());
            case "Fixed" -> voucherRepository.insert(new FixedAmountVoucher());
            default -> System.out.println("wrong");
        }
    }

    private void printAllVoucher(){
        if(voucherRepository.findAll().isEmpty()){
            output.printAllVoucher(voucherRepository.findAll().get());
        }
        else{
            output.printEmptyListMessage();
        }

    }

}
