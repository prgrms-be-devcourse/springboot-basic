package org.prgrms.springorder.global.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class<?> entity, Object parameter) {
        super(
          String.format(
              "Entity '%s' Not Found By Value '%s'",
              entity.getSimpleName(), parameter.toString()
          )
        );
    }

    public EntityNotFoundException(Class<?> entity, Object parameter, Throwable e) {
        super(String.format(
            "Entity '%s' Not Found By Value '%s'",
            entity.getSimpleName(), parameter.toString()
        ), e);
    }
}
