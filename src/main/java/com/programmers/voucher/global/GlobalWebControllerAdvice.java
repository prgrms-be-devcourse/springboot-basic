package com.programmers.voucher.global;

import com.programmers.voucher.global.util.CommonErrorMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice(annotations = Controller.class)
public class GlobalWebControllerAdvice {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalApiControllerAdvice.class);

    @ExceptionHandler(NoSuchElementException.class)
    public String noSuchElementExHandle(NoSuchElementException ex, Model model) {
        ErrorResult errorResult = new ErrorResult("404", ex.getMessage());
        model.addAttribute("errorResult", errorResult);
        return "errorPage";
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public String duplicateKeyExHandle(DuplicateKeyException ex, Model model) {
        ErrorResult errorResult = new ErrorResult("409", ex.getMessage());
        model.addAttribute("errorResult", errorResult);
        return "errorPage";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String illegalArgumentExHandle(IllegalArgumentException ex, Model model) {
        ErrorResult errorResult = new ErrorResult("400", ex.getMessage());
        model.addAttribute("errorResult", errorResult);
        return "errorPage";
    }

    @ExceptionHandler(RuntimeException.class)
    public String runtimeExHandle(RuntimeException ex, Model model) {
        LOG.error(CommonErrorMessages.UNEXPECTED_EXCEPTION, ex);
        ErrorResult errorResult = new ErrorResult("500", CommonErrorMessages.UNEXPECTED_EXCEPTION);
        model.addAttribute("errorResult", errorResult);
        return "errorPage";
    }
}
