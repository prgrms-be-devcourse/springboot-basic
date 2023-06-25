package prgms.spring_week1;

import org.springframework.stereotype.Component;
import prgms.spring_week1.domain.customer.repository.BlackListRepository;
import prgms.spring_week1.domain.voucher.model.Voucher;
import prgms.spring_week1.domain.voucher.repository.VoucherRepository;
import prgms.spring_week1.domain.voucher.service.VoucherService;
import prgms.spring_week1.io.Input;
import prgms.spring_week1.io.Output;
import prgms.spring_week1.menu.Menu;
import java.io.IOException;

@Component
public class CommandLine implements Runnable{
    private final Input input;
    private final Output output;
    private final VoucherRepository voucherRepository;
    private final VoucherService voucherService;
    private final BlackListRepository blackListRepository;
    private final boolean IS_RUNNING = true;

    public CommandLine(Input input, Output output, VoucherRepository voucherRepository, VoucherService voucherService, BlackListRepository blackListRepository) {
        this.input = input;
        this.output = output;
        this.voucherRepository = voucherRepository;
        this.voucherService = voucherService;
        this.blackListRepository = blackListRepository;
    }

    @Override
    public void run() {
        while (IS_RUNNING) {
            output.printMenuList();
            try {
                String selectOption = input.inputTextOption();
                switch (findMenuName(selectOption)) {
                    case EXIT -> System.exit(0);
                    case CREATE -> {
                        try {
                            selectVoucherType();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case LIST -> printAllVoucher();
                    case BLACK -> {
                        output.printBlackConsumerList(blackListRepository.getBlackConsumerList());
                    }
                    default -> output.printWrongMenuMessage();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private Menu findMenuName(String inputText){
        try {
           Menu selectMenu = Menu.findMenuType(inputText);
           return selectMenu;
        }catch (IllegalArgumentException e){
            return Menu.INVALID;
        }

    }

    private void selectVoucherType() throws IOException {
        output.printTypeSelectMessage();
        String select = input.inputVoucherType();

        Voucher voucher = voucherService.matchVoucherType(select);
        output.printInsertVoucherInfo(voucher);

    }

    private void printAllVoucher(){
        if(!voucherRepository.findAll().isEmpty()){
            output.printAllVoucher(voucherRepository.findAll());
        }
        else{
            output.printEmptyListMessage();
        }
    }
}
