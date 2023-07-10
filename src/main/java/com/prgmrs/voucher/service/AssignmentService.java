package com.prgmrs.voucher.service;

import com.prgmrs.voucher.dto.request.AssignmentRequest;
import com.prgmrs.voucher.dto.response.AssignmentResponse;
import com.prgmrs.voucher.enums.AssignmentSelectionType;
import com.prgmrs.voucher.exception.WrongRangeFormatException;
import com.prgmrs.voucher.model.Assignment;
import com.prgmrs.voucher.model.User;
import com.prgmrs.voucher.model.Voucher;
import com.prgmrs.voucher.model.validator.OrderValidator;
import com.prgmrs.voucher.model.validator.UserValidator;
import com.prgmrs.voucher.repository.AssignmentRepository;
import com.prgmrs.voucher.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentService {
    private final UserValidator userValidator;
    private final OrderValidator orderValidator;
    private final UserRepository userRepository;
    private final AssignmentRepository assignmentRepository;

    public AssignmentService(UserValidator userValidator, OrderValidator orderValidator, UserRepository userRepository, AssignmentRepository assignmentRepository) {
        this.userValidator = userValidator;
        this.orderValidator = orderValidator;
        this.userRepository = userRepository;
        this.assignmentRepository = assignmentRepository;
    }

    public AssignmentResponse assignVoucher(AssignmentRequest assignmentRequest) throws WrongRangeFormatException {
        return getAssignmentResponse(assignmentRequest, AssignmentSelectionType.ASSIGN_VOUCHER);
    }

    public AssignmentResponse freeVoucher(AssignmentRequest assignmentRequest) {
        return getAssignmentResponse(assignmentRequest, AssignmentSelectionType.FREE_VOUCHER);
    }

    private AssignmentResponse getAssignmentResponse(AssignmentRequest assignmentRequest, AssignmentSelectionType assignmentSelectionType) {
        String username = assignmentRequest.getUsername();
        String order = assignmentRequest.getOrder();
        List<Voucher> voucherList = assignmentRequest.getVoucherList();

        if (!userValidator.isValidNameFormat(username)) {
            throw new WrongRangeFormatException("incorrect token format");
        }

        if (!orderValidator.isValidOrder(order, voucherList)) {
            throw new WrongRangeFormatException("possible value out of range");
        }

        int convertedOrder = Integer.parseInt(order);
        Voucher voucher = voucherList.get(convertedOrder-1);

        User user = userRepository.findByUsername(username);

        Assignment assignment = new Assignment(user.getUserId(), voucher.getVoucherId());

        switch(assignmentSelectionType) {
            case ASSIGN_VOUCHER -> assignmentRepository.save(assignment);
            case FREE_VOUCHER -> assignmentRepository.free(assignment);
        }

        return new AssignmentResponse(assignment, username);
    }
}
