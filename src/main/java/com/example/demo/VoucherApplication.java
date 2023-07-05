package com.example.demo;


import com.example.demo.controller.VoucherController;
import com.example.demo.dto.VoucherDto;
import com.example.demo.util.CommandType;
import com.example.demo.util.VoucherType;
import com.example.demo.view.VoucherView;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class VoucherApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(VoucherApplication.class);
    private final VoucherController voucherController;
    private final VoucherView voucherView;

    public VoucherApplication(VoucherController voucherController, VoucherView voucherView) {
        this.voucherController = voucherController;
        this.voucherView = voucherView;
    }

    //흐름 제어
    @Override
    public void run(String... args) throws Exception {
        voucherView.printStartingMessage();

        boolean shouldContinue = true;
        while (shouldContinue) {
            try {
                CommandType commandType = voucherView.readCommandOption();

                switch (commandType) {
                    case CREATE -> {
                        VoucherType voucherType = voucherView.readVoucherOption();
                        int amount = voucherView.readVoucherAmount(voucherType);
                        VoucherDto voucherDto = voucherController.create(voucherType, amount);
                        voucherView.printCreateMessage(voucherDto);
                    }
                    case LIST -> {
                        List<VoucherDto> voucherDtoList = voucherController.readList();
                        voucherView.printVoucherList(voucherDtoList);
                    }
                    case EXIT -> shouldContinue = false;
                    default -> throw new IllegalArgumentException(String.format("입력하신 %s은 올바르지 않은 커맨드입니다.", commandType.toString()));
                }
            } catch (IllegalArgumentException e) {
                logger.info(e.getMessage());
            }
        }
    }
}
