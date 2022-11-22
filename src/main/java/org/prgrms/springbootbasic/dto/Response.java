package org.prgrms.springbootbasic.dto;

import org.prgrms.springbootbasic.type.MethodType;

import java.text.MessageFormat;
import java.util.List;

import static org.prgrms.springbootbasic.type.MethodType.*;

public class Response {
    public static final String GENERATED = "is generated";
    public static final String DOES_NOT_EXIST = "does not exist";
    public static final String EXIST = "exist";
    public static final String UPDATED = "updated";
    public static final String DELETED = "deleted";
    private final MethodType methodType;
    private final List<?> list;

    public Response(MethodType methodType, List<?> list) {
        this.methodType = methodType;
        this.list = list;
    }

    @Override
    public String toString() {
        if (methodType == CREATE) {
            return MessageFormat.format("{0} {1}", list2String(), GENERATED);
        } else if (methodType == LOOKUP) {
            if (list.isEmpty()) {
                return DOES_NOT_EXIST;
            }
            return MessageFormat.format("{0} {1}", list2String(), EXIST);
        } else if (methodType == UPDATE) {
            return MessageFormat.format("{0} {1}", list2String(), UPDATED);
        } else if (methodType == DELETE) {
            return MessageFormat.format("{0} {1}", list2String(), DELETED);
        }
        return list.toString();
    }

    private String list2String() {
        StringBuffer sb = new StringBuffer();
        list.forEach((e) -> sb.append(e).append("\n"));
        return sb.toString();
    }
}
