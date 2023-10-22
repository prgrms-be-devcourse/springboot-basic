package com.pgms.part1;

import com.pgms.part1.domain.voucher.controller.VoucherController;
import com.pgms.part1.util.file.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ProgramRunner implements CommandLineRunner {
    private final Logger log = LoggerFactory.getLogger(ProgramRunner.class);
    private final VoucherController voucherController;
    private Boolean isRunning = true;

    public ProgramRunner(VoucherController voucherController) {
        this.voucherController = voucherController;
    }

    @Override
    public void run(String... args) throws Exception {
        while(isRunning){
            try{
                voucherController.getMenu();
            }
            catch (Exception e){
                log.warn(e.getMessage());
            }
        }
    }
}
