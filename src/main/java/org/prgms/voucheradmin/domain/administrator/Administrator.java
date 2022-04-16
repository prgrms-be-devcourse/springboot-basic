package org.prgms.voucheradmin.domain.administrator;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.prgms.voucheradmin.domain.console.service.InputService;
import org.prgms.voucheradmin.domain.console.service.OutputService;
import org.prgms.voucheradmin.domain.console.service.enums.Command;
import org.prgms.voucheradmin.domain.console.service.enums.CommandAboutVoucher;
import org.prgms.voucheradmin.domain.customer.dto.CustomerDto;
import org.prgms.voucheradmin.domain.customer.service.CustomerService;
import org.prgms.voucheradmin.domain.voucher.dto.VoucherCreateReqDto;
import org.prgms.voucheradmin.domain.voucher.dto.VoucherUpdateReqDto;
import org.prgms.voucheradmin.domain.voucher.entity.Voucher;
import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType;
import org.prgms.voucheradmin.domain.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static org.prgms.voucheradmin.domain.console.service.enums.CommandAboutVoucher.*;

/**
 * 로직 수행을 위해 필요한 메서드를 호출하는 클래스 입니다.
 */
@Component
public class Administrator {
    private final static Logger logger = LoggerFactory.getLogger(Administrator.class);

    private final OutputService outputService;
    private final InputService inputService;
    private final VoucherService voucherService;
    private final CustomerService customerService;

    public Administrator(OutputService outputService, InputService inputService, VoucherService voucherService, CustomerService customerService) {
        this.outputService = outputService;
        this.inputService = inputService;
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    /**
     * 어플리케이션을 시작하는 메서드입니다.
     **/
    public void run() {
        while (true) {
            try {
                outputService.showCommandList();
                Command command = inputService.selectCommand();

                switch (command) {
                    case VOUCHER:
                        outputService.showCommandAboutVoucher();
                        CommandAboutVoucher commandAboutVoucher = inputService.selectCommandAboutVoucher();
                        doCommandAboutVoucher(commandAboutVoucher);
                        break;
                    case CUSTOMER:
                        break;
                    case BLACKLIST:
                        List<CustomerDto> blackListedCustomers = customerService.getBlackList();
                        outputService.showBlacklist(blackListedCustomers);
                        break;
                    default:
                        return;
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }

    /**
     * 바우처에서 선택된 추가 명령에 따라 필요한 메서드 실행을 담당하는 메서드입니다.
     **/
    private void doCommandAboutVoucher(CommandAboutVoucher commandAboutVoucher) throws IOException {
        switch (commandAboutVoucher) {
            case CREATE:
                outputService.showVoucherType();
                VoucherType voucherTypeForCreate = inputService.selectVoucherType();
                long amountForCreate = inputService.inputAmount(voucherTypeForCreate);

                Voucher createdVoucher = voucherService.createVoucher(new VoucherCreateReqDto(voucherTypeForCreate, amountForCreate));
                outputService.showVoucher(createdVoucher, CREATE);
                break;
            case READ:
                List<Voucher> vouchers = voucherService.getVouchers();
                outputService.showVoucherList(vouchers);
                break;
            case UPDATE:
                UUID voucherIdForUpdate = inputService.inputVoucherId();
                Voucher voucher = voucherService.getVoucherById(voucherIdForUpdate);
                outputService.showVoucher(voucher, READ);

                outputService.showVoucherType();
                VoucherType voucherTypeForUpdate = inputService.selectVoucherType();
                long amountForUpdate = inputService.inputAmount(voucherTypeForUpdate);

                Voucher updatedVoucher = voucherService.updateVoucher(new VoucherUpdateReqDto(voucher.getVoucherId(), voucherTypeForUpdate, amountForUpdate));
                outputService.showVoucher(updatedVoucher, UPDATE);
                break;
            case DELETE:
                UUID voucherIdForDelete = inputService.inputVoucherId();
                voucherService.deleteVoucher(voucherIdForDelete);
                break;
        }
    }

}
