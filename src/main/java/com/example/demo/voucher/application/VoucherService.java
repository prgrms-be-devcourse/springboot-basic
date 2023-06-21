package com.example.demo.voucher.application;

import com.example.demo.common.io.Input;
import com.example.demo.common.io.Output;
import com.example.demo.voucher.domain.Voucher;
import com.example.demo.voucher.domain.repository.VoucherRepository;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.UUID;

public class VoucherService {
    private final VoucherRepository voucherRepository;
    private final Input input;
    private final Output output;

    public VoucherService(VoucherRepository voucherRepository, Input input, Output output) {
        this.voucherRepository = voucherRepository;
        this.input = input;
        this.output = output;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
    }

    public void useVoucher(Voucher voucher) {

    }

    public void createVoucher() {
        printMenu();
        String voucherTypeInput = input.readLine();
        UUID voucherId = UUID.randomUUID();

        VoucherType.fromCounter(voucherTypeInput).ifPresentOrElse(
                voucherType -> {
                    output.printLine(voucherType.getPromptMessage());
                    long value = Long.parseLong(input.readLine());
                    voucherRepository.insert(voucherType.createVoucher(voucherId, value));
                },
                () -> output.printLine("Invalid voucher type")
        );

    }

    public void listVouchers() {
        output.printLine("Vouchers:");
        voucherRepository.findAll()
                .stream()
                .map(Voucher::toString)
                .forEach(output::printLine);
    }

    private void printMenu() {
        output.printLine("Please enter the voucher type:");
        Arrays.stream(VoucherType.values())
                .map(type -> type.getCounter() + " : " + type.getName())
                .forEach(output::printLine);
    }
}
