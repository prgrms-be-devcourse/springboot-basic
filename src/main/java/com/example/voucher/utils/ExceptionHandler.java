package com.example.voucher.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.voucher.io.Console;

public class ExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    private static final Console console = new Console();

    private ExceptionHandler(Exception e) {

    }

    public static void handleException(Exception e) {

        if (e instanceof IllegalArgumentException) {
            logger.error(e.getMessage());
            console.printCustomMessage(e.getMessage());
        } else if (e instanceof Exception) {
            logger.error(e.getMessage());
            console.printCustomMessage(e.getMessage());
        }
    }

}

