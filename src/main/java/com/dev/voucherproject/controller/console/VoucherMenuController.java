package com.dev.voucherproject.controller.console;

import com.dev.voucherproject.model.menu.VoucherMenu;
import com.dev.voucherproject.model.storage.voucher.VoucherDao;
import com.dev.voucherproject.model.voucher.*;
import com.dev.voucherproject.view.Console;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class VoucherMenuController implements MenuController {
    private final Console console;
    private final VoucherDao voucherDao;

    public VoucherMenuController(Console console, VoucherDao voucherDao) {
        this.console = console;
        this.voucherDao = voucherDao;
    }

    @Override
    public void execute() {
        boolean turnON = true;

        while (turnON) {
            console.printVoucherMenu();
            String inputVoucherMenuSelection = console.inputVoucherMenuSelection();

            VoucherMenu voucherMenu = VoucherMenu.convertInputToMenu(inputVoucherMenuSelection);

            switch (voucherMenu) {
                case CREATE -> {
                    VoucherVo voucherVo = inputVoucherVo();
                    voucherDao.insert(Voucher.of(UUID.randomUUID(), voucherVo.voucherPolicy(), voucherVo.discountNumber()));
                }
                case LIST -> {
                    List<Voucher> vouchers = voucherDao.findAll();
                    List<VoucherDto> dtos = convertToVoucherDtos(vouchers);

                    console.printAllVouchers(dtos);
                }
                case FIND_BY_ID -> {
                    UUID voucherId = inputUuid();
                    Optional<Voucher> optionalVoucher = voucherDao.findById(voucherId);

                    if (optionalVoucher.isPresent()) {
                        Voucher voucher = optionalVoucher.get();
                        console.printVoucher(VoucherDto.fromEntity(voucher));
                    }
                }
                case FIND_BY_POLICY -> {
                    console.printSelectVoucherPolicy();
                    String policyInput = console.inputVoucherPolicySelection();

                    VoucherPolicy voucherPolicy = convertStringInputToPolicy(policyInput);
                    List<Voucher> vouchers = voucherDao.findAllByPolicy(voucherPolicy);
                    List<VoucherDto> dtos = convertToVoucherDtos(vouchers);

                    console.printAllVouchers(dtos);
                }
                case DELETE_ALL -> {
                    voucherDao.deleteAll();
                }
                case DELETE_BY_ID -> {
                    UUID voucherId = inputUuid();

                    voucherDao.deleteById(voucherId);
                }
                case UPDATE -> {
                    UUID voucherId = inputUuid();
                    VoucherVo voucherVo = inputVoucherVo();

                    voucherDao.update(Voucher.of(voucherId, voucherVo.voucherPolicy(), voucherVo.discountNumber()));
                }
                case MAIN -> {
                    turnON = false;
                }
            }
            console.newLine();
        }
    }

    private List<VoucherDto> convertToVoucherDtos(List<Voucher> vouchers) {
        return vouchers.stream()
            .map(VoucherDto::fromEntity)
            .toList();
    }

    private VoucherVo inputVoucherVo() {
        console.printSelectVoucherPolicy();
        String policyInput = console.inputVoucherPolicySelection();

        VoucherPolicy voucherPolicy = convertStringInputToPolicy(policyInput);
        long discountNumber = selectPolicy(voucherPolicy);

        return new VoucherVo(voucherPolicy, discountNumber);
    }

    private UUID inputUuid() {
        String uuidInput = console.inputUuid();
        return UUID.fromString(uuidInput);
    }

    private VoucherPolicy convertStringInputToPolicy(String policyInput) {
        if (policyInput.equals("1")) {
            return VoucherPolicy.FIXED_AMOUNT_VOUCHER;
        }

        return VoucherPolicy.PERCENT_DISCOUNT_VOUCHER;
    }

    private long selectPolicy(final VoucherPolicy voucherPolicy) {
        long discountNumber;

        if (voucherPolicy == VoucherPolicy.FIXED_AMOUNT_VOUCHER) {
            console.printFixAmountPolicyArgs();
            discountNumber = console.inputAmount();

            return discountNumber;
        }

        console.printDiscountVoucherPolicyArgs();
        discountNumber = console.inputPercent();

        return discountNumber;
    }
}
