package com.example.demo;


import com.example.demo.util.IndexMenuCommand;
import com.example.demo.view.index.IndexView;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IndexMenuFlowManager implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(IndexMenuFlowManager.class);

    private final VoucherFlowManager voucherFlowManager;
    private final CustomerFlowManager customerFlowManager;
    private final IndexView indexView;

    @Override
    public void run(String... args) throws Exception {
        boolean shouldContinue = true;
        while (shouldContinue) {
            try {
                IndexMenuCommand command = indexView.readCommandOption();

                switch (command) {
                    case CUSTOMER -> {
                        customerFlowManager.runMenu();
                    }
                    case VOUCHER -> {
                        voucherFlowManager.runMenu();
                    }
                    case EXIT -> shouldContinue = false;
                    default -> throw new IllegalArgumentException(String.format("입력하신 %d은 올바르지 않은 숫자 커맨드입니다. 1 ~ 3 사이 숫자를 입력하세요.", command.getCommandNum()));
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                System.out.println(e.getMessage());
            }
        }
    }
}
