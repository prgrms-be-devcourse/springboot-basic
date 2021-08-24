package org.prgrms.orderApp.presentation.commandOperator.script;



public interface WarnningAndErrorScript {

    String apologize = "An unexpected error occurred. Our company is sorry for the inconvenience.";

    // < WARNING MESSAGE >------------------------------------------------------
    String messageBeforeExit ="ARE YOU SURE ?(yes) ";
    String exit_WarringMessage ="Are You Sure To Exit? ";

    // < ERROR MESSAGE >------------------------------------------------------
    String selectedNumber_LimitError = "[ERROR] INVALID SELECTED NUMBER!! CHECK SELECTED NUMBER!!";
    String percent_LimitError = "[ERROR] INVALID AMOUNT!! CHECK THE AMOUNT!";
    String voucherId_DuplicateError = "[ERROR] Voucher Id Duplicate Error, Voucher Id : ";
    String invalidValue_MustWriteDownNumber = "[ERROR] Please write it down in numbers.";

}
