package org.prgms.management.service;

import org.prgms.management.entity.Voucher;
import org.prgms.management.entity.VoucherType;
import org.prgms.management.io.Input;
import org.prgms.management.io.Output;
import org.prgms.management.repository.BlackListRepository;
import org.prgms.management.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.UUID;

@Service
public class ConsoleManagementService implements ManagementService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final VoucherRepository voucherRepository;
    private final BlackListRepository blackListRepository;
    private final Output output;
    private final Input input;

    public ConsoleManagementService(VoucherRepository voucherRepository,
                                    BlackListRepository blackListRepository,
                                    Output output, Input input) {
        this.voucherRepository = voucherRepository;
        this.blackListRepository = blackListRepository;
        this.output = output;
        this.input = input;
    }

    @Override
    public void run() {
        System.out.println("=== Voucher Program ===");
        label:
        while (true) {
            output.init();

            String command = input.getInput("Enter a command: ");

            switch (command) {
                case "exit":
                    break label;
                case "create":
                    createVoucher();
                    break;
                case "list":
                    getVoucherList();
                    break;
                case "blacklist":
                    getBlackList();
                    break;
                default:
                    logger.error("wrong input at input command -> {}\n", command);
                    break;
            }
        }
    }

    @Override
    public void createVoucher() {
        output.chooseVoucherType();

        String voucherType = input.getInput("VoucherType(fixed <- FixedAmountVoucher, " +
                "percent <- PercentAmountVoucher): ");

        String voucherName = input.getInput("\nVoucherName: ");

        String temp = input.getInput("DiscountNum: ");

        if (!temp.matches("[0-9]+")) {
            logger.error("Wrong input at discountNum -> {}", temp);
            return;
        }

        int discountNum = Integer.parseInt(temp);

        Voucher voucher = VoucherType.createVoucher(
                UUID.randomUUID(), discountNum, voucherName, voucherType);

        if (voucherRepository.save(voucher))
            output.voucherCreateSuccess();
        else
            output.voucherCreateFail();
    }

    @Override
    public void getVoucherList() {
        Map<UUID, Voucher> voucherList = voucherRepository.getAll();
        output.voucherList(voucherList);
    }

    @Override
    public void getBlackList() {
        Map<UUID, String> blackList = blackListRepository.getAll();
        output.blackList(blackList);
    }
}
