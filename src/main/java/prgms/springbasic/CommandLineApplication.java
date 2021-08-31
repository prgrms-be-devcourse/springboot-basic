package prgms.springbasic;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import prgms.springbasic.consol.Printer;
import prgms.springbasic.repository.VoucherRepository;
import prgms.springbasic.voucher.Voucher;
import prgms.springbasic.voucher.VoucherServiceImpl;
import prgms.springbasic.voucher.VoucherType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

@SpringBootApplication
public class CommandLineApplication {
    public static void main(String[] args) throws IOException {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(CommandLineAppConfig.class);
        VoucherServiceImpl voucherService = applicationContext.getBean("voucherService", VoucherServiceImpl.class);
        VoucherRepository voucherRepository = applicationContext.getBean("voucherRepository", VoucherRepository.class);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Printer printer = new Printer();
        Voucher newVoucher = null;

        while (true) {
            printer.printCommandList();
            String inputCommand = reader.readLine();

            if (inputCommand.equals("create")) {
                printer.printVoucherTypeList();
                int voucherNumber = Integer.parseInt(reader.readLine());

                if (voucherNumber == 1) {
                    printer.printAmountInputInfo();
                    String amount = reader.readLine();
                    newVoucher = voucherService.createVoucher(VoucherType.FIXEDAMOUNTVOUCHER, UUID.randomUUID(), amount);
                } else if (voucherNumber == 2) {
                    printer.printPercentInputInfo();
                    String percent = reader.readLine();
                    newVoucher = voucherService.createVoucher(VoucherType.PERCENTDISCOUNTVOUCHER, UUID.randomUUID(), percent);
                }

                voucherRepository.save(newVoucher);

            } else if (inputCommand.equals("list")) {
                if (voucherService.getVoucherList().isEmpty()) {
                    printer.printVoucherListEmpty();
                    continue;
                }
                printer.printVoucherList(voucherService.getVoucherList());

            } else if (inputCommand.equals("exit")) {
                break;
            }
        }
    }
}
