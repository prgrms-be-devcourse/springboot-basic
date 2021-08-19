package com.example.kdtspringmission;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KdtSpringMissionApplication {

    public static void main(String[] args) {
        AppConfig ac = new AppConfig();
        VoucherRepository voucherRepository = ac.voucherRepository();
        new CommandLineApplication(voucherRepository).run();
    }

}
