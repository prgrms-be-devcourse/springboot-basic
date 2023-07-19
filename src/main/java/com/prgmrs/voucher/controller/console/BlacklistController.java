package com.prgmrs.voucher.controller.console;

import com.prgmrs.voucher.controller.console.wrapper.ResponseDTO;
import com.prgmrs.voucher.enums.StatusCode;
import com.prgmrs.voucher.exception.WrongRangeFormatException;
import com.prgmrs.voucher.service.BlacklistService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

@Component
public class BlacklistController {
    private final BlacklistService blacklistService;

    public BlacklistController(BlacklistService blacklistService) {
        this.blacklistService = blacklistService;
    }

    public ResponseDTO<?> findAll() {
        try {
            return new ResponseDTO<>(blacklistService.findAll(), StatusCode.REQUEST_OK);
        } catch (WrongRangeFormatException | DataAccessException e) {
            return new ResponseDTO<>(e.getMessage(), StatusCode.BAD_REQUEST);
        }
    }
}
