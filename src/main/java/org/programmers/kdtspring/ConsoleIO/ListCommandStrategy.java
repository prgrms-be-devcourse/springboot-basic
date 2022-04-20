package org.programmers.kdtspring.ConsoleIO;

import org.programmers.kdtspring.entity.voucher.Voucher;
import org.programmers.kdtspring.repository.voucher.VoucherRepository;

import java.util.List;

public class ListCommandStrategy implements CommandStrategy {

    private final Output output;
    private final VoucherRepository voucherRepository;

    public ListCommandStrategy(Output output, VoucherRepository voucherRepository) {
        this.output = output;
        this.voucherRepository = voucherRepository;
    }

    @Override
    public void runCommand() {

        List<Voucher> vouchers = voucherRepository.findAll();

        if (vouchers.isEmpty()) {
            System.out.println("저장된 바우처가 없습니다.");
            return;
        }
        System.out.println("===== 바우처 리스트 ======");
        output.showAllVoucher();
    }
}
