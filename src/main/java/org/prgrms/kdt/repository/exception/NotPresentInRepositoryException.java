<<<<<<<< HEAD:src/main/java/org/prgrms/kdt/exception/NotPresentInRepositoryException.java
package org.prgrms.kdt.exception;
========
package org.prgrms.kdt.repository.exception;
>>>>>>>> 91e357b (refactor: 디렉토리 구조를 바꾸다.):src/main/java/org/prgrms/kdt/repository/exception/NotPresentInRepositoryException.java

public class NotPresentInRepositoryException extends RuntimeException {

    public NotPresentInRepositoryException(String message) {
        super(message);
    }

    public NotPresentInRepositoryException(String message, Exception e) {
        super(message, e);
    }
}
