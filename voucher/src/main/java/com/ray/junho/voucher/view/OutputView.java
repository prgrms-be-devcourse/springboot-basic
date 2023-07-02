package com.ray.junho.voucher.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OutputView {

    private static final Logger logger = LoggerFactory.getLogger(OutputView.class);

    public void print(String message) {
        logger.info(message);
    }
}
