package org.programmers.kdtspringvoucherweek1.voucher;
import org.programmers.kdtspringvoucherweek1.io.Input;
import org.programmers.kdtspringvoucherweek1.io.Output;
import org.programmers.kdtspringvoucherweek1.log.LogLevel;
import org.programmers.kdtspringvoucherweek1.voucher.repository.VoucherRepository;
import org.programmers.kdtspringvoucherweek1.error.Error;
import org.programmers.kdtspringvoucherweek1.voucher.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherManager {
    private final Input input;
    private final Output output;
    private final VoucherRepository voucherRepository;
    private final VoucherService voucherService;
    private final VoucherProperties voucherProperties;

    @Autowired
    public VoucherManager(Input input, Output output, VoucherRepository voucherRepository,
                          VoucherService voucherService, VoucherProperties voucherProperties) {
        this.input = input;
        this.output = output;
        this.voucherRepository = voucherRepository;
        this.voucherService = voucherService;
        this.voucherProperties = voucherProperties;
    }

    public void run() {
        while (true) {
            output.mainMenu(voucherProperties.getVersion());
            String inputCommand = input.nextLine();
            switch(inputCommand) {
                case "exit" -> {
                    output.msg("bye");
                    output.logging(LogLevel.INFO, "exit");
                    return ;
                }
                case "create" -> {
                    Voucher voucher = voucherService.createVoucher();
                    output.logging(LogLevel.INFO, "voucher create : " + voucher.getVoucherId());
                    voucherRepository.save(voucher);
                    output.logging(LogLevel.INFO, "voucher save : " + voucher.getVoucherId());
                }
                case "list" -> {
                    voucherService.readList();
                    output.logging(LogLevel.INFO, "Check the list");
                }
                default -> {
                    output.msg(Error.INVALID_COMMAND);
                    output.logging(LogLevel.WARN, Error.INVALID_COMMAND);
                }
            }
        }
    }
}
