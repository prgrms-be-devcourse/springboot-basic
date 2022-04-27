package org.prgrms.vouchermanager.infrastructure.shell;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.prgrms.vouchermanager.domain.voucher.domain.Voucher;
import org.prgrms.vouchermanager.domain.voucher.service.VoucherService;
import org.prgrms.vouchermanager.infrastructure.dto.VoucherDto.VoucherListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.text.MessageFormat;
import java.util.List;

@ShellComponent
public class VoucherManagerShell {

    private final Logger log = LoggerFactory.getLogger(VoucherManagerShell.class);
    private final VoucherService voucherService;

    public VoucherManagerShell(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    /**
     * 쉘 명령을 받아 바우처 생성
     * <p>
     * 명령 형식 - {명령어} {타입} {수량}
     * 예) create fixed 10
     */
    @ShellMethod("create a voucher")
    public String create(String type, long amount) {
        try {
            voucherService.createVoucher(type, amount);
            return MessageFormat.format("{0} voucher created.", type);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
        }
        return "fail to create a voucher.";
    }

    /**
     * 쉘 명령을 받아 바우처 목록 조회
     * <p>
     * 명령 형식 - {명령어}
     * 예) list
     */
    @ShellMethod("show voucher list")
    public VoucherListResponse list() throws JsonProcessingException {
        final List<Voucher> vouchers = voucherService.findVouchers();

        return new VoucherListResponse(vouchers);
    }
}
