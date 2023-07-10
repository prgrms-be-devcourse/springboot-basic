package com.prgmrs.voucher.repository;

import com.prgmrs.voucher.model.Assignment;

public interface AssignmentRepository {
    void save(Assignment assignment);

    void free(Assignment assignment);
}
