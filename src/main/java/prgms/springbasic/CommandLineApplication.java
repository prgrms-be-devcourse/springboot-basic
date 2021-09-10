package prgms.springbasic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import prgms.springbasic.io.Printer;
import prgms.springbasic.voucher.Voucher;
import prgms.springbasic.voucher.VoucherService;
import prgms.springbasic.voucher.VoucherType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

public class CommandLineApplication {
    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);

    public static void main(String[] args) throws IOException {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(CommandLineAppConfig.class);
        VoucherService voucherService = applicationContext.getBean(VoucherService.class);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Printer printer = new Printer();

        while (true) {
            printer.printCommandList();
            String command = reader.readLine();

            if (command.equals("create")) {
                printer.printVoucherTypeList();
                int newVoucherType = Integer.parseInt(reader.readLine());

                printer.printDiscountValueInputInfo();
                String discountValue = reader.readLine();
                Voucher newVoucher = voucherService.createVoucher(convertToVoucherType(newVoucherType), UUID.randomUUID(), discountValue);

                printer.printVoucherCreateSuccess(newVoucher);
                logger.info("바우처 저장소에 바우처가 추가되었습니다. VoucherId = {}", newVoucher.getVoucherId());

            } else if (command.equals("list")) {
                if (voucherService.getVoucherList().isEmpty()) {
                    printer.printVoucherListEmpty();
                    continue;
                }
                printer.printVoucherList(voucherService.getVoucherList());

            } else if (command.equals("exit")) {
                break;
            }
        }
    }

    public static VoucherType convertToVoucherType(int inputNumber) {
        for (VoucherType voucherType : VoucherType.values()) {
            if (voucherType.getTypeNumber() == inputNumber) {
                return voucherType;
            }
        }
        throw new IllegalArgumentException("해당 번호의 바우처 타입이 존재하지 않습니다.");
    }
}
