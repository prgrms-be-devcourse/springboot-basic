package org.prgms.voucheradmin.domain.console;

import static org.prgms.voucheradmin.domain.console.Commands.*;
import static org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherTypes.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import org.prgms.voucheradmin.domain.customer.dto.CustomerDto;
import org.prgms.voucheradmin.domain.customer.service.CustomerService;
import org.prgms.voucheradmin.domain.voucher.dto.VoucherInputDto;
import org.prgms.voucheradmin.domain.voucher.entity.Voucher;
import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherTypes;
import org.prgms.voucheradmin.global.exception.WrongInputException;
import org.prgms.voucheradmin.domain.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 입출력을 담당하는 클래스입니다.
 **/
@Component
public class Console {
    private final static Logger logger = LoggerFactory.getLogger(Console.class);

    private final VoucherService voucherService;
    private final CustomerService customerService;

    public Console(VoucherService voucherService, CustomerService customerService) {
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    /**
    * 어플리케이션을 시작하는 메서드입니다.
    **/
    public void run() {
        boolean keepRun = true;

        while (keepRun) {
            try {
                showCommandList();
                Commands commands = selectCommand();

                switch (commands) {
                    case CREATE:
                        showVoucherType();
                        VoucherTypes voucherType = selectVoucherType();
                        VoucherInputDto voucherInputDto = inputAmount(voucherType);
                        Voucher createdVoucher = voucherService.createVoucher(voucherInputDto);
                        System.out.println(createdVoucher+" created");
                        break;
                    case LIST:
                        List<Voucher> vouchers = voucherService.getVouchers();
                        for(Voucher voucher : vouchers) {
                            System.out.println(voucher);
                        }
                        break;
                    case BLACKLIST:
                        List<CustomerDto> blackListedCustomers = customerService.getBlackList();
                        for(CustomerDto blackListedCustomer : blackListedCustomers) {
                            System.out.println(blackListedCustomer);
                        }
                        break;
                    default:
                        keepRun = false;
                        break;
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                System.out.println("[error] "+e.getMessage());
            }
        }
    }

    /**
     * 선택 가능한 명령들을 사용자에게 보여주는 메서드입니다.
     **/
    private void showCommandList() {
        StringBuilder commandListBuilder = new StringBuilder();

        commandListBuilder
                .append("\n=== Voucher Program ===\n")
                .append("Type exit to exit the program.\n")
                .append("Type create to create a new voucher.\n")
                .append("Type list to list all vouchers.\n")
                .append("Type blacklist to list all blacklisted customers.\n");

        System.out.println(commandListBuilder);
        System.out.print("command> ");
    }

    /**
     * 사용자가 명령을 입력하는 메서드입니다.
     * findCommand 메서드를 통하여 옳바른 입력인지 검증하고 옳바른 입력일 경우 Commands를 반환합니다.
     * 만약 옳바른 입력이 아닌 경우 WrongInputException을 발생시킵니다.
     **/
    private Commands selectCommand() throws IOException, WrongInputException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String selectedCommand = br.readLine().trim();

        Commands command = findCommand(selectedCommand);

        return command;
    }

    /**
     * 생성할 수 있는 바우처의 종류들을 사용자에게 보여주는 메서드입니다.
     **/
    private void showVoucherType() {
        StringBuilder voucherTypeStrBuilder = new StringBuilder();

        voucherTypeStrBuilder.append("\n");
        Arrays.stream(VoucherTypes.values()).forEach(voucherType -> voucherTypeStrBuilder.append(voucherType.toString()).append("\n"));
        voucherTypeStrBuilder.append("\nvoucher type> ");

        System.out.print(voucherTypeStrBuilder);
    }

    /**
     * 사용자가 생성할 바우처의 종류를 선택하는 메서드입니다.
     * 옳바른 입력일 경우 VoucherTypes를 반환하지만 그렇지 않은 경우 WrongInputException을 발생시킵니다.
     **/
    private VoucherTypes selectVoucherType() throws IOException, WrongInputException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String selectedVoucherTypeId = br.readLine().trim();
        System.out.println();

        return findVoucherType(selectedVoucherTypeId).orElseThrow(WrongInputException::new);
    }

    /**
     * 사용자가 생성할 바우처의 할인 amount 또는 percent를 입력하는 메서드입니다.
     * 정상적으로 입력이 이루어진 경우 VoucherInputDto 반환하지만 그렇지 않은 경우 WrongInputException을 발생시킵니다.
     **/
    private VoucherInputDto inputAmount(VoucherTypes voucherType) throws IOException, WrongInputException {
        System.out.print(voucherType == FIXED_AMOUNT ? "amount> " : "percent> ");

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            return new VoucherInputDto(voucherType, Long.parseLong(br.readLine().trim()));
        }catch (NumberFormatException numberFormatException) {
            throw new WrongInputException();
        }
    }
}
