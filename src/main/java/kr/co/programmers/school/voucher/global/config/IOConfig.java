package kr.co.programmers.school.voucher.global.config;

import kr.co.programmers.school.voucher.global.view.InputView;
import kr.co.programmers.school.voucher.global.view.OutputView;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class IOConfig {
    @Bean
    public InputView inputView() {
        return new InputView();
    }

    @Bean
    public OutputView outputView() {
        return new OutputView();
    }
}