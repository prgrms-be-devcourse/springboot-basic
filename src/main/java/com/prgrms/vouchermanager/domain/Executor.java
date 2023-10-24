package com.prgrms.vouchermanager.domain;

import com.prgrms.vouchermanager.exception.EmptyListException;
import com.prgrms.vouchermanager.exception.NotCorrectCommand;
import com.prgrms.vouchermanager.exception.NotCorrectForm;
import com.prgrms.vouchermanager.exception.NotCorrectId;

import java.util.List;
import java.util.UUID;

public interface Executor {
    void create();

    void list() throws EmptyListException;
}
