package org.prgrms.orderApp.CMDProgramForOrderApp.console;

// 사용자들에게 전달될 안내메시지 모음(error 포함)
public enum Script {

    // < BASIC MESSAGE>------------------------------------------------------
    StartMessage("=== Voucher Program === \n"+
                 "Type exit to exit the program\n" +
                 "Type created to a new voucher\n" +
                 "Type list to list all vouchers"),
    GuideMessage("=========================================== \n"+
            "Please Select The Number To Want To Do \n"),
    InputUserSelectedMenuNumber_GuideMessage("[1] CREATE_VOUCHER \n[2] SHOW ALL VOUCHER LIST \n" +
            "[3] CREATE ORDER \n[4] SHOW ALL ORDER LIST \n[5] CONNECT FILE DB \n[6] EXIT " ),
    InputUserSelectedMenuNumber("Selected Menu(number) : "),
    InputAmount_GuideMessage("Please Put The Amount To Save"),
    InputAmount("Amount : "),
    InputUserSelectedVoucherType_GuideMessage("[1] Fixed Amount Type \n[2] Percent Amount Type"),
    InputUserSelectedVoucherType("Amount Type : "),
    ShowVoucherList_GuideMessage("Show Voucher List ...................."),
    Success_Amount_Save("Saving Succeeded, Created Voucher Id: "),
    EmptyData("No Result"),

    MonguDbMainMenu("[1] collection connect \n[2] collection create \n[3] collection drop"),
    MonguDbCollectionConnect_GuideMessage("[To Connect] collection : "),
    MonguDbCollectionCreate_GuideMessage("[To Create] collection : "),
    MonguDbCollectionDrop_GuideMessage("[To Drop] collection : "),

    DivisionLine("------------------------------------------"),
    DivisionBlank(" "),
    Apologize("An unexpected error occurred. Our company is sorry for the inconvenience."),



    // < WARNING MESSAGE >------------------------------------------------------
    MessageBeforeExit("ARE YOU SURE ?(yes) "),
    Exit_WarringMessage("Are You Sure To Exit? IF YOU EXIT the program, Your Data could be DELETED!!! "),



    // < ERROR MESSAGE >------------------------------------------------------
    SelectedNumber_LimitError("[ERROR] INVALID SELECTED NUMBER!! CHECK SELECTED NUMBER!!"),
    Percent_LimitError("[ERROR] INVALID AMOUNT!! CHECK THE AMOUNT!"),
    VoucherId_DuplicateError("[ERROR] Voucher Id Duplicate Error, Voucher Id : "),
    InvalidValue_MustWriteDownNumber("[ERROR] Please write it down in numbers.");



    private String message ;

    Script(String message) {
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
