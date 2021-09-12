package org.prgrms.kdt.error;

import org.apache.commons.lang3.StringUtils;

public class NotFoundException extends ServiceRuntimeException {

    static final String MESSAGE_KEY = "error.notFound";
    static final String MESSAGE_DETAILS = "error.notFound.details";

    public NotFoundException(Class<?> className, Object... values) {
        this(className.getSimpleName(), values);
    }

    public NotFoundException(String targetName, Object... values) {
        super(MESSAGE_KEY, MESSAGE_DETAILS, new String[]{targetName, (values != null && values.length > 0) ? StringUtils.join(values, ",") : ""});
    }

    @Override
    public String getMessageKey() {
        return super.getMessageKey();
    }

    @Override
    public String getDetailKey() {
        return super.getDetailKey();
    }

    @Override
    public Object[] getParams() {
        return super.getParams();
    }
}
