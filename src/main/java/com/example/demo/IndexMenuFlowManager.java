package com.example.demo;


import com.example.demo.enums.IndexMenuCommand;
import com.example.demo.view.index.IndexView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("!test")
@Slf4j
public class IndexMenuFlowManager implements CommandLineRunner {

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
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("알 수 없는 에러가 발생되었습니다. 에러 메세지 : " + e.getMessage());
                log.error("루트에서 알 수 없는 에러를 잡았습니다.", e);
            }
        }
    }
}
