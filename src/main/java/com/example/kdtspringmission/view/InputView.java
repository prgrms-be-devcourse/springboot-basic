package com.example.kdtspringmission.view;

import com.example.kdtspringmission.Command;
import java.util.UUID;

public interface InputView {

    Command getCommand();

    String nextLine();

    String getCustomerName();

    UUID getVoucherId();
}
