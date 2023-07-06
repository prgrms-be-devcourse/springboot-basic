package com.dev.voucherproject.controller.console;

import com.dev.voucherproject.model.menu.VoucherMenu;
import com.dev.voucherproject.model.storage.voucher.VoucherDao;
import com.dev.voucherproject.model.voucher.*;
import com.dev.voucherproject.model.storage.voucher.VoucherStorage;
import com.dev.voucherproject.view.Console;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class VoucherMenuController implements MenuController {
    private final Console console;
    private final VoucherStorage voucherStorage;
    private final VoucherDao voucherDao;

    public VoucherMenuController(Console console, VoucherStorage voucherStorage, VoucherDao voucherDao) {
        this.console = console;
        this.voucherStorage = voucherStorage;
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
                    console.printSelectVoucherPolicy();

                    VoucherVo voucherVo = getVoucherVo();

                    voucherStorage.insert(Voucher.of(UUID.randomUUID(), voucherVo.voucherPolicy(), voucherVo.discountNumber()));
                }
                case LIST -> {
                    List<Voucher> vouchers = voucherStorage.findAll();
                    List<VoucherDto> dtos = getVoucherDtos(vouchers);

                    console.printAllVouchers(dtos);
                }
                case FIND_BY_ID -> {
                    UUID voucherId = getUuid();

                    Optional<Voucher> optionalVoucher = voucherDao.findById(voucherId);

                    if (optionalVoucher.isPresent()) {
                        Voucher voucher = optionalVoucher.get();

                        console.printVoucher(VoucherDto.fromEntity(voucher));
                    }
                }
                case FIND_BY_POLICY -> {
                    String policyInput = console.inputVoucherPolicySelection();

                    VoucherPolicy voucherPolicy = VoucherPolicy.convertStringInputToPolicy(policyInput);
                    List<Voucher> vouchers = voucherDao.findAllByPolicy(voucherPolicy);
                    List<VoucherDto> dtos = getVoucherDtos(vouchers);

                    console.printAllVouchers(dtos);
                }
                case DELETE_ALL -> {
                    voucherDao.deleteAll();
                }
                case DELETE_BY_ID -> {
                    UUID voucherId = getUuid();

                    voucherDao.deleteById(voucherId);
                }
                case UPDATE -> {
                    UUID voucherId = getUuid();

                    VoucherVo voucherVo = getVoucherVo();

                    voucherDao.update(Voucher.of(voucherId, voucherVo.voucherPolicy(), voucherVo.discountNumber()));
                }
                case MAIN -> {
                    turnON = false;
                }
            }
            console.newLine();
        }
    }

    private static List<VoucherDto> getVoucherDtos(List<Voucher> vouchers) {
        List<VoucherDto> dtos = vouchers.stream()
            .map(VoucherDto::fromEntity)
            .toList();
        return dtos;
    }

    private VoucherVo getVoucherVo() {
        String policyInput = console.inputVoucherPolicySelection();

        VoucherPolicy voucherPolicy = VoucherPolicy.convertStringInputToPolicy(policyInput);
        long discountNumber = selectPolicy(voucherPolicy);

        return new VoucherVo(voucherPolicy, discountNumber);
    }

    private UUID getUuid() {
        String uuidInput = console.inputUuid();
        return UUID.fromString(uuidInput);
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
