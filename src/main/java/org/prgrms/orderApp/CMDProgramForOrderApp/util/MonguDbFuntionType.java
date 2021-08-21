package org.prgrms.orderApp.CMDProgramForOrderApp.util;

public enum MonguDbFuntionType {
    CONNECT(1, "COLLECTION_CONNECT"),
    CREATE(2,"COLLECTION_CREATE"),
    DROP(3,"COLLECTION_DROP");



    private int menuNumber;
    private String menuName;

    MonguDbFuntionType(int menuNumber, String menuName){
        this.menuNumber = menuNumber;
        this.menuName = menuName;
    }

    public String getMenuName(){
        return menuName;
    }

    public static String getMenuName(int menuNumber){
        if(menuNumber==MonguDbFuntionType.CONNECT.menuNumber){
            return MonguDbFuntionType.CONNECT.menuName;
        }else if(menuNumber==MonguDbFuntionType.CREATE.menuNumber){
            return MonguDbFuntionType.CREATE.menuName;
        }else if(menuNumber==MonguDbFuntionType.DROP.menuNumber){
            return MonguDbFuntionType.DROP.menuName;
        }else{
            return "";
        }
    }
}
