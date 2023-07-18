package com.wonu606.vouchermanager;

import com.wonu606.vouchermanager.console.cableadapter.FrontCableAdapter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class VoucherManagerCommandLineRunner implements CommandLineRunner {
    private final FrontCableAdapter frontCableAdapter;

    public VoucherManagerCommandLineRunner(FrontCableAdapter frontCableAdapter) {
        this.frontCableAdapter = frontCableAdapter;
    }

    @Override
    public void run(String... args) {
        frontCableAdapter.run();
    }
}
