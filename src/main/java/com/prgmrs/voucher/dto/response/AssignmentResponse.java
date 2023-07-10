package com.prgmrs.voucher.dto.response;

import com.prgmrs.voucher.model.Assignment;

public class AssignmentResponse {
    private final Assignment assignment;
    private final String username;

    public AssignmentResponse(Assignment assignment, String username) {
        this.assignment = assignment;
        this.username = username;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public String getUsername() { return username; }
}
