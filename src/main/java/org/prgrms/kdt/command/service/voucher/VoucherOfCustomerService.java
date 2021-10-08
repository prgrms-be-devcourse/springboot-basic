package org.prgrms.kdt.command.service.voucher;

import org.prgrms.kdt.command.io.Input;
import org.prgrms.kdt.command.service.CommandService;
import org.prgrms.kdt.customer.Customer;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherOfCustomerService implements CommandService {
    private final VoucherService voucherService;

    public VoucherOfCustomerService(final VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @Override
    public void commandRun() {
        System.out.println("voucher_id를 입력해주세요.");
        final UUID voucherId = toUUID(Input.input().getBytes());

        if (voucherService.getVoucher(voucherId).isPresent()) {
            final List<Customer> customerList = voucherService.getCustomer(voucherId);
            for (final Customer customer : customerList) {
                System.out.println(customer);
            }
        } else
            System.out.println("잘못된 voucher_id 입력입니다.");
    }

    UUID toUUID(final byte[] bytes) {
        final var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
