package org.prgrms.kdt.command.service.voucher;

import org.prgrms.kdt.command.io.Input;
import org.prgrms.kdt.command.service.CommandService;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.UUID;

@Service
public class VoucherDeleteService implements CommandService {
    private final VoucherService voucherService;

    public VoucherDeleteService(final VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @Override
    public void commandRun() {
        System.out.println("voucher_id를 입력해주세요.");
        final UUID voucherId = toUUID(Input.input().getBytes());

        if (voucherService.getVoucher(voucherId).isPresent()) {
            voucherService.deleteVoucher(voucherId);
            System.out.println("해당 Voucher가 삭제되었습니다.");
        } else
            System.out.println("voucher_id에 해당하는 Voucher가 존재하지 않습니다.");
    }

    UUID toUUID(final byte[] bytes) {
        final var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
