package org.prgrms.kdt;

import org.prgrms.kdt.config.AppConfiguration;
import org.prgrms.kdt.customer.CustomerRepository;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherRepository;
import org.prgrms.kdt.IO.Input;
import org.prgrms.kdt.IO.Output;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Optional;
import java.util.UUID;

public class VoucherTester {

    private final String commandGuide = "사용 가능한 명령은 4가지 입니다. 한번에 하나의 명령만 가능합니다. 대소문자는 구분하지 않습니다.\n바우처 생성하기 : create\nvoucher 리스트 보기 : list\nblackcustomer 리스트 보기 : black\n종료하기 : exit 를 입력하세요.";
    private final String voucherGuide = "fixed형 voucher를 생성하려면 f를, percent형 voucher를 생성하려면 p를 입력하세요.";
    private final String voucherInfoGuide = "fixed amount 또는 percent discount를 입력하세요.";
    private final String voucherCreateSuccess = "바우처 생성 성공";
    private final String inputErrorMsg = "입력 형식이 잘못되었습니다. 처음부터 입력해 주세요.";
    private final String exitMsg = "바우처 관리 프로그램을 종료합니다.";
    private final String endLine = "======================================================================";
    private boolean exitProgram = false;

    public void run() {
        Input input = new Console();
        Output output = new Console();

        var applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AppConfiguration.class);
        var environment = applicationContext.getEnvironment();
        environment.setActiveProfiles("local");
        applicationContext.refresh();

        var voucherRepository = applicationContext.getBean(VoucherRepository.class);
        var customerRepository = applicationContext.getBean(CustomerRepository.class);

        while (!exitProgram) {
            Optional<CommandType> commandType = CommandType.matchCommandType(input.input(commandGuide));
            if (commandType.isEmpty()) {
                output.print(inputErrorMsg);
                continue;
            }
            switch (commandType.get()) {
                case EXIT:
                    output.print(exitMsg);
                    exitProgram = true;
                    break;

                case CREATE:
                    Optional<VoucherType> voucherType = VoucherType.matchVoucherType(input.input(voucherGuide));
                    int discountInfo = Integer.parseInt(input.input(voucherInfoGuide));

                    if (!isValidVoucherInfo(voucherType, discountInfo)) {
                        output.print(inputErrorMsg);
                        break;
                    }

                    insertVoucherRepository(voucherType.get(), discountInfo, voucherRepository);
                    output.print(voucherCreateSuccess);
                    break;

                case LIST:
                    output.print("바우처는 총 " + voucherRepository.numVouchers() + "개 입니다.");
                    output.print(voucherRepository.getList());
                    break;

                case BLACK:
                    output.print(customerRepository.getList());
                    break;

                default:
                    output.print(inputErrorMsg);
                    break;
            }
            output.print(endLine);
        }
        applicationContext.close();
    }

    private boolean isValidVoucherInfo(Optional<VoucherType> voucherType, int discountInfo) {
        if (voucherType.isEmpty()) return false;
        switch (voucherType.get()) {
            case FIXED:
                if (discountInfo < 0) return false;
                break;
            case PERCENT:
                if (discountInfo < 1 || discountInfo > 100) return false;
                break;
        }
        return true;
    }

    private void insertVoucherRepository(VoucherType voucherType, long voucherInfo, VoucherRepository voucherRepository) {
        Voucher voucher = null;
        switch (voucherType) {
            case FIXED:
                voucher = new FixedAmountVoucher(UUID.randomUUID(), voucherInfo);
                break;
            case PERCENT:
                voucher = new PercentDiscountVoucher(UUID.randomUUID(), voucherInfo);
                break;
        }
        if (voucher != null) voucherRepository.insert(voucher);
    }
}

