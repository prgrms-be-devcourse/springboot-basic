package org.prgrms.orderApp.presentation.commandOperator.script;



public interface WarnningAndErrorScript {

    final String apologize = "An unexpected error occurred. Our company is sorry for the inconvenience.";

    // < WARNING MESSAGE >------------------------------------------------------
    final String messageBeforeExit ="ARE YOU SURE ?(yes) ";
    final String exit_WarringMessage ="Are You Sure To Exit? IF YOU EXIT the program, Your Data could be DELETED!!! ";

    // < ERROR MESSAGE >------------------------------------------------------
    final String selectedNumber_LimitError = "[ERROR] INVALID SELECTED NUMBER!! CHECK SELECTED NUMBER!!";
    final String percent_LimitError = "[ERROR] INVALID AMOUNT!! CHECK THE AMOUNT!";
    final String voucherId_DuplicateError = "[ERROR] Voucher Id Duplicate Error, Voucher Id : ";
    final String invalidValue_MustWriteDownNumber = "[ERROR] Please write it down in numbers.";

}
