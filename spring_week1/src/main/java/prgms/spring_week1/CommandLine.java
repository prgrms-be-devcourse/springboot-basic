package prgms.spring_week1;

import org.springframework.stereotype.Component;
import prgms.spring_week1.domain.customer.repository.BlackListRepository;
import prgms.spring_week1.domain.customer.service.CustomerService;
import prgms.spring_week1.domain.voucher.model.Voucher;
import prgms.spring_week1.domain.voucher.model.type.VoucherType;
import prgms.spring_week1.domain.voucher.repository.VoucherRepository;
import prgms.spring_week1.domain.voucher.service.VoucherService;
import prgms.spring_week1.exception.EmptyListException;
import prgms.spring_week1.exception.NoSuchOptionValue;
import prgms.spring_week1.exception.NoSuchVoucherType;
import prgms.spring_week1.io.Input;
import prgms.spring_week1.io.Output;
import prgms.spring_week1.menu.Menu;

@Component
public class CommandLine implements Runnable{
    private final Input input;
    private final Output output;
    private final VoucherRepository voucherRepository;
    private final VoucherService voucherService;
    private final CustomerService customerService;
    private boolean IS_RUNNING = true;

    public CommandLine(Input input, Output output, VoucherRepository voucherRepository, VoucherService voucherService, CustomerService customerService) {
        this.input = input;
        this.output = output;
        this.voucherRepository = voucherRepository;
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    @Override
    public void run() {
        while (IS_RUNNING) {
            output.printMenuList();
            String selectOption = input.inputTextOption();
            switch (findMenuName(selectOption)) {
                case EXIT -> System.exit(0);
                case CREATE -> selectVoucherType();
                case LIST -> printAllVoucher();
                case BLACK -> output.printBlackConsumerList(customerService.blackConsumerList());
                default -> output.printWrongMenuMessage();
                }
            }
        }


    private Menu findMenuName(String inputText){
        try {
           Menu selectMenu = Menu.findMenuType(inputText);
           return selectMenu;
        }catch (NoSuchOptionValue e){
            return Menu.INVALID;
        }
    }

    private void selectVoucherType(){
        output.printTypeSelectMessage();
        try {
            String select = input.inputVoucherType();
            VoucherType voucherType = voucherService.matchVoucherType(select);
            System.out.println(voucherType);
            switch (voucherType){
                case FIXED -> {
                    long discountAmount = input.insertDiscountAmountVoucher();
                    Voucher newVoucher = voucherService.insertFixedAmountVoucher(discountAmount);
                    output.printInsertVoucherInfo(newVoucher);
                }
                case PERCENT -> {
                    int discountPercent = input.insertDiscountPercentVoucher();
                    Voucher newVoucher = voucherService.insertPercentDiscountVoucher(discountPercent);
                    output.printInsertVoucherInfo(newVoucher);
                }
            }

        }catch (NoSuchVoucherType e){
            output.printNoSuchVoucherType();
        }
    }

    private void printAllVoucher(){
        try{
            output.printAllVoucher(voucherRepository.findAll());
        }catch (EmptyListException e){
            output.printEmptyListMessage();
        }

    }
}
