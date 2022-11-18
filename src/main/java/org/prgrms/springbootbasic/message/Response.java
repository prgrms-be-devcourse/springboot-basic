package org.prgrms.springbootbasic.message;

import org.prgrms.springbootbasic.type.MethodType;

import java.text.MessageFormat;
import java.util.List;

import static org.prgrms.springbootbasic.type.MethodType.CREATE;
import static org.prgrms.springbootbasic.type.MethodType.LOOKUP;

public class Response {
    public static final String DOES_NOT_EXIST = "does not exist";
    public static final String GENERATED = "is generated";
    public static final String EXIST = "exist";
    private final MethodType methodType;
    private final List<?> list;

    public Response(MethodType methodType, List<?> list) {
        this.methodType = methodType;
        this.list = list;
    }

    @Override
    public String toString() {
        if (methodType == CREATE) return MessageFormat.format("{0} {1}", list, GENERATED);
        if (methodType == LOOKUP) {
            if (list.isEmpty()) {
                return DOES_NOT_EXIST;
            }
            return MessageFormat.format("{0} {1}", list, EXIST);
        }
        return list.toString();
    }
}
