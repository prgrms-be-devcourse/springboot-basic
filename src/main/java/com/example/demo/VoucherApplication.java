package com.example.demo;


import com.example.demo.controller.VoucherController;
import com.example.demo.dto.VoucherDto;
import com.example.demo.util.CommandType;
import com.example.demo.util.VoucherType;
import com.example.demo.view.VoucherView;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class VoucherApplication implements CommandLineRunner {

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
            CommandType commandType = voucherView.readCommandOption();

            switch (commandType) {
                case CREATE -> {
                    VoucherType voucherType = voucherView.readVoucherOption();
                    Integer amount = voucherView.readVoucherAmount(voucherType);
                    voucherController.create(voucherType, amount);
                    voucherView.printVoucherCreateMessage(voucherType, amount);
                }
                case LIST -> {
                    List<VoucherDto> voucherDtoList = voucherController.readList();
                    voucherView.printVoucherList(voucherDtoList);
                }
                case EXIT -> shouldContinue = false;
                default -> throw new IllegalArgumentException("올바르지 않은 커맨드입니다.");
            }
        }
    }
}
