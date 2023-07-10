package com.prgmrs.voucher.controller;

import com.prgmrs.voucher.dto.request.AssignmentRequest;
import com.prgmrs.voucher.dto.response.AssignmentResponse;
import com.prgmrs.voucher.exception.WrongRangeFormatException;
import com.prgmrs.voucher.service.AssignmentService;
import org.springframework.stereotype.Component;

@Component
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    public AssignmentResponse assignVoucher(AssignmentRequest assignmentRequest) throws WrongRangeFormatException {
        return assignmentService.assignVoucher(assignmentRequest);
    }

    public AssignmentResponse freeVoucher(AssignmentRequest assignmentRequest) throws WrongRangeFormatException {
        return assignmentService.freeVoucher(assignmentRequest);
    }
}
